package com.example.vaxtrack

import android.app.Application
import androidx.work.*
import com.example.vaxtrack.sync.SyncWorker
import java.util.concurrent.TimeUnit

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        val request = PeriodicWorkRequestBuilder<SyncWorker>(
            15, TimeUnit.MINUTES
        )
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "syncWork",
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }
}
