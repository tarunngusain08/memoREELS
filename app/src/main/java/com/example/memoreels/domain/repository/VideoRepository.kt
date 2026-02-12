package com.example.memoreels.domain.repository

import androidx.paging.PagingData
import com.example.memoreels.domain.model.Favorite
import com.example.memoreels.domain.model.Video
import kotlinx.coroutines.flow.Flow

interface VideoRepository {

    fun getVideos(exclusions: List<String> = emptyList()): Flow<PagingData<Video>>

    fun searchVideos(query: String, exclusions: List<String> = emptyList()): Flow<PagingData<Video>>

    suspend fun getVideoByUri(uri: String): Video?

    fun getFavorites(): Flow<List<Favorite>>

    suspend fun toggleFavorite(video: Video): Boolean

    suspend fun isFavorite(uri: String): Boolean
}
