package com.maximatech.provaandroid.presentation.features.orders

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maximatech.provaandroid.LocalTopBarManager
import com.maximatech.provaandroid.R
import com.maximatech.provaandroid.core.domain.model.Order
import com.maximatech.provaandroid.features.orders.OrdersViewModel
import com.maximatech.provaandroid.presentation.designSystem.tokens.AppColors
import com.maximatech.provaandroid.presentation.designSystem.tokens.AppColors.Background
import com.maximatech.provaandroid.presentation.designSystem.tokens.OrderStatusColors.Blocked
import com.maximatech.provaandroid.presentation.designSystem.tokens.OrderStatusColors.Budget
import com.maximatech.provaandroid.presentation.designSystem.tokens.OrderStatusColors.Canceled
import com.maximatech.provaandroid.presentation.designSystem.tokens.OrderStatusColors.Invoiced
import com.maximatech.provaandroid.presentation.designSystem.tokens.OrderStatusColors.Mounted
import com.maximatech.provaandroid.presentation.designSystem.tokens.OrderStatusColors.Pending
import com.maximatech.provaandroid.presentation.designSystem.tokens.OrderStatusColors.Processing
import com.maximatech.provaandroid.presentation.designSystem.tokens.OrderStatusColors.Rejected
import com.maximatech.provaandroid.presentation.designSystem.tokens.OrderStatusColors.Released
import org.koin.androidx.compose.koinViewModel

@Composable
fun OrdersRoute(
    modifier: Modifier = Modifier,
    viewModel: OrdersViewModel = koinViewModel()
) {
    val topBarManager = LocalTopBarManager.current
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        topBarManager.showTopBar()
        topBarManager.setTopBarConfig {
            this.title = "MaxApp"
            showTitle = true
            showMenuButton = true
            this.onMenuClicked = {
            }
        }
        viewModel.getOrders()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.CardBackground)
            .padding(16.dp)
    ) {
        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = AppColors.Primary
                    )
                }
            }

            state.hasError -> {
                ErrorCard(
                    message = state.errorMessage,
                    onRetry = { viewModel.getOrders() }
                )
            }

            else -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.orders) { order ->
                        OrderCard(
                            order = order,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun OrderCard(
    order: Order,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = AppColors.Surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LegendIcon(
                legendas = order.legendas,
                tipo = order.tipo,
                status = order.status
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                if(order.tipo == "ORCAMENTO") {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "Nº Orca. RCA/ERP: ",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            ),
                            color = AppColors.OnSurfaceLight
                        )
                        Text(
                            text = "${order.numeroPedRca} / ${order.numeroPedErp}",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            ),
                            color = AppColors.OnSurfaceHigh
                        )
                    }
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "Nº Ped. RCA/ERP: ",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            ),
                            color = AppColors.OnSurfaceLight,
                        )
                        Text(
                            text = "${order.numeroPedRca} / ${order.numeroPedErp}",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            ),
                            color = AppColors.OnSurfaceHigh
                        )
                    }
                }


                Spacer(modifier = Modifier.height(2.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Cliente: ",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                        ),
                        color = AppColors.OnSurfaceLight,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "${order.codigoCliente} - ${order.nomeCliente}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                        ),
                        color = AppColors.OnSurfaceHigh,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = order.status,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = AppColors.OnSurfaceHigh
                    )

                    if (order.critica.isNotEmpty()) {
                        Spacer(modifier = Modifier.width(64.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "Crítica: ",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                color = AppColors.OnSurfaceLight
                            )
                            CriticaIcon(critica = order.critica)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = formatTime(order.data),
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 10.sp
                    ),
                    color = AppColors.OnSurfaceLight
                )

                if (order.legendas.isNotEmpty()) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        order.legendas.forEach { legenda ->
                            LegendaIcon(legenda = legenda)
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }

                Text(
                    text = "R$ 617,40",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    ),
                    color = AppColors.OnSurfaceHigh
                )
            }
        }
    }
}

@Composable
private fun LegendaIcon(
    legenda: String,
    modifier: Modifier = Modifier
) {
    val iconRes = when (legenda) {
        "PEDIDO_SOFREU_CORTE" -> R.drawable.ic_maxima_legenda_corte
        "PEDIDO_COM_FALTA" -> R.drawable.ic_maxima_legenda_falta
        "PEDIDO_CANCELADO_ERP" -> R.drawable.ic_maxima_legenda_cancelamento
        "PEDIDO_COM_DEVOLUCAO" -> R.drawable.ic_maxima_legenda_devolucao
        "PEDIDO_FEITO_TELEMARKETING" -> R.drawable.ic_maxima_legenda_telemarketing
        else -> null
    }

    iconRes?.let { icon ->
        Image(
            painter = painterResource(id = icon),
            contentDescription = legenda,
            modifier = modifier.size(16.dp)
        )
    }
}

@Composable
private fun LegendIcon(
    legendas: List<String>,
    tipo: String,
    status: String,
    modifier: Modifier = Modifier
) {
    val (backgroundColor, textColor, text) = when {

        status.contains("processamento", ignoreCase = true) ->
            Triple(Processing, Background, null)

        legendas.contains("PEDIDO_RECUSADO_ERP") ->
            Triple(Rejected, Background, "!")

        status.equals("Pendente", ignoreCase = true) ->
            Triple(Pending, Background, "P")

        legendas.contains("PEDIDO_PENDENTE_ERP") ->
            Triple(Pending, Background, "P")

        legendas.contains("PEDIDO_BLOQUEADO_ERP") ->
            Triple(Blocked, Background, "B")

        legendas.contains("PEDIDO_LIBERADO_ERP") ->
            Triple(Released, Background, "L")

        legendas.contains("PEDIDO_MONTADO_ERP") ->
            Triple(Mounted, Background, "M")

        legendas.contains("PEDIDO_FATURADO_ERP") ->
            Triple(Invoiced, Background, "F")

        legendas.contains("PEDIDO_CANCELADO_ERP") ->
            Triple(Canceled, Background, "C")

        tipo.equals("ORCAMENTO", ignoreCase = true) ->
            Triple(Budget, Background, "O")

        else -> {
            val firstChar = status.firstOrNull()?.toString()?.uppercase() ?: "?"
            Triple(AppColors.Primary, Background, firstChar)
        }
    }

    if( text == null) {
        Box(
            modifier = modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(backgroundColor),
            contentAlignment = Alignment.Center
        ) {
        Image(
            painter = painterResource(id = R.drawable.ic_maxima_em_processamento),
            contentDescription = "Aguardando Crítica",
            modifier = Modifier.size(24.dp)
        )
        }
    }else {
        Box(
            modifier = modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                ),
                color = textColor,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun CriticaIcon(
    critica: String,
    modifier: Modifier = Modifier
) {
    val icon = when (critica.uppercase()) {
        "SUCESSO" -> R.drawable.ic_maxima_critica_sucesso
        "FALHA_PARCIAL" -> R.drawable.ic_maxima_critica_alerta
        "FALHA_TOTAL" -> R.drawable.ic_maxima_legenda_cancelamento
        else -> R.drawable.ic_maxima_aguardando_critica
    }

    Image(
        painter = painterResource(id = icon),
        contentDescription = critica,
        modifier = modifier.size(14.dp)
    )
}

@Composable
private fun ErrorCard(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = AppColors.Surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Erro ao carregar pedidos",
                style = MaterialTheme.typography.titleMedium,
                color = AppColors.Error
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = AppColors.OnSurfaceMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.Primary
                )
            ) {
                Text(
                    text = "Tentar novamente",
                    color = AppColors.OnPrimary
                )
            }
        }
    }
}

private fun formatTime(dateString: String): String {
    return try {
        if (dateString.contains("T") && dateString.length >= 16) {
            dateString.substring(11, 16)
        } else {
            "00:00"
        }
    } catch (e: Exception) {
        "00:00"
    }
}