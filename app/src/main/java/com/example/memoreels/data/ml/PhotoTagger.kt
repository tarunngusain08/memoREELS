package com.example.memoreels.data.ml

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import com.example.memoreels.data.datasource.PhotoDataSource
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

/**
 * Processes untagged photos with ML Kit Image Labeling to extract
 * semantic tags. Stores tags in the shared video_tags table using
 * the photo content URI as the key.
 */
@Singleton
class PhotoTagger @Inject constructor(
    @ApplicationContext private val context: Context,
    private val videoTagDao: VideoTagDao,
    private val photoDataSource: PhotoDataSource
) {
    companion object {
        private const val TAG = "PhotoTagger"
    }

    private val _progress = MutableStateFlow<TaggingProgress?>(null)
    val progress: StateFlow<TaggingProgress?> = _progress.asStateFlow()

    /**
     * Scans all device photos, skips those already tagged, and
     * processes the rest through ML Kit image labeling.
     */
    suspend fun processUntaggedPhotos() = withContext(Dispatchers.IO) {
        val labeler = ImageLabeling.getClient(
            ImageLabelerOptions.Builder()
                .setConfidenceThreshold(0.6f)
                .build()
        )
        try {
            val allPhotos = photoDataSource.loadPhotos(limit = Int.MAX_VALUE)
            val processedUris = videoTagDao.getProcessedUris().toSet()
            val unprocessed = allPhotos.filter { it.uri !in processedUris }

            if (unprocessed.isEmpty()) {
                _progress.value = null
                return@withContext
            }

            _progress.value = TaggingProgress(0, unprocessed.size)

            unprocessed.forEachIndexed { index, photo ->
                var bitmap: Bitmap? = null
                try {
                    bitmap = loadPhotoBitmap(photo.uri)
                    if (bitmap != null) {
                        val inputImage = InputImage.fromBitmap(bitmap, 0)
                        val labels = labeler.process(inputImage).await()
                        val tagEntities = labels.map { label ->
                            VideoTagEntity(
                                videoUri = photo.uri,
                                tag = label.text,
                                confidence = label.confidence
                            )
                        }
                        if (tagEntities.isNotEmpty()) {
                            videoTagDao.insertTags(tagEntities)
                        }
                    }
                } catch (e: Exception) {
                    Log.w(TAG, "Failed to process photo: ${photo.uri}", e)
                } finally {
                    bitmap?.recycle()
                }
                _progress.value = TaggingProgress(index + 1, unprocessed.size)
            }

            _progress.value = null
        } catch (e: Exception) {
            Log.e(TAG, "Photo tagging failed", e)
            _progress.value = null
        } finally {
            labeler.close()
        }
    }

    /** Loads a photo bitmap from its content URI with aggressive downsampling. */
    private fun loadPhotoBitmap(photoUri: String): Bitmap? {
        return try {
            context.contentResolver.openInputStream(Uri.parse(photoUri))?.use { inputStream ->
                val options = BitmapFactory.Options().apply {
                    // Downsample aggressively -- ML labeling doesn't need full resolution
                    inSampleSize = 4
                }
                BitmapFactory.decodeStream(inputStream, null, options)
            }
        } catch (e: Exception) {
            Log.w(TAG, "Failed to load bitmap: $photoUri", e)
            null
        }
    }
}
