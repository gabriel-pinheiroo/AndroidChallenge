package com.maximatech.provaandroid.core.data.repository

import com.maximatech.provaandroid.core.data.remote.service.ApiService
import com.maximatech.provaandroid.core.data.local.datasource.OrderLocalDataSource
import com.maximatech.provaandroid.core.data.network.NetworkConnectivityManager
import com.maximatech.provaandroid.core.domain.model.Order
import com.maximatech.provaandroid.core.domain.repository.OrdersRepository
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext

class DefaultOrdersRepositoryImpl(
    private val api: ApiService,
    private val localDataSource: OrderLocalDataSource,
    private val networkConnectivityManager: NetworkConnectivityManager
) : OrdersRepository {

    override suspend fun getOrders(): Result<List<Order>> {
        return try {
            val isConnected = networkConnectivityManager.isCurrentlyConnected()

            if (isConnected) {
                getOrdersFromNetwork()
            } else {
                getOrdersFromLocal()
            }
        } catch (exception: Throwable) {
            coroutineContext.ensureActive()
            getOrdersFromLocal().takeIf { it.isSuccess } ?: Result.failure(exception)
        }
    }

    suspend fun syncOrdersFromNetwork(): Result<List<Order>> {
        return getOrdersFromNetwork()
    }

    private suspend fun getOrdersFromNetwork(): Result<List<Order>> {
        return try {
            val response = api.getOrders()
            val orders = response.pedidos?.map { it.toOrder() } ?: emptyList()

            localDataSource.saveOrders(orders)

            Result.success(orders)
        } catch (exception: Throwable) {
            coroutineContext.ensureActive()

            val localResult = getOrdersFromLocal()
            if (localResult.isSuccess) {
                localResult
            } else {
                Result.failure(exception)
            }
        }
    }

    private suspend fun getOrdersFromLocal(): Result<List<Order>> {
        return try {
            val orders = localDataSource.getOrders()
            if (orders.isNotEmpty()) {
                Result.success(orders)
            } else {
                Result.failure(Exception("Nenhum dado local encontrado. Conecte-se à internet para sincronizar."))
            }
        } catch (exception: Throwable) {
            Result.failure(exception)
        }
    }
}