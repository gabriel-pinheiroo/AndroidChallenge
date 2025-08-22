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
            Result.failure(exception)
        }
    }

    override suspend fun getOrdersFromNetwork(): Result<List<Order>> {
        return try {
            val response = api.getOrders()
            val orders = response.getPedidos()?.map { it.toOrder() } ?: emptyList()

            localDataSource.saveOrders(orders)

            Result.success(orders)
        } catch (exception: Throwable) {
            coroutineContext.ensureActive()
            getOrdersFromLocal()
        }
    }

    private suspend fun getOrdersFromLocal(): Result<List<Order>> {
        return try {
            val orders: List<Order> = localDataSource.getOrders()
            Result.success(orders)
        } catch (exception: Throwable) {
            Result.failure(exception)
        }
    }
}