package com.maximatech.provaandroid.di

import com.maximatech.provaandroid.MainActivityViewModel
import com.maximatech.provaandroid.features.client.ClientViewModel
import com.maximatech.provaandroid.features.orders.OrdersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainActivityViewModel() }

    viewModel {
        ClientViewModel(
            getClientUseCase = get()
        )
    }

    viewModel {
        OrdersViewModel(
            getOrdersUseCase = get()
        )
    }
}