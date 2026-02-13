package com.example.memoreels

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.memoreels.widget.DailyMemoryWorker
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
}
