package com.example.memoreels.data.datasource

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import com.example.memoreels.domain.model.Photo
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Loads local images from MediaStore.Images with optional limit
 * to prevent OOM on devices with large galleries.
 */
@Singleton
class PhotoDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        /** Default max photos to load (prevents OOM on large galleries). */
        const val DEFAULT_LIMIT = 500

        private val PROJECTION = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Images.Media.WIDTH,
            MediaStore.Images.Media.HEIGHT,
            MediaStore.Images.Media.MIME_TYPE
        )

        private val DEFAULT_EXCLUDED_FOLDERS = listOf(
            "%/WhatsApp/%",
            "%/.thumbnails/%",
            "%/Screenshots/%",
            "%/Download/%",
            "%/Downloads/%"
        )
    }

    /**
     * Fetch photos with optional limit and folder exclusions.
     * @param limit Max number of photos to return. Pass [Int.MAX_VALUE] for all.
     * @param exclusions Folder patterns to exclude. Defaults to common non-personal folders.
     */
    suspend fun loadPhotos(
        limit: Int = DEFAULT_LIMIT,
        exclusions: List<String> = emptyList()
    ): List<Photo> = withContext(Dispatchers.IO) {
        val photos = mutableListOf<Photo>()
        val exclusionList = exclusions.ifEmpty { DEFAULT_EXCLUDED_FOLDERS }

        val selection = buildString {
            append("${MediaStore.Images.Media.MIME_TYPE} LIKE ?")
            exclusionList.forEach { _ ->
                append(" AND LOWER(${MediaStore.Images.Media.DATA}) NOT LIKE ?")
            }
        }
        val selectionArgs = arrayOf("image/%") +
            exclusionList.map { it.lowercase() }.toTypedArray()

        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

        val cursor = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            PROJECTION,
            selection,
            selectionArgs,
            sortOrder
        ) ?: return@withContext photos

        try {
            val idCol = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val pathCol = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            val nameCol = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val dateCol = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)
            val widthCol = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.WIDTH)
            val heightCol = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT)
            val mimeCol = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE)

            var count = 0
            while (cursor.moveToNext() && count < limit) {
                val id = cursor.getLong(idCol)
                val contentUri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id
                )
                photos.add(
                    Photo(
                        id = id,
                        uri = contentUri.toString(),
                        path = cursor.getString(pathCol),
                        displayName = cursor.getString(nameCol),
                        dateAdded = cursor.getLong(dateCol) * 1000,
                        width = cursor.getInt(widthCol),
                        height = cursor.getInt(heightCol),
                        mimeType = cursor.getString(mimeCol)
                    )
                )
                count++
            }
        } finally {
            cursor.close()
        }

        photos
    }
}
