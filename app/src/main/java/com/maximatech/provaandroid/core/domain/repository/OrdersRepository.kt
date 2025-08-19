package com.maximatech.provaandroid.core.domain.repository

import com.maximatech.provaandroid.core.domain.model.Order

interface OrdersRepository {
    suspend fun getOrders(): Result<List<Order>>
}