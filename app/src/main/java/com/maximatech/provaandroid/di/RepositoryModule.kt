package com.maximatech.provaandroid.di

import DefaultClientRepositoryImpl
import com.maximatech.provaandroid.core.data.repository.DefaultOrdersRepositoryImpl
import com.maximatech.provaandroid.core.domain.repository.ClientRepository
import com.maximatech.provaandroid.core.domain.repository.OrdersRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<ClientRepository> {
        DefaultClientRepositoryImpl(
            api = get(),
            localDataSource = get(),
            networkConnectivityManager = get()
        )
    }

    single<OrdersRepository> {
        DefaultOrdersRepositoryImpl(
            api = get(),
            localDataSource = get(),
            networkConnectivityManager = get()
        )
    }
}