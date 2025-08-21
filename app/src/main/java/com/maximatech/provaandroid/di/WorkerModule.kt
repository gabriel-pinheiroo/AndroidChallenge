package com.maximatech.provaandroid.di

import org.koin.dsl.module
import com.maximatech.provaandroid.core.data.worker.SyncManager
import org.koin.android.ext.koin.androidContext

val workerModule = module {

    single {
        SyncManager(androidContext())
    }

}