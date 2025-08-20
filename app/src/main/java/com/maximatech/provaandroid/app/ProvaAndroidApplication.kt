package com.maximatech.provaandroid.app

import android.app.Application
import com.maximatech.provaandroid.BuildConfig
import com.maximatech.provaandroid.di.appModule
import com.maximatech.provaandroid.di.databaseModule
import com.maximatech.provaandroid.di.networkModule
import com.maximatech.provaandroid.di.repositoryModule
import com.maximatech.provaandroid.di.useCaseModule
import com.maximatech.provaandroid.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ProvaAndroidApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupKoin()
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
                viewModelModule
            )
        }
    }
}