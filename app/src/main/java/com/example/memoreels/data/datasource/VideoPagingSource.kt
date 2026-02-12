package com.example.memoreels.data.datasource

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.memoreels.data.model.VideoEntity

class VideoPagingSource constructor(
    private val context: Context,
    private val exclusions: List<String>,
    private val query: String = "",
    private val shuffle: Boolean = true
) : PagingSource<Int, VideoEntity>() {

    companion object {
        private val PROJECTION = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DATE_ADDED,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.MIME_TYPE
        )

        private val DEFAULT_EXCLUDED_FOLDERS = listOf(
            "%/WhatsApp/%",
            "%/ScreenRecordings/%",
            "%/Download/%",
            "%/Downloads/%",
            "%/.thumbnails/%"
        )
    }

    // Shuffled IDs cached for consistent pagination
    private var shuffledIds: List<Long>? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, VideoEntity> {
        return try {
            val page = params.key ?: 0
            val pageSize = params.loadSize.coerceIn(20, 50)

            val contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            val selection = buildSelection()
            val selectionArgs = buildSelectionArgs()

            if (shuffle && shuffledIds == null) {
                shuffledIds = loadAndShuffleIds(contentUri, selection, selectionArgs)
            }

            val videos = if (shuffle && shuffledIds != null) {
                loadShuffledPage(shuffledIds!!, page, pageSize)
            } else {
                loadOrderedPage(contentUri, selection, selectionArgs, page, pageSize)
            }

            val nextKey = if (videos.size < pageSize) null else page + 1
            val prevKey = if (page == 0) null else page - 1

            LoadResult.Page(data = videos, prevKey = prevKey, nextKey = nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private fun loadAndShuffleIds(
        contentUri: android.net.Uri,
        selection: String,
        selectionArgs: Array<String>
    ): List<Long> {
        val ids = mutableListOf<Long>()
        val cursor = context.contentResolver.query(
            contentUri,
            arrayOf(MediaStore.Video.Media._ID),
            selection,
            selectionArgs,
            null
        ) ?: return ids

        try {
            while (cursor.moveToNext()) {
                ids.add(cursor.getLong(0))
            }
        } finally {
            cursor.close()
        }
        return ids.shuffled()
    }

    private fun loadShuffledPage(
        ids: List<Long>,
        page: Int,
        pageSize: Int
    ): List<VideoEntity> {
        val start = page * pageSize
        if (start >= ids.size) return emptyList()
        val end = (start + pageSize).coerceAtMost(ids.size)
        val pageIds = ids.subList(start, end)
        if (pageIds.isEmpty()) return emptyList()

        val idSelection = pageIds.joinToString(",") { "?" }
        val cursor = context.contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            PROJECTION,
            "${MediaStore.Video.Media._ID} IN ($idSelection)",
            pageIds.map { it.toString() }.toTypedArray(),
            null
        ) ?: return emptyList()

        val videoMap = mutableMapOf<Long, VideoEntity>()
        try {
            while (cursor.moveToNext()) {
                val entity = cursorToVideoEntity(cursor)
                if (entity != null) {
                    videoMap[entity.id] = entity
                }
            }
        } finally {
            cursor.close()
        }

        // Return in shuffled order
        return pageIds.mapNotNull { videoMap[it] }
    }

    private fun loadOrderedPage(
        contentUri: android.net.Uri,
        selection: String,
        selectionArgs: Array<String>,
        page: Int,
        pageSize: Int
    ): List<VideoEntity> {
        val sortOrder = "${MediaStore.Video.Media.DATE_ADDED} DESC"
        val cursor = context.contentResolver.query(
            contentUri, PROJECTION, selection, selectionArgs, sortOrder
        ) ?: return emptyList()

        val videos = mutableListOf<VideoEntity>()
        val startPosition = page * pageSize

        try {
            if (cursor.moveToPosition(startPosition)) {
                var count = 0
                do {
                    val entity = cursorToVideoEntity(cursor)
                    if (entity != null) {
                        videos.add(entity)
                        count++
                        if (count >= pageSize) break
                    }
                } while (cursor.moveToNext())
            }
        } finally {
            cursor.close()
        }
        return videos
    }

    override fun getRefreshKey(state: PagingState<Int, VideoEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    private fun buildSelection(): String {
        val baseSelection = "${MediaStore.Video.Media.MIME_TYPE} LIKE ?"
        val exclusionList = if (exclusions.isNotEmpty()) exclusions else DEFAULT_EXCLUDED_FOLDERS
        val exclusionConditions = exclusionList.map {
            "LOWER(${MediaStore.Video.Media.DATA}) NOT LIKE ?"
        }
        val fullExclusion = if (exclusionConditions.isNotEmpty()) {
            " AND (${exclusionConditions.joinToString(" AND ")})"
        } else ""

        val searchCondition = if (query.isNotBlank()) {
            " AND ${MediaStore.Video.Media.DISPLAY_NAME} LIKE ?"
        } else ""

        return "$baseSelection$fullExclusion$searchCondition"
    }

    private fun buildSelectionArgs(): Array<String> {
        val mimeArgs = arrayOf("video/%")
        val exclusionList = if (exclusions.isNotEmpty()) exclusions else DEFAULT_EXCLUDED_FOLDERS
        val exclusionArgs = exclusionList.map { it.lowercase() }.toTypedArray()
        val searchArgs = if (query.isNotBlank()) arrayOf("%${query}%") else emptyArray()
        return mimeArgs + exclusionArgs + searchArgs
    }

    private fun cursorToVideoEntity(cursor: Cursor): VideoEntity? {
        return try {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID))
            val path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA))
            val displayName =
                cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME))
            val dateAdded =
                cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED)) * 1000
            val duration =
                cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION))
            val mimeType =
                cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE))

            val contentUri = ContentUris.withAppendedId(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                id
            )

            VideoEntity(
                id = id,
                uri = contentUri.toString(),
                path = path,
                displayName = displayName,
                dateAdded = dateAdded,
                duration = duration,
                location = null,
                thumbnailUri = contentUri.toString(),
                mimeType = mimeType
            )
        } catch (e: Exception) {
            null
        }
    }
}
