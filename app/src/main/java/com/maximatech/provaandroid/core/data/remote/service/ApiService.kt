package com.maximatech.provaandroid.core.data.remote.service

import com.maximatech.provaandroid.core.data.remote.dto.ApiClientResponse
import retrofit2.http.GET

interface ApiService {

    @GET("cliente")
    suspend fun getClient(
    ): ApiClientResponse
}