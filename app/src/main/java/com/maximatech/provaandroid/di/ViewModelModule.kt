package com.maximatech.provaandroid.di

import com.maximatech.provaandroid.app.MainActivityViewModel
import com.maximatech.provaandroid.presentation.features.clients.ClientViewModel
import com.maximatech.provaandroid.presentation.features.clientDetails.ClientDetailsViewModel
import com.maximatech.provaandroid.presentation.features.orders.OrdersViewModel
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

    viewModel {
        ClientDetailsViewModel(
            getClientUseCase = get()
        )
    }
}