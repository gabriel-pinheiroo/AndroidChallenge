package com.maximatech.provaandroid.core.data.repository

import com.maximatech.provaandroid.core.data.remote.service.ApiService
import com.maximatech.provaandroid.core.data.local.datasource.OrderLocalDataSource
import com.maximatech.provaandroid.core.domain.model.Order
import com.maximatech.provaandroid.core.domain.repository.OrdersRepository
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext

class DefaultOrdersRepositoryImpl(
    private val api: ApiService,
    private val localDataSource: OrderLocalDataSource
) : OrdersRepository {

    override suspend fun getOrders(): Result<List<Order>> {
        return try {
            val response = api.getOrders()
            val orders = response.pedidos?.map { it.toOrder() } ?: emptyList()

            localDataSource.saveOrders(orders)

            Result.success(orders)
        } catch (exception: Throwable) {
            coroutineContext.ensureActive()
            Result.failure(exception)
        }
    }

    suspend fun getOrdersFromLocal(): Result<List<Order>> {
        return try {
            val orders = localDataSource.getOrders()
            Result.success(orders)
        } catch (exception: Throwable) {
            Result.failure(exception)
        }
    }
}