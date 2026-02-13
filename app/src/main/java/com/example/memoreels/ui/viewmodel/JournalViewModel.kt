package com.example.memoreels.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memoreels.data.datasource.PhotoDataSource
import com.example.memoreels.data.local.JournalDao
import com.example.memoreels.data.model.JournalEntryEntity
import com.example.memoreels.domain.model.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

/** A day's worth of media grouped by date string. */
data class DayGroup(
    val date: String,
    val displayDate: String,
    val photos: List<Photo>,
    val note: String?
)

@HiltViewModel
class JournalViewModel @Inject constructor(
    private val journalDao: JournalDao,
    private val photoDataSource: PhotoDataSource,
    @ApplicationContext private val context: Context
) : ViewModel() {

    companion object {
        private const val TAG = "JournalVM"
    }

    private val _dayGroups = MutableStateFlow<List<DayGroup>>(emptyList())
    val dayGroups: StateFlow<List<DayGroup>> = _dayGroups.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    val journalEntries = journalDao.getAll()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        loadTimeline()
    }

    private fun loadTimeline() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val photos = withContext(Dispatchers.IO) {
                    photoDataSource.loadPhotos(limit = 1000)
                }
                val entries = withContext(Dispatchers.IO) {
                    journalDao.getAll().first()
                }
                val noteMap = entries.associateBy { it.date }

                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val displayFormat = SimpleDateFormat("EEEE, MMM dd, yyyy", Locale.getDefault())

                val grouped = photos.groupBy { photo ->
                    dateFormat.format(Date(photo.dateAdded))
                }.map { (date, photosForDay) ->
                    DayGroup(
                        date = date,
                        displayDate = try {
                            displayFormat.format(dateFormat.parse(date)!!)
                        } catch (_: Exception) { date },
                        photos = photosForDay.sortedByDescending { it.dateAdded },
                        note = noteMap[date]?.note
                    )
                }.sortedByDescending { it.date }

                _dayGroups.value = grouped
                _isLoading.value = false
            } catch (e: Exception) {
                Log.e(TAG, "Failed to load timeline", e)
                _isLoading.value = false
            }
        }
    }

    fun saveNote(date: String, note: String) {
        viewModelScope.launch {
            try {
                if (note.isBlank()) {
                    journalDao.delete(date)
                } else {
                    journalDao.upsert(
                        JournalEntryEntity(
                            date = date,
                            note = note,
                            updatedAt = System.currentTimeMillis()
                        )
                    )
                }
                // Update local state
                _dayGroups.value = _dayGroups.value.map {
                    if (it.date == date) it.copy(note = note.ifBlank { null }) else it
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to save note", e)
            }
        }
    }
}
