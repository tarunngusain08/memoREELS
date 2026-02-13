package com.example.memoreels.widget

import android.content.Context
import android.content.SharedPreferences
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.memoreels.data.local.VideoTagDao
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@HiltWorker
class DailyMemoryWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val videoTagDao: VideoTagDao
) : CoroutineWorker(context, params) {

    companion object {
        const val WORK_NAME = "daily_memory_widget"
        const val PREFS_NAME = "widget_prefs"
        const val KEY_URI = "memory_uri"
        const val KEY_IS_VIDEO = "memory_is_video"
        const val KEY_TAG = "memory_tag"
        const val KEY_DATE = "memory_date"
    }

    override suspend fun doWork(): Result {
        return try {
            val allUris = videoTagDao.getProcessedUris()
            val uri = allUris.randomOrNull() ?: return Result.success()
            val isVideo = uri.contains("/video/")
            val topTag = videoTagDao.getTagsForVideo(uri)
                .maxByOrNull { it.confidence }?.tag ?: ""
            val dateStr = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                .format(Date())

            val prefs: SharedPreferences = applicationContext.getSharedPreferences(
                PREFS_NAME, Context.MODE_PRIVATE
            )
            prefs.edit()
                .putString(KEY_URI, uri)
                .putBoolean(KEY_IS_VIDEO, isVideo)
                .putString(KEY_TAG, topTag)
                .putString(KEY_DATE, dateStr)
                .apply()

            // Update all widget instances
            val manager = GlanceAppWidgetManager(applicationContext)
            val widget = DailyMemoryWidget()
            manager.getGlanceIds(DailyMemoryWidget::class.java).forEach { id ->
                widget.update(applicationContext, id)
            }

            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
