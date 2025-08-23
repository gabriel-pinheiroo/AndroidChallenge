package com.maximatech.provaandroid.app

import android.app.Application
import com.maximatech.provaandroid.BuildConfig
import com.maximatech.provaandroid.core.data.worker.SyncManager
import com.maximatech.provaandroid.di.*
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ProvaAndroidApplication : Application() {

    private val syncManager: SyncManager by inject()

    override fun onCreate() {
        super.onCreate()

        setupKoin()
        initializeDataSync()
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

    private fun initializeDataSync() {
        try {
            syncManager.initialize()
        } catch (e: Exception) {
            println(e)
        }
    }
}