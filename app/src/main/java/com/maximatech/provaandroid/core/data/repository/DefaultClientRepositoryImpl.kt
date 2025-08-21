package com.maximatech.provaandroid.core.data.repository

import com.maximatech.provaandroid.core.data.local.datasource.ClientLocalDataSource
import com.maximatech.provaandroid.core.data.remote.service.ApiService
import com.maximatech.provaandroid.core.data.network.NetworkConnectivityManager
import com.maximatech.provaandroid.core.domain.repository.ClientRepository
import com.maximatech.provaandroid.core.domain.model.Client
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext

class DefaultClientRepositoryImpl(
    private val api: ApiService,
    private val localDataSource: ClientLocalDataSource,
    private val networkConnectivityManager: NetworkConnectivityManager
) : ClientRepository {

    override suspend fun getClient(): Result<Client> {
        return try {
            val isConnected = networkConnectivityManager.isCurrentlyConnected()

            if (isConnected) {
                getClientFromNetwork()
            } else {
                getClientFromLocal()
            }
        } catch (exception: Throwable) {
            coroutineContext.ensureActive()
            Result.failure(exception)
        }
    }

    suspend fun syncClientFromNetwork(): Result<Client> {
        return getClientFromNetwork()
    }

    private suspend fun getClientFromNetwork(): Result<Client> {
        return try {
            val response = api.getClient()
            val client = response.cliente?.toClient() ?: Client.EMPTY

            localDataSource.saveClient(client)

            Result.success(client)
        } catch (exception: Throwable) {
            coroutineContext.ensureActive()

            val localResult = getClientFromLocal()
            if (localResult.isSuccess) {
                localResult
            } else {
                Result.failure(exception)
            }
        }
    }

    private suspend fun getClientFromLocal(): Result<Client> {
        return try {
            val client = localDataSource.getClient()
                Result.success(client)
        } catch (exception: Throwable) {
            Result.failure(exception)
        }
    }
}