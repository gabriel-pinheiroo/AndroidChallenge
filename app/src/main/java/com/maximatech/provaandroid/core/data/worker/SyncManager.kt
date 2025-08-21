package com.maximatech.provaandroid.core.data.worker

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

class SyncManager(private val context: Context) {

    companion object {
        private const val WORK_NAME = "data_sync"
    }

    fun startPeriodicSync() {
        WorkManager.getInstance(context).cancelUniqueWork(WORK_NAME)

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<DataSyncWorker>(
            15, TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .setInitialDelay(15, TimeUnit.SECONDS)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )
    }

    fun stopSync() {
        WorkManager.getInstance(context).cancelUniqueWork(WORK_NAME)
    }
}