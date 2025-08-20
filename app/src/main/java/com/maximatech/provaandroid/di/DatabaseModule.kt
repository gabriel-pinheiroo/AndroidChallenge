package com.maximatech.provaandroid.di

import org.koin.dsl.module
import org.koin.android.ext.koin.androidContext
import com.maximatech.provaandroid.core.data.local.database.AppDatabase
import com.maximatech.provaandroid.core.data.local.datasource.ClientLocalDataSource
import com.maximatech.provaandroid.core.data.local.datasource.OrderLocalDataSource

val databaseModule = module {

    single { AppDatabase.getDatabase(androidContext()) }

    single { get<AppDatabase>().clientDao() }
    single { get<AppDatabase>().orderDao() }

    single { ClientLocalDataSource(get()) }
    single { OrderLocalDataSource(get()) }
}