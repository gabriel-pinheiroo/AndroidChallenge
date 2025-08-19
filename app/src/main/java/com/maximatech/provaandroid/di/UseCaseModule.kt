package com.maximatech.provaandroid.di

import com.maximatech.provaandroid.core.domain.usecase.GetClientUseCase
import com.maximatech.provaandroid.core.domain.usecase.GetOrdersUseCase
import org.koin.dsl.module

val useCaseModule = module {

    factory {
        GetClientUseCase(
            clientRepository = get()
        )
    }

    factory {
        GetOrdersUseCase(
            ordersRepository = get()
        )
    }
}