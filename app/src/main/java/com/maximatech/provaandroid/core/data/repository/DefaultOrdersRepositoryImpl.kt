package com.maximatech.provaandroid.core.data.repository

import com.maximatech.provaandroid.core.data.remote.service.ApiService
import com.maximatech.provaandroid.core.domain.model.Order
import com.maximatech.provaandroid.core.domain.repository.OrdersRepository

class DefaultOrdersRepositoryImpl(
    private val api: ApiService
) : OrdersRepository {

    override suspend fun getOrders(): Result<List<Order>> {
        return try {
            val response = api.getOrders()
            val orders = response.pedidos?.map { it.toOrder() } ?: emptyList()
            Result.success(orders)
        } catch (exception: Throwable) {
            Result.failure(exception)
        }
    }
}