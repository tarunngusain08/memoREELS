package com.example.memoreels

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.memoreels.widget.DailyMemoryWorker
import com.example.memoreels.worker.MediaTaggingWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class MemoReelsApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        scheduleDailyMemoryWidget()
        scheduleMediaTagging()
    }

    private fun scheduleDailyMemoryWidget() {
        val request = PeriodicWorkRequestBuilder<DailyMemoryWorker>(
            1, TimeUnit.DAYS
        ).build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            DailyMemoryWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }

    /** Schedule ML tagging as a one-time background task (runs immediately). */
    private fun scheduleMediaTagging() {
        val taggingWork = OneTimeWorkRequestBuilder<MediaTaggingWorker>()
            .build()
        WorkManager.getInstance(this).enqueueUniqueWork(
            MediaTaggingWorker.WORK_NAME,
            ExistingWorkPolicy.KEEP,
            taggingWork
        )
    }
}
