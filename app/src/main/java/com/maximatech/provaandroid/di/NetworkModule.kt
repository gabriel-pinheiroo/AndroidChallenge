package com.maximatech.provaandroid.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.maximatech.provaandroid.core.data.network.DefaultNetworkConnectivityManager
import com.maximatech.provaandroid.core.data.network.NetworkConnectivityManager
import com.maximatech.provaandroid.core.data.remote.service.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    single<Gson> {
        GsonBuilder()
            .setLenient()
            .create()
    }

    single<HttpLoggingInterceptor> {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("https://private-anon-93f006ab9d-maximatech.apiary-mock.com/android/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }

    single<ApiService> {
        get<Retrofit>().create(ApiService::class.java)
    }

    single<NetworkConnectivityManager> {
        DefaultNetworkConnectivityManager(androidContext())
    }
}