package com.maximatech.provaandroid.di

import com.maximatech.provaandroid.core.data.repository.DefaultOrdersRepositoryImpl
import com.maximatech.provaandroid.core.data.repository.DefaultClientRepositoryImpl
import com.maximatech.provaandroid.core.domain.repository.ClientRepository
import com.maximatech.provaandroid.core.domain.repository.OrdersRepository
import org.koin.dsl.module

val repositoryModule = module {

    single {
        DefaultClientRepositoryImpl(
            api = get(),
            localDataSource = get(),
            networkConnectivityManager = get()
        )
    }

    single {
        DefaultOrdersRepositoryImpl(
            api = get(),
            localDataSource = get(),
            networkConnectivityManager = get()
        )
    }

    single<ClientRepository> {
        get<DefaultClientRepositoryImpl>()
    }

    single<OrdersRepository> {
        get<DefaultOrdersRepositoryImpl>()
    }
}