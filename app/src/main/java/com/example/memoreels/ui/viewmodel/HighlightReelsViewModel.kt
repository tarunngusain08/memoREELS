package com.example.memoreels.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memoreels.data.datasource.PhotoDataSource
import com.example.memoreels.data.local.VideoTagDao
import com.example.memoreels.domain.model.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * An "event" is a cluster of media captured within a short time window.
 */
data class MediaEvent(
    val id: String,
    val title: String,
    val dateRange: String,
    val mediaUris: List<String>,
    val thumbnailUri: String
)

@HiltViewModel
class HighlightReelsViewModel @Inject constructor(
    private val photoDataSource: PhotoDataSource,
    private val videoTagDao: VideoTagDao,
    @ApplicationContext private val context: Context
) : ViewModel() {

    companion object {
        private const val TAG = "HighlightReelsVM"
        /** Time window for event clustering (4 hours in ms). */
        private const val EVENT_WINDOW_MS = 4 * 60 * 60 * 1000L
        /** Minimum media items to form an event. */
        private const val MIN_EVENT_SIZE = 3
    }

    private val _events = MutableStateFlow<List<MediaEvent>>(emptyList())
    val events: StateFlow<List<MediaEvent>> = _events.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        detectEvents()
    }

    /**
     * Cluster media by timestamp proximity to detect "events".
     * Media within [EVENT_WINDOW_MS] of each other belongs to the same event.
     */
    private fun detectEvents() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val photos = withContext(Dispatchers.IO) {
                    photoDataSource.loadPhotos(limit = 2000)
                }.sortedBy { it.dateAdded }

                if (photos.isEmpty()) {
                    _events.value = emptyList()
                    _isLoading.value = false
                    return@launch
                }

                val clusters = mutableListOf<MutableList<Photo>>()
                var currentCluster = mutableListOf(photos.first())

                for (i in 1 until photos.size) {
                    if (photos[i].dateAdded - photos[i - 1].dateAdded <= EVENT_WINDOW_MS) {
                        currentCluster.add(photos[i])
                    } else {
                        if (currentCluster.size >= MIN_EVENT_SIZE) {
                            clusters.add(currentCluster)
                        }
                        currentCluster = mutableListOf(photos[i])
                    }
                }
                if (currentCluster.size >= MIN_EVENT_SIZE) {
                    clusters.add(currentCluster)
                }

                val dateFormat = SimpleDateFormat("MMM dd", Locale.getDefault())
                val events = clusters.mapIndexed { index, cluster ->
                    val startDate = dateFormat.format(Date(cluster.first().dateAdded))
                    val endDate = dateFormat.format(Date(cluster.last().dateAdded))
                    val range = if (startDate == endDate) startDate else "$startDate - $endDate"

                    // Get the top tag for the first photo if available
                    val topTag = withContext(Dispatchers.IO) {
                        try {
                            videoTagDao.getTagsForVideo(cluster.first().uri)
                                .maxByOrNull { it.confidence }?.tag
                        } catch (_: Exception) { null }
                    }

                    MediaEvent(
                        id = "event_$index",
                        title = topTag ?: "Event ${index + 1}",
                        dateRange = range,
                        mediaUris = cluster.map { it.uri },
                        thumbnailUri = cluster.first().uri
                    )
                }.reversed() // Most recent first

                _events.value = events
                _isLoading.value = false
            } catch (e: Exception) {
                Log.e(TAG, "Event detection failed", e)
                _isLoading.value = false
            }
        }
    }
}
