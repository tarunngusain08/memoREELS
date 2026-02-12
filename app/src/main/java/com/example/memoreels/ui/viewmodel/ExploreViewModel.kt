package com.example.memoreels.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.memoreels.data.local.VideoTagDao
import com.example.memoreels.data.ml.TaggingProgress
import com.example.memoreels.data.ml.VideoTagger
import com.example.memoreels.domain.model.Video
import com.example.memoreels.domain.repository.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
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
    private val videoTagger: VideoTagger
) : ViewModel() {

    // --- Collections (shuffled on each ViewModel creation) ---

    val collections: StateFlow<List<VideoCollection>> = videoTagDao.getTagCounts()
        .map { tagCounts ->
            tagCounts.map { tc ->
                VideoCollection(
                    tag = tc.tag,
                    videoCount = tc.cnt,
                    thumbnailUri = videoTagDao.getFirstVideoForTag(tc.tag)
                )
            }.shuffled()
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // --- Video of the Day ---

    private val _videoOfTheDay = MutableStateFlow<String?>(null)
    val videoOfTheDay: StateFlow<String?> = _videoOfTheDay.asStateFlow()

    init {
        viewModelScope.launch {
            val allUris = videoTagDao.getProcessedUris()
            if (allUris.isNotEmpty()) {
                _videoOfTheDay.value = allUris.random()
            }
        }
    }

    // --- Tagging progress ---

    val taggingProgress: StateFlow<TaggingProgress?> = videoTagger.progress

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
