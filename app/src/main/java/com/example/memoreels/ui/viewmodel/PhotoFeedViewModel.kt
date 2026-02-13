package com.example.memoreels.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memoreels.data.datasource.FeedItemFactory
import com.example.memoreels.data.datasource.PhotoDataSource
import com.example.memoreels.domain.model.FeedItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the photo-only feed tab (separate mode).
 * Loads all photos, groups them, and exposes as a list of PhotoGroups.
 */
@HiltViewModel
class PhotoFeedViewModel @Inject constructor(
    private val photoDataSource: PhotoDataSource,
    private val feedItemFactory: FeedItemFactory
) : ViewModel() {

    private val _photoGroups = MutableStateFlow<List<FeedItem.PhotoGroup>>(emptyList())
    val photoGroups: StateFlow<List<FeedItem.PhotoGroup>> = _photoGroups.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadPhotos()
    }

    private fun loadPhotos() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val photos = photoDataSource.loadPhotos()
                _photoGroups.value = feedItemFactory.buildPhotoFeed(photos)
            } catch (_: Exception) { }
            _isLoading.value = false
        }
    }
}
