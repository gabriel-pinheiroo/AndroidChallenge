package com.maximatech.provaandroid.core.domain.model

data class Order(
    val numeroPedRca: Int,
    val numeroPedErp: String,
    val codigoCliente: String,
    val nomeCliente: String,
    val data: String,
    val status: String,
    val critica: String,
    val tipo: String,
    val legendas: List<String>
) {
    companion object {
        val EMPTY = Order(
            numeroPedRca = 0,
            numeroPedErp = "",
            codigoCliente = "",
            nomeCliente = "",
            data = "",
            status = "",
            critica = "",
            tipo = "",
            legendas = emptyList()
        )
    }
}

enum class OrderStatus {
    PROCESSADO,
    EM_PROCESSAMENTO,
    PENDENTE,
    CANCELADO;

    companion object {
        fun fromString(status: String): OrderStatus {
            return when (status.uppercase()) {
                "PROCESSADO" -> PROCESSADO
                "EM PROCESSAMENTO" -> EM_PROCESSAMENTO
                "PENDENTE" -> PENDENTE
                "CANCELADO" -> CANCELADO
                else -> PENDENTE
            }
        }
    }
}

enum class OrderType {
    PEDIDO,
    ORCAMENTO;

    companion object {
        fun fromString(type: String): OrderType {
            return when (type.uppercase()) {
                "PEDIDO" -> PEDIDO
                "ORCAMENTO" -> ORCAMENTO
                else -> PEDIDO
            }
        }
    }
}

enum class OrderLegend(val displayName: String) {
    PEDIDO_SOFREU_CORTE("Pedido sofreu corte"),
    PEDIDO_FEITO_TELEMARKETING("Pedido feito pelo Telemarketing"),
    PEDIDO_CANCELADO_ERP("Pedido cancelado no ERP"),
    PEDIDO_COM_FALTA("Pedido com falta"),
    PEDIDO_COM_DEVOLUCAO("Pedido com devolução"),
    EM_PROCESSAMENTO_FV("Em processamento por parte do FV"),
    PEDIDO_RECUSADO_ERP("Pedido recusado pelo ERP"),
    POSICAO_ERP_PENDENTE("Posição no ERP Pendente"),
    POSICAO_ERP_BLOQUEADO("Posição no ERP Bloqueado"),
    POSICAO_ERP_LIBERADO("Posição no ERP Liberado"),
    POSICAO_ERP_MONTADO("Posição no ERP Montado"),
    POSICAO_ERP_FATURADO("Posição no ERP Faturado"),
    POSICAO_ERP_CANCELADO("Posição no ERP Cancelado");

    companion object {
        fun fromString(legend: String): OrderLegend? {
            return values().find { it.name == legend }
        }
    }
}