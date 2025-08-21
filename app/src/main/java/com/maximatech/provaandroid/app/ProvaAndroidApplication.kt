package com.maximatech.provaandroid.app

import android.app.Application
import androidx.work.WorkManager
import com.maximatech.provaandroid.BuildConfig
import com.maximatech.provaandroid.core.data.worker.SyncManager
import com.maximatech.provaandroid.di.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ProvaAndroidApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()

        cleanupWorkers()
        setupKoin()
        startSyncAsync()
    }

    private fun cleanupWorkers() {
        try {
            val workManager = WorkManager.getInstance(this)
            workManager.cancelAllWork()
            workManager.pruneWork()
        } catch (e: Exception) {
            println(e)
        }
    }

    private fun setupKoin() {
        startKoin {
            if (BuildConfig.DEBUG) {
                androidLogger(Level.DEBUG)
            }
            androidContext(this@ProvaAndroidApplication)
            modules(
                appModule,
                databaseModule,
                networkModule,
                repositoryModule,
                useCaseModule,
                viewModelModule,
                workerModule
            )
        }
    }

    private fun startSyncAsync() {
        applicationScope.launch {
            try {
                delay(2000)

                val koin = org.koin.core.context.GlobalContext.get()
                val syncManager: SyncManager = koin.get()
                syncManager.startPeriodicSync()

            } catch (e: Exception) {
                println(e)
            }
        }
    }
}