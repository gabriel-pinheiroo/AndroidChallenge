package com.maximatech.provaandroid.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maximatech.provaandroid.core.domain.model.Order

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey
    val numeroPedRca: Int,
    val numeroPedErp: String,
    val codigoCliente: String,
    val nomeCliente: String,
    val data: String,
    val status: String,
    val critica: String,
    val tipo: String,
    val legendas: String
) {
    fun toOrder(): Order {
        return Order(
            numeroPedRca,
            numeroPedErp,
            codigoCliente,
            nomeCliente,
            data,
            status,
            critica,
            tipo,
            if (legendas.isNotEmpty()) {
                try {
                    com.google.gson.Gson().fromJson(legendas, Array<String>::class.java).toList()
                } catch (e: Exception) {
                    emptyList()
                }
            } else emptyList()
        )
    }

    companion object {
        fun fromOrder(order: Order): OrderEntity {
            return OrderEntity(
                numeroPedRca = order.numeroPedRca,
                numeroPedErp = order.numeroPedErp,
                codigoCliente = order.codigoCliente,
                nomeCliente = order.nomeCliente,
                data = order.data,
                status = order.status,
                critica = order.critica,
                tipo = order.tipo,
                legendas = com.google.gson.Gson().toJson(order.legendas)
            )
        }
    }
}