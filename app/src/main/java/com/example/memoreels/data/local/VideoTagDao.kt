package com.example.memoreels.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.memoreels.data.model.VideoTagEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VideoTagDao {

    /** Batch insert tags for a video. */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTags(tags: List<VideoTagEntity>)

    /** All tags for a specific video. */
    @Query("SELECT * FROM video_tags WHERE videoUri = :uri")
    suspend fun getTagsForVideo(uri: String): List<VideoTagEntity>

    /** All distinct tag names across all videos (reactive). */
    @Query("SELECT DISTINCT tag FROM video_tags ORDER BY tag ASC")
    fun getDistinctTags(): Flow<List<String>>

    /** All video URIs that have a specific tag (reactive). */
    @Query("SELECT videoUri FROM video_tags WHERE tag = :tag ORDER BY confidence DESC")
    fun getVideosForTag(tag: String): Flow<List<String>>

    /** Video URIs whose tags match a LIKE query (reactive). */
    @Query("SELECT DISTINCT videoUri FROM video_tags WHERE LOWER(tag) LIKE '%' || LOWER(:query) || '%'")
    fun searchByTag(query: String): Flow<List<String>>

    /** Count of videos for each tag (reactive). */
    @Query("SELECT tag, COUNT(DISTINCT videoUri) as cnt FROM video_tags GROUP BY tag ORDER BY cnt DESC")
    fun getTagCounts(): Flow<List<TagCount>>

    /** First video URI for a given tag (for thumbnails). */
    @Query("SELECT videoUri FROM video_tags WHERE tag = :tag ORDER BY confidence DESC LIMIT 1")
    suspend fun getFirstVideoForTag(tag: String): String?

    /** All URIs that have already been processed (at least one tag). */
    @Query("SELECT DISTINCT videoUri FROM video_tags")
    suspend fun getProcessedUris(): List<String>

    /** All tags with their associated URIs for tag-based grouping. */
    @Query("SELECT * FROM video_tags ORDER BY confidence DESC")
    suspend fun getAllTags(): List<VideoTagEntity>

    /** Synchronous tag search for mood filtering. */
    @Query("SELECT DISTINCT videoUri FROM video_tags WHERE LOWER(tag) LIKE :query")
    suspend fun searchByTagDirect(query: String): List<String>
}

/** Simple projection for tag counts. */
data class TagCount(
    val tag: String,
    val cnt: Int
)
