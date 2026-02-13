package com.example.memoreels.data.ml

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.example.memoreels.data.local.VideoTagDao
import com.example.memoreels.data.model.VideoTagEntity
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/** Progress information emitted during tagging. */
data class TaggingProgress(
    val processed: Int,
    val total: Int
)

/**
 * Processes untagged videos with ML Kit Image Labeling to extract
 * semantic tags. Runs on Dispatchers.IO, emits progress via [progress].
 */
@Singleton
class VideoTagger @Inject constructor(
    @ApplicationContext private val context: Context,
    private val videoTagDao: VideoTagDao
) {
    companion object {
        private const val TAG = "VideoTagger"
    }

    private val _progress = MutableStateFlow<TaggingProgress?>(null)
    val progress: StateFlow<TaggingProgress?> = _progress.asStateFlow()

    /**
     * Scans all device videos, skips those already tagged, and
     * processes the rest through ML Kit image labeling.
     */
    suspend fun processUntaggedVideos() = withContext(Dispatchers.IO) {
        val labeler = ImageLabeling.getClient(
            ImageLabelerOptions.Builder()
                .setConfidenceThreshold(0.6f)
                .build()
        )
        try {
            val allUris = getAllVideoUris(context.contentResolver)
            val processedUris = videoTagDao.getProcessedUris().toSet()
            val unprocessed = allUris.filter { it !in processedUris }

            if (unprocessed.isEmpty()) {
                _progress.value = null
                return@withContext
            }

            _progress.value = TaggingProgress(0, unprocessed.size)

            unprocessed.forEachIndexed { index, videoUri ->
                var bitmap: Bitmap? = null
                try {
                    bitmap = extractFrame(videoUri)
                    if (bitmap != null) {
                        val inputImage = InputImage.fromBitmap(bitmap, 0)
                        val labels = labeler.process(inputImage).await()
                        val tagEntities = labels.map { label ->
                            VideoTagEntity(
                                videoUri = videoUri,
                                tag = label.text,
                                confidence = label.confidence
                            )
                        }
                        if (tagEntities.isNotEmpty()) {
                            videoTagDao.insertTags(tagEntities)
                        }
                    }
                } catch (e: Exception) {
                    Log.w(TAG, "Failed to process video: $videoUri", e)
                } finally {
                    bitmap?.recycle()
                }
                _progress.value = TaggingProgress(index + 1, unprocessed.size)
            }

            _progress.value = null
        } catch (e: Exception) {
            Log.e(TAG, "Video tagging failed", e)
            _progress.value = null
        } finally {
            labeler.close()
        }
    }

    /** Extracts the first frame of a video as a Bitmap. */
    private fun extractFrame(videoUri: String): Bitmap? {
        val retriever = MediaMetadataRetriever()
        return try {
            retriever.setDataSource(context, Uri.parse(videoUri))
            retriever.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST_SYNC)
        } catch (e: Exception) {
            Log.w(TAG, "Frame extraction failed: $videoUri", e)
            null
        } finally {
            try {
                retriever.release()
            } catch (_: Exception) { }
        }
    }

    /** Queries all video content URIs from MediaStore. */
    private fun getAllVideoUris(contentResolver: ContentResolver): List<String> {
        val uris = mutableListOf<String>()
        val projection = arrayOf(MediaStore.Video.Media._ID)
        val selection = "${MediaStore.Video.Media.MIME_TYPE} LIKE ?"
        val selectionArgs = arrayOf("video/%")

        val cursor = contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            null
        ) ?: return uris

        try {
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val uri = android.content.ContentUris.withAppendedId(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    id
                )
                uris.add(uri.toString())
            }
        } finally {
            cursor.close()
        }
        return uris
    }
}
