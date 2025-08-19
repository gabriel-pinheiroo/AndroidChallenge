package com.maximatech.provaandroid.presentation.designSystem.tokens

import androidx.compose.ui.graphics.Color

/**
 * Cores específicas para status de pedidos - Sem dark theme
 */
object OrderStatusColors {

    /**
     * Em processamento por parte do FV - cinza claro
     */
    val Processing: Color = Color.OrderStatusProcessing

    /**
     * Pedido recusado pelo ERP - amarelo
     */
    val Rejected: Color = Color.OrderStatusRejected

    /**
     * Pendente - cinza
     */
    val Pending: Color = Color.OrderStatusPending

    /**
     * Bloqueado - roxo
     */
    val Blocked: Color = Color.OrderStatusBlocked

    /**
     * Liberado - azul
     */
    val Released: Color = Color.OrderStatusReleased

    /**
     * Montado - verde claro
     */
    val Mounted: Color = Color.OrderStatusMounted

    /**
     * Faturado - verde escuro
     */
    val Invoiced: Color = Color.OrderStatusInvoiced

    /**
     * Cancelado - vermelho
     */
    val Canceled: Color = Color.OrderStatusCanceled

    /**
     * Orçamento - cinza escuro
     */
    val Budget: Color = Color.OrderStatusBudget
}

/**
 * Cores para tipos de crítica - Sem dark theme
 */
object CriticTypeColors {

    /**
     * Aguardando retorno do ERP - cinza
     */
    val WaitingReturn: Color = Color.CriticTypeWaitingReturn

    /**
     * Sucesso - verde
     */
    val Success: Color = Color.CriticTypeSuccess

    /**
     * Falha parcial - amarelo
     */
    val PartialFailure: Color = Color.CriticTypePartialFailure

    /**
     * Falha total - vermelho
     */
    val TotalFailure: Color = Color.CriticTypeTotalFailure
}

/**
 * Cores para legendas - Sem dark theme
 */
object LegendColors {

    /**
     * Pedido sofreu corte - amarelo
     */
    val CutOrder: Color = Color.LegendCutOrder

    /**
     * Pedido com falta - rosa
     */
    val MissingOrder: Color = Color.LegendMissingOrder

    /**
     * Pedido cancelado no ERP - vermelho
     */
    val CanceledErp: Color = Color.LegendCanceledErp

    /**
     * Pedido com devolução - azul
     */
    val WithDevolution: Color = Color.LegendWithDevolution

    /**
     * Pedido feito pelo telemarketing - verde
     */
    val Telemarketing: Color = Color.LegendTelemarketing
}