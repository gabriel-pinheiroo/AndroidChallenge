package com.maximatech.provaandroid.core.domain.usecase

import com.maximatech.provaandroid.core.domain.model.Order
import com.maximatech.provaandroid.core.domain.repository.OrdersRepository

class GetOrdersUseCase(
    private val ordersRepository: OrdersRepository
) {
    suspend operator fun invoke(): Result<List<Order>> {
        return ordersRepository.getOrders()
    }
}