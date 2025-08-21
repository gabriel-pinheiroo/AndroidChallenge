package com.maximatech.provaandroid.core.data.worker

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

class SyncManager(private val context: Context) {

    companion object {
        private const val WORK_NAME = "data_sync"
    }

    private val workManager: WorkManager by lazy {
        WorkManager.getInstance(context)
    }

    fun startPeriodicSync() {
        workManager.cancelUniqueWork(WORK_NAME)

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<DataSyncWorker>(
            15, TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .setInitialDelay(15, TimeUnit.MINUTES)
            .build()

        workManager.enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )
    }

    fun stopSync() {
        workManager.cancelUniqueWork(WORK_NAME)
    }
}