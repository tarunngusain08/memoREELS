package com.example.memoreels.domain.usecase

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import com.example.memoreels.domain.model.Video
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.Calendar
import javax.inject.Inject

/**
 * Returns videos whose DATE_ADDED falls on the same month+day as today
 * but in a previous year (i.e. "This Day in History" / "Memories").
 */
class GetThisDayVideosUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {
    operator fun invoke(): Flow<List<Video>> = flow {
        val today = Calendar.getInstance()
        val currentMonth = today.get(Calendar.MONTH)
        val currentDay = today.get(Calendar.DAY_OF_MONTH)
        val currentYear = today.get(Calendar.YEAR)

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
        val sortOrder = "${MediaStore.Video.Media.DATE_ADDED} DESC"

        val cursor = context.contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )

        val results = mutableListOf<Video>()
        cursor?.use { c ->
            val idCol = c.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            val pathCol = c.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            val nameCol = c.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
            val dateCol = c.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED)
            val durCol = c.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
            val mimeCol = c.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE)

            val cal = Calendar.getInstance()
            while (c.moveToNext()) {
                val dateAdded = c.getLong(dateCol) * 1000 // seconds -> millis
                cal.timeInMillis = dateAdded
                val month = cal.get(Calendar.MONTH)
                val day = cal.get(Calendar.DAY_OF_MONTH)
                val year = cal.get(Calendar.YEAR)

                if (month == currentMonth && day == currentDay && year < currentYear) {
                    val id = c.getLong(idCol)
                    val contentUri = ContentUris.withAppendedId(
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id
                    )
                    results.add(
                        Video(
                            id = id,
                            uri = contentUri.toString(),
                            path = c.getString(pathCol),
                            displayName = c.getString(nameCol),
                            dateAdded = dateAdded,
                            duration = c.getLong(durCol),
                            location = null,
                            thumbnailUri = contentUri.toString(),
                            mimeType = c.getString(mimeCol)
                        )
                    )
                }
            }
        }
        emit(results)
    }.flowOn(Dispatchers.IO)
}
