package com.example.memoreels.domain.usecase

import com.example.memoreels.domain.model.Video
import com.example.memoreels.domain.repository.VideoRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: VideoRepository
) {

    suspend operator fun invoke(video: Video): Boolean {
        return repository.toggleFavorite(video)
    }
}
