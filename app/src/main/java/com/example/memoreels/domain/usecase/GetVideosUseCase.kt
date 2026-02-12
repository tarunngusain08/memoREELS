package com.example.memoreels.domain.usecase

import androidx.paging.PagingData
import com.example.memoreels.domain.model.Video
import com.example.memoreels.domain.repository.VideoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetVideosUseCase @Inject constructor(
    private val repository: VideoRepository
) {

    operator fun invoke(exclusions: List<String> = emptyList()): Flow<PagingData<Video>> {
        return repository.getVideos(exclusions)
    }
}
