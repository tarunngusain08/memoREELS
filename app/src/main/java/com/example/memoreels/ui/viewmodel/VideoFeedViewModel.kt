package com.example.memoreels.ui.viewmodel

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.memoreels.data.datasource.FeedItemFactory
import com.example.memoreels.data.datasource.PhotoDataSource
import com.example.memoreels.data.local.VideoTagDao
import com.example.memoreels.data.ml.PhotoTagger
import com.example.memoreels.data.ml.VideoTagger
import com.example.memoreels.data.preferences.FeedMode
import com.example.memoreels.data.preferences.UserPreferences
import com.example.memoreels.ui.components.MemoryOfMoment
import com.example.memoreels.domain.model.FeedItem
import com.example.memoreels.domain.model.Video
import com.example.memoreels.domain.repository.VideoRepository
import com.example.memoreels.domain.usecase.GetThisDayVideosUseCase
import com.example.memoreels.domain.usecase.GetVideosUseCase
import com.example.memoreels.domain.usecase.ToggleFavoriteUseCase
import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class VideoFeedViewModel @Inject constructor(
    private val getVideos: GetVideosUseCase,
    private val toggleFavorite: ToggleFavoriteUseCase,
    private val repository: VideoRepository,
    private val getThisDayVideos: GetThisDayVideosUseCase,
    private val videoTagger: VideoTagger,
    private val photoTagger: PhotoTagger,
    private val photoDataSource: PhotoDataSource,
    private val feedItemFactory: FeedItemFactory,
    private val videoTagDao: VideoTagDao,
    val userPreferences: UserPreferences,
    @ApplicationContext private val appContext: Context
) : ViewModel() {

    companion object {
        private const val TAG = "VideoFeedViewModel"
    }

    /** Video-only paged flow (for SEPARATE mode). */
    val videos: Flow<PagingData<Video>> = getVideos(emptyList())
        .cachedIn(viewModelScope)

    /** Unified feed items (videos + photos mixed). */
    private val _unifiedFeed = MutableStateFlow<List<FeedItem>>(emptyList())
    val unifiedFeed: StateFlow<List<FeedItem>> = _unifiedFeed.asStateFlow()

    /** Current feed mode. */
    val feedMode: StateFlow<FeedMode> = userPreferences.feedMode
        .stateIn(viewModelScope, SharingStarted.Eagerly, FeedMode.UNIFIED)

    private val _isMuted = MutableStateFlow(false)
    val isMuted: StateFlow<Boolean> = _isMuted.asStateFlow()

    private val _isPlaying = MutableStateFlow(true)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    /** Auto-mute preference from DataStore. */
    val autoMute: StateFlow<Boolean> = userPreferences.autoMute
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    /** Loop videos preference from DataStore. */
    val loopVideos: StateFlow<Boolean> = userPreferences.loopVideos
        .stateIn(viewModelScope, SharingStarted.Eagerly, true)

    private val _favoriteUris = MutableStateFlow<Set<String>>(emptySet())
    val favoriteUris: StateFlow<Set<String>> = _favoriteUris.asStateFlow()

    // "This Day in History" videos
    val thisDayVideos: StateFlow<List<Video>> = getThisDayVideos()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _thisDayDismissed = MutableStateFlow(false)
    val thisDayDismissed: StateFlow<Boolean> = _thisDayDismissed.asStateFlow()

    // Memory of the Moment
    private val _memoryOfMoment = MutableStateFlow<MemoryOfMoment?>(null)
    val memoryOfMoment: StateFlow<MemoryOfMoment?> = _memoryOfMoment.asStateFlow()

    private val _memoryDismissed = MutableStateFlow(false)
    val memoryDismissed: StateFlow<Boolean> = _memoryDismissed.asStateFlow()

    init {
        // Observe favorites
        viewModelScope.launch {
            try {
                repository.getFavorites().collect { favorites ->
                    _favoriteUris.value = favorites.map { it.videoUri }.toSet()
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to observe favorites", e)
            }
        }
        // Launch background video tagging
        viewModelScope.launch {
            try {
                videoTagger.processUntaggedVideos()
            } catch (e: Exception) {
                Log.e(TAG, "Video tagging failed", e)
            }
        }
        // Launch background photo tagging
        viewModelScope.launch {
            try {
                photoTagger.processUntaggedPhotos()
            } catch (e: Exception) {
                Log.e(TAG, "Photo tagging failed", e)
            }
        }
        // Pick Memory of the Moment
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
        // Build unified feed
        loadUnifiedFeed()
    }

    /** Loads photos + videos and builds the unified feed. */
    private fun loadUnifiedFeed() {
        viewModelScope.launch {
            try {
                val photos = photoDataSource.loadPhotos()
                val videoList = loadAllVideos()
                _unifiedFeed.value = feedItemFactory.buildUnifiedFeed(videoList, photos)
            } catch (e: Exception) {
                Log.e(TAG, "Failed to load unified feed", e)
            }
        }
    }

    /** Direct MediaStore query for all videos (non-paged, for unified feed). */
    private suspend fun loadAllVideos(): List<Video> = withContext(Dispatchers.IO) {
        val videos = mutableListOf<Video>()
        val projection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DATE_ADDED,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.MIME_TYPE
        )
        val selection = "${MediaStore.Video.Media.MIME_TYPE} LIKE ?"
        val selectionArgs = arrayOf("video/%")

        val cursor = appContext.contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            projection, selection, selectionArgs,
            "${MediaStore.Video.Media.DATE_ADDED} DESC"
        ) ?: return@withContext videos

        try {
            val idCol = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            val pathCol = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            val nameCol = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
            val dateCol = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED)
            val durCol = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
            val mimeCol = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idCol)
                val contentUri = ContentUris.withAppendedId(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id
                )
                videos.add(
                    Video(
                        id = id,
                        uri = contentUri.toString(),
                        path = cursor.getString(pathCol),
                        displayName = cursor.getString(nameCol),
                        dateAdded = cursor.getLong(dateCol) * 1000,
                        duration = cursor.getLong(durCol),
                        location = null,
                        thumbnailUri = contentUri.toString(),
                        mimeType = cursor.getString(mimeCol)
                    )
                )
            }
        } finally {
            cursor.close()
        }
        videos
    }

    fun setFeedMode(mode: FeedMode) {
        viewModelScope.launch {
            userPreferences.setFeedMode(mode)
            if (mode == FeedMode.UNIFIED) {
                loadUnifiedFeed()
            }
        }
    }

    fun toggleMute() {
        _isMuted.value = !_isMuted.value
    }

    /** Persist auto-mute preference. */
    fun setAutoMute(enabled: Boolean) {
        viewModelScope.launch {
            try {
                userPreferences.setAutoMute(enabled)
            } catch (e: Exception) {
                Log.e(TAG, "Failed to set auto-mute", e)
            }
        }
    }

    /** Persist loop videos preference. */
    fun setLoopVideos(enabled: Boolean) {
        viewModelScope.launch {
            try {
                userPreferences.setLoopVideos(enabled)
            } catch (e: Exception) {
                Log.e(TAG, "Failed to set loop videos", e)
            }
        }
    }

    fun togglePlayPause() {
        _isPlaying.value = !_isPlaying.value
    }

    fun onToggleFavorite(video: Video) {
        viewModelScope.launch {
            try {
                val added = toggleFavorite(video)
                _favoriteUris.value = if (added) {
                    _favoriteUris.value + video.uri
                } else {
                    _favoriteUris.value - video.uri
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to toggle favorite", e)
            }
        }
    }

    /** Dismiss the "This Day" card for this session. */
    fun dismissThisDay() {
        _thisDayDismissed.value = true
    }

    /** Dismiss the "Memory of the Moment" card for this session. */
    fun dismissMemory() {
        _memoryDismissed.value = true
    }
}
