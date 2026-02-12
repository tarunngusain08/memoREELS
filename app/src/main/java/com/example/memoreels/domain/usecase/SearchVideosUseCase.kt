package com.example.memoreels.domain.usecase

import androidx.paging.PagingData
import com.example.memoreels.domain.model.Video
import com.example.memoreels.domain.repository.VideoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchVideosUseCase @Inject constructor(
    private val repository: VideoRepository
) {

    @Suppress("unused")
    operator fun invoke(query: String, exclusions: List<String> = emptyList()): Flow<PagingData<Video>> {
        // TODO: Filter by query once search is implemented
        return repository.getVideos(exclusions)
    }
}
