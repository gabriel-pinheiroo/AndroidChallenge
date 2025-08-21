package com.maximatech.provaandroid.presentation.features.orders

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.maximatech.provaandroid.app.LocalTopBarManager
import com.maximatech.provaandroid.core.domain.model.Order
import com.maximatech.provaandroid.features.orders.OrdersState
import com.maximatech.provaandroid.features.orders.OrdersViewModel
import com.maximatech.provaandroid.presentation.designSystem.tokens.AppColors
import com.maximatech.provaandroid.presentation.designSystem.components.legends.LegendsDialog
import org.koin.androidx.compose.koinViewModel

@Composable
fun OrdersRoute(
    modifier: Modifier = Modifier,
    viewModel: OrdersViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getOrders()
    }

    OrdersScreen(
        modifier = modifier,
        state = state,
        onRetry = viewModel::getOrders
    )
}

@Composable
fun OrdersScreen(
    modifier: Modifier = Modifier,
    state: OrdersState,
    onRetry: () -> Unit = {}
) {
    val topBarManager = LocalTopBarManager.current
    var showLegendsDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        topBarManager.showTopBar()
        topBarManager.setTopBarConfig {
            this.title = "MaxApp"
            showTitle = true
            showMenuButton = true
            this.onMenuClicked = {
                showLegendsDialog = true
            }
        }
    }

    if (state.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = AppColors.Primary
            )
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(AppColors.CardBackground)
                .padding(16.dp)
        ) {
            when {
                state.hasError -> {
                    ErrorCard(
                        message = state.errorMessage,
                        onRetry = onRetry
                    )
                }

                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(state.orders) { order ->
                            OrderCard(
                                order = order
                            )
                        }
                    }
                }
            }
        }
    }

    if (showLegendsDialog) {
        LegendsDialog(
            onDismiss = { showLegendsDialog = false }
        )
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
                if (order.tipo == "ORCAMENTO") {
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
    val iconRes = OrderDialogLegendIconHelper.getLegendaIconResource(legenda)

    iconRes?.let { icon ->
        Image(
            painter = painterResource(id = icon),
            contentDescription = "",
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
    val iconConfig = OrderLegendIconHelper.getLegendIconConfig(legendas, tipo, status)

    if (iconConfig.showIcon && iconConfig.iconResource != null) {
        Box(
            modifier = modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(androidx.compose.ui.graphics.Color(iconConfig.backgroundColor)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = iconConfig.iconResource),
                contentDescription = "Aguardando Crítica",
                modifier = Modifier.size(24.dp)
            )
        }
    } else {
        Box(
            modifier = modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(androidx.compose.ui.graphics.Color(iconConfig.backgroundColor)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = iconConfig.text ?: "?",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                ),
                color = androidx.compose.ui.graphics.Color(iconConfig.textColor),
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
    val icon = OrderCriticaIconHelper.getCriticaIconResource(critica)

    Image(
        painter = painterResource(id = icon),
        contentDescription = OrderCriticaIconHelper.getCriticaDescription(critica),
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