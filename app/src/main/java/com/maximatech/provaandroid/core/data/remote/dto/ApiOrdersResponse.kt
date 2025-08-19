package com.maximatech.provaandroid.core.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.maximatech.provaandroid.core.domain.model.Order

data class ApiOrdersResponse(
    @SerializedName("pedidos")
    val pedidos: List<ApiOrder>? = null
)

data class ApiOrder(
    @SerializedName("numero_ped_Rca")
    val numeroPedRca: Int? = null,
    @SerializedName("numero_ped_erp")
    val numeroPedErp: String? = null,
    @SerializedName("codigoCliente")
    val codigoCliente: String? = null,
    @SerializedName("NOMECLIENTE")
    val nomeCliente: String? = null,
    @SerializedName("data")
    val data: String? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("critica")
    val critica: String? = null,
    @SerializedName("tipo")
    val tipo: String? = null,
    @SerializedName("legendas")
    val legendas: List<String>? = null
){
    fun toOrder(): Order {
        return Order(
            numeroPedRca = numeroPedRca ?: 0,
            numeroPedErp = numeroPedErp.orEmpty(),
            codigoCliente = codigoCliente.orEmpty(),
            nomeCliente = nomeCliente.orEmpty(),
            data = data.orEmpty(),
            status = status.orEmpty(),
            critica = critica.orEmpty(),
            tipo = tipo.orEmpty(),
            legendas = legendas ?: emptyList()
        )
    }
}