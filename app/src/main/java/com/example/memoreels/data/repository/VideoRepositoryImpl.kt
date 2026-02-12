package com.example.memoreels.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.memoreels.data.datasource.VideoPagingSourceFactory
import com.example.memoreels.data.local.FavoritesDao
import com.example.memoreels.data.model.FavoriteEntity
import com.example.memoreels.data.model.VideoEntity
import com.example.memoreels.domain.model.Favorite
import com.example.memoreels.domain.model.Video
import com.example.memoreels.domain.repository.VideoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideoRepositoryImpl @Inject constructor(
    private val videoPagingSourceFactory: VideoPagingSourceFactory,
    private val favoritesDao: FavoritesDao
) : VideoRepository {

    override fun getVideos(exclusions: List<String>): Flow<PagingData<Video>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                initialLoadSize = 20
            ),
            pagingSourceFactory = {
                videoPagingSourceFactory.create(exclusions, query = "", shuffle = true)
            }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }

    override fun searchVideos(
        query: String,
        exclusions: List<String>
    ): Flow<PagingData<Video>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                initialLoadSize = 20
            ),
            pagingSourceFactory = {
                videoPagingSourceFactory.create(exclusions, query = query, shuffle = false)
            }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }

    override suspend fun getVideoByUri(uri: String): Video? {
        return null
    }

    override fun getFavorites(): Flow<List<Favorite>> {
        return favoritesDao.getAllFavorites().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun toggleFavorite(video: Video): Boolean {
        val existing = favoritesDao.getFavoriteByUri(video.uri)
        return if (existing != null) {
            favoritesDao.removeFavorite(video.uri)
            false
        } else {
            favoritesDao.insertFavorite(
                FavoriteEntity(
                    videoUri = video.uri,
                    note = null,
                    createdAt = System.currentTimeMillis()
                )
            )
            true
        }
    }

    override suspend fun isFavorite(uri: String): Boolean {
        return favoritesDao.isFavorite(uri)
    }
}

private fun VideoEntity.toDomain(): Video = Video(
    id = id,
    uri = uri,
    path = path,
    displayName = displayName,
    dateAdded = dateAdded,
    duration = duration,
    location = location,
    thumbnailUri = thumbnailUri,
    mimeType = mimeType
)

private fun FavoriteEntity.toDomain(): Favorite = Favorite(
    videoUri = videoUri,
    note = note,
    createdAt = createdAt
)
