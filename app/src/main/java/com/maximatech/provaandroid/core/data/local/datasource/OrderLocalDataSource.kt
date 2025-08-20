package com.maximatech.provaandroid.core.data.local.datasource

import com.maximatech.provaandroid.core.data.local.dao.OrderDao
import com.maximatech.provaandroid.core.data.local.entity.OrderEntity
import com.maximatech.provaandroid.core.domain.model.Order

class OrderLocalDataSource(
    private val orderDao: OrderDao
) {

    suspend fun saveOrders(orders: List<Order>) {
        val orderEntities = orders.map { OrderEntity.fromOrder(it) }
        orderDao.saveOrders(orderEntities)
    }

    suspend fun getOrders(): List<Order> {
        return orderDao.getAllOrders().map { it.toOrder() }
    }
}