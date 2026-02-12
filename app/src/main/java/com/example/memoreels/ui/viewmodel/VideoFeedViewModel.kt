package com.example.memoreels.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.memoreels.data.ml.VideoTagger
import com.example.memoreels.domain.model.Video
import com.example.memoreels.domain.usecase.GetThisDayVideosUseCase
import com.example.memoreels.domain.usecase.GetVideosUseCase
import com.example.memoreels.domain.usecase.ToggleFavoriteUseCase
import com.example.memoreels.domain.repository.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoFeedViewModel @Inject constructor(
    private val getVideos: GetVideosUseCase,
    private val toggleFavorite: ToggleFavoriteUseCase,
    private val repository: VideoRepository,
    private val getThisDayVideos: GetThisDayVideosUseCase,
    private val videoTagger: VideoTagger
) : ViewModel() {

    val videos: Flow<PagingData<Video>> = getVideos(emptyList())
        .cachedIn(viewModelScope)

    private val _isMuted = MutableStateFlow(false)
    val isMuted: StateFlow<Boolean> = _isMuted.asStateFlow()

    private val _isPlaying = MutableStateFlow(true)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _favoriteUris = MutableStateFlow<Set<String>>(emptySet())
    val favoriteUris: StateFlow<Set<String>> = _favoriteUris.asStateFlow()

    // "This Day in History" videos
    val thisDayVideos: StateFlow<List<Video>> = getThisDayVideos()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _thisDayDismissed = MutableStateFlow(false)
    val thisDayDismissed: StateFlow<Boolean> = _thisDayDismissed.asStateFlow()

    init {
        // Observe favorites
        viewModelScope.launch {
            repository.getFavorites().collect { favorites ->
                _favoriteUris.value = favorites.map { it.videoUri }.toSet()
            }
        }
        // Launch background video tagging
        viewModelScope.launch {
            videoTagger.processUntaggedVideos()
        }
    }

    fun toggleMute() {
        _isMuted.value = !_isMuted.value
    }

    fun togglePlayPause() {
        _isPlaying.value = !_isPlaying.value
    }

    fun onToggleFavorite(video: Video) {
        viewModelScope.launch {
            val added = toggleFavorite(video)
            _favoriteUris.value = if (added) {
                _favoriteUris.value + video.uri
            } else {
                _favoriteUris.value - video.uri
            }
        }
    }

    /** Dismiss the "This Day" card for this session. */
    fun dismissThisDay() {
        _thisDayDismissed.value = true
    }
}
