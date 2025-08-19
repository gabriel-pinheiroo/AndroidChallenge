package com.maximatech.provaandroid.di

import com.maximatech.provaandroid.MainActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { MainActivityViewModel() }
}