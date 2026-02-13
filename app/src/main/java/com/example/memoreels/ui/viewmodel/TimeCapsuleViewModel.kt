package com.example.memoreels.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memoreels.data.local.TimeCapsuleDao
import com.example.memoreels.data.model.TimeCapsuleEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimeCapsuleViewModel @Inject constructor(
    private val timeCapsuleDao: TimeCapsuleDao,
    private val videoTagDao: com.example.memoreels.data.local.VideoTagDao
) : ViewModel() {

    companion object {
        private const val TAG = "TimeCapsuleVM"
    }

    val capsules: StateFlow<List<TimeCapsuleEntity>> = timeCapsuleDao.getAll()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    /** Available media URIs that have been processed/tagged. */
    private val _taggedMediaUris = MutableStateFlow<List<String>>(emptyList())
    val taggedMediaUris: StateFlow<List<String>> = _taggedMediaUris.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                _taggedMediaUris.value = videoTagDao.getProcessedUris()
            } catch (e: Exception) {
                Log.e(TAG, "Failed to load tagged URIs", e)
            }
        }
    }

    fun createCapsule(title: String, mediaUris: List<String>, unlockDate: Long) {
        viewModelScope.launch {
            try {
                timeCapsuleDao.insert(
                    TimeCapsuleEntity(
                        title = title,
                        mediaUris = mediaUris.joinToString(","),
                        unlockDate = unlockDate
                    )
                )
            } catch (e: Exception) {
                Log.e(TAG, "Failed to create capsule", e)
            }
        }
    }

    fun openCapsule(id: Long) {
        viewModelScope.launch {
            try {
                timeCapsuleDao.markOpened(id)
            } catch (e: Exception) {
                Log.e(TAG, "Failed to open capsule", e)
            }
        }
    }

    fun deleteCapsule(id: Long) {
        viewModelScope.launch {
            try {
                timeCapsuleDao.delete(id)
            } catch (e: Exception) {
                Log.e(TAG, "Failed to delete capsule", e)
            }
        }
    }
}
