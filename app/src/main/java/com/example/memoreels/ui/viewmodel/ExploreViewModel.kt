package com.example.memoreels.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.memoreels.data.local.VideoTagDao
import com.example.memoreels.data.ml.PhotoTagger
import com.example.memoreels.data.ml.TaggingProgress
import com.example.memoreels.data.ml.VideoTagger
import com.example.memoreels.domain.model.Video
import com.example.memoreels.domain.repository.VideoRepository
import com.example.memoreels.ui.components.MemoryOfMoment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/** A single AI-generated collection (tag with metadata). */
data class VideoCollection(
    val tag: String,
    val videoCount: Int,
    val thumbnailUri: String?
)

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val repository: VideoRepository,
    private val videoTagDao: VideoTagDao,
    private val videoTagger: VideoTagger,
    private val photoTagger: PhotoTagger
) : ViewModel() {

    companion object {
        private const val TAG = "ExploreViewModel"
    }

    // --- Collections (shuffled on each ViewModel creation) ---

    val collections: StateFlow<List<VideoCollection>> = videoTagDao.getTagCounts()
        .flatMapLatest { tagCounts ->
            flow {
                val result = tagCounts.map { tc ->
                    VideoCollection(
                        tag = tc.tag,
                        videoCount = tc.cnt,
                        thumbnailUri = videoTagDao.getFirstVideoForTag(tc.tag)
                    )
                }.shuffled()
                emit(result)
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // --- Memory of the Moment (replaces Video of the Day) ---

    private val _memoryOfMoment = MutableStateFlow<MemoryOfMoment?>(null)
    val memoryOfMoment: StateFlow<MemoryOfMoment?> = _memoryOfMoment.asStateFlow()

    private val _memoryDismissed = MutableStateFlow(false)
    val memoryDismissed: StateFlow<Boolean> = _memoryDismissed.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val allUris = videoTagDao.getProcessedUris()
                val uri = allUris.randomOrNull() ?: return@launch
                val isVideo = uri.contains("/video/")
                val topTag = videoTagDao.getTagsForVideo(uri)
                    .maxByOrNull { it.confidence }?.tag
                _memoryOfMoment.value = MemoryOfMoment(
                    uri = uri,
                    isVideo = isVideo,
                    tag = topTag
                )
            } catch (e: Exception) {
                Log.e(TAG, "Failed to pick memory of the moment", e)
            }
        }
    }

    fun dismissMemory() {
        _memoryDismissed.value = true
    }

    // --- Combined tagging progress (video + photo) ---

    val taggingProgress: StateFlow<TaggingProgress?> = combine(
        videoTagger.progress,
        photoTagger.progress
    ) { videoProgress, photoProgress ->
        when {
            videoProgress != null && photoProgress != null -> TaggingProgress(
                processed = videoProgress.processed + photoProgress.processed,
                total = videoProgress.total + photoProgress.total
            )
            videoProgress != null -> videoProgress
            photoProgress != null -> photoProgress
            else -> null
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // --- Search ---

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    /** Filename-based search via MediaStore. */
    val searchResults: Flow<PagingData<Video>> = _query
        .debounce(300)
        .flatMapLatest { q ->
            if (q.isBlank()) repository.getVideos()
            else repository.searchVideos(q)
        }
        .cachedIn(viewModelScope)

    /** AI-tag-based search results (separate from PagingData to avoid combine crash). */
    val tagSearchResults: StateFlow<List<String>> = _query
        .debounce(300)
        .flatMapLatest { q ->
            if (q.isBlank()) {
                kotlinx.coroutines.flow.flowOf(emptyList())
            } else {
                videoTagDao.searchByTag(q)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    /** URIs for a specific tag. */
    fun getVideosForTag(tag: String): Flow<List<String>> {
        return videoTagDao.getVideosForTag(tag)
    }

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }
}
