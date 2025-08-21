package com.maximatech.provaandroid.core.domain.repository

import com.maximatech.provaandroid.core.domain.model.Client

interface ClientRepository {
    suspend fun getClient(): Result<Client>
    suspend fun getClientFromNetwork(): Result<Client>
}
