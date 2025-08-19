package com.maximatech.provaandroid.di

import DefaultClientRepositoryImpl
import com.maximatech.provaandroid.core.domain.repository.ClientRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<ClientRepository> {
        DefaultClientRepositoryImpl(
            api = get()
        )
    }
}