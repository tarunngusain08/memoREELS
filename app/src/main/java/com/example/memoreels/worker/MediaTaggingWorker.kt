package com.example.memoreels.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.memoreels.data.ml.PhotoTagger
import com.example.memoreels.data.ml.VideoTagger
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

/**
 * WorkManager worker that runs ML tagging in the background with
 * battery-aware scheduling. Replaces the previous approach of running
 * tagging in the ViewModel init block.
 */
@HiltWorker
class MediaTaggingWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val videoTagger: VideoTagger,
    private val photoTagger: PhotoTagger
) : CoroutineWorker(appContext, workerParams) {

    companion object {
        const val WORK_NAME = "mediaTagging"
        private const val TAG = "MediaTaggingWorker"
    }

    override suspend fun doWork(): Result {
        return try {
            Log.d(TAG, "Starting media tagging...")
            videoTagger.processUntaggedVideos()
            photoTagger.processUntaggedPhotos()
            Log.d(TAG, "Media tagging completed successfully")
            Result.success()
        } catch (e: Exception) {
            Log.e(TAG, "Media tagging failed", e)
            Result.retry()
        }
    }
}
