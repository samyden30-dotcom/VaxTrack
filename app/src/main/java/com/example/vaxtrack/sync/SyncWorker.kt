package com.example.vaxtrack.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.vaxtrack.data.local.AppDatabase
import com.example.vaxtrack.data.repository.SyncRepository

class SyncWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val db = AppDatabase.getInstance(applicationContext)
        val syncRepository = SyncRepository(db.syncDao())
        syncRepository.processPendingOperations()
        return Result.success()
    }
}
