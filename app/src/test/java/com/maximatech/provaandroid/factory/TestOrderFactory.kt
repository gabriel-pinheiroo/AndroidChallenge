package com.maximatech.provaandroid.factory

import com.maximatech.provaandroid.core.domain.model.Order

object TestOrderFactory {

    fun createDefaultOrder(): Order {
        return Order(
            123,
            "ERP-001",
            "CLI-001",
            "Cliente Padrão",
            "2024-01-15",
            "pendente",
            "Nenhuma",
            "venda",
            mutableListOf("NOVA", "URGENTE")
        )
    }

    fun createOrderWithStatus(status: String): Order {
        return Order(
            456,
            "ERP-002",
            "CLI-002",
            "Cliente $status",
            "2024-01-16",
            status,
            "Crítica $status",
            "venda",
            mutableListOf(status.uppercase())
        )
    }

    fun createOrderWithNumber(numeroPedRca: Int): Order {
        return Order(
            numeroPedRca,
            "ERP-$numeroPedRca",
            "CLI-$numeroPedRca",
            "Cliente $numeroPedRca",
            "2024-01-17",
            "ativo",
            "Nenhuma",
            "venda",
            mutableListOf("PEDIDO_$numeroPedRca")
        )
    }

    fun createMultipleOrders(count: Int): List<Order> {
        return (1..count).map { i ->
            createOrderWithNumber(100 + i)
        }
    }

    fun createEmptyOrder(): Order {
        return Order.EMPTY
    }
}