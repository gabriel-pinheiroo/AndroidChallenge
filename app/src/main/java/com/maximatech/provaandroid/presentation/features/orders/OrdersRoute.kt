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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.maximatech.provaandroid.app.LocalTopBarManager
import com.maximatech.provaandroid.core.domain.model.Order
import com.maximatech.provaandroid.features.orders.OrdersState
import com.maximatech.provaandroid.features.orders.OrdersViewModel
import com.maximatech.provaandroid.presentation.designSystem.tokens.AppColors
import com.maximatech.provaandroid.presentation.designSystem.tokens.*
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
                .padding(top = ExtraHugeSpacing)
                .padding(MediumSpacing)
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
                        verticalArrangement = Arrangement.spacedBy(SmallSpacing)
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
            defaultElevation = LowElevation
        ),
        shape = RoundedCornerShape(MediumRadius)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MediumSpacing),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LegendIcon(
                legendas = order.legendas,
                tipo = order.tipo,
                status = order.status
            )

            Spacer(modifier = Modifier.width(SmallMediumSpacing))

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
                                fontSize = SmallFontSize
                            ),
                            color = AppColors.OnSurfaceLight
                        )
                        Text(
                            text = "${order.numeroPedRca} / ${order.numeroPedErp}",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = SmallFontSize
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
                                fontSize = SmallFontSize
                            ),
                            color = AppColors.OnSurfaceLight,
                        )
                        Text(
                            text = "${order.numeroPedRca} / ${order.numeroPedErp}",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = SmallFontSize
                            ),
                            color = AppColors.OnSurfaceHigh
                        )
                    }
                }

                Spacer(modifier = Modifier.height(DoubleSpacing))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Cliente: ",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = SmallFontSize,
                            fontWeight = FontWeight.Bold,
                        ),
                        color = AppColors.OnSurfaceLight,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "${order.codigoCliente} - ${order.nomeCliente}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = SmallFontSize,
                            fontWeight = FontWeight.Bold,
                        ),
                        color = AppColors.OnSurfaceHigh,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(DoubleSpacing))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = order.status,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = SmallFontSize,
                            fontWeight = FontWeight.Bold
                        ),
                        color = AppColors.OnSurfaceHigh
                    )

                    if (order.critica.isNotEmpty()) {
                        Spacer(modifier = Modifier.width(HugeSpacing))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "Crítica: ",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = SmallFontSize,
                                    fontWeight = FontWeight.Bold
                                ),
                                color = AppColors.OnSurfaceLight
                            )
                            CriticaIcon(critica = order.critica)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.width(SmallSpacing))

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = formatTime(order.data),
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = TinyFontSize
                    ),
                    color = AppColors.OnSurfaceLight
                )

                if (order.legendas.isNotEmpty()) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(TinySpacing),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        order.legendas.forEach { legenda ->
                            DialogLegendIcon(legenda = legenda)
                        }
                    }
                    Spacer(modifier = Modifier.height(TinySpacing))
                }

                Text(
                    text = "R$ 617,40",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = SmallFontSize
                    ),
                    color = AppColors.OnSurfaceHigh
                )
            }
        }
    }
}

@Composable
private fun DialogLegendIcon(
    legenda: String,
    modifier: Modifier = Modifier
) {
    val iconRes = OrderDialogLegendIconHelper.getLegendaIconResource(legenda)

    iconRes?.let { icon ->
        Image(
            painter = painterResource(id = icon),
            contentDescription = "",
            modifier = modifier.size(IconSizeMedium)
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
                .size(CircularIconSize)
                .clip(CircleShape)
                .background(androidx.compose.ui.graphics.Color(iconConfig.backgroundColor)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = iconConfig.iconResource),
                contentDescription = "Aguardando Crítica",
                modifier = Modifier.size(IconSizeLarge)
            )
        }
    } else {
        Box(
            modifier = modifier
                .size(CircularIconSize)
                .clip(CircleShape)
                .background(androidx.compose.ui.graphics.Color(iconConfig.backgroundColor)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = iconConfig.text ?: "?",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = SubtitleFontSize
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
        modifier = modifier.size(IconSizeSmall)
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
            defaultElevation = LowElevation
        ),
        shape = RoundedCornerShape(MediumRadius)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MediumSpacing),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Erro ao carregar pedidos",
                style = MaterialTheme.typography.titleMedium,
                color = AppColors.Error
            )

            Spacer(modifier = Modifier.height(SmallSpacing))

            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = AppColors.OnSurfaceMedium
            )

            Spacer(modifier = Modifier.height(MediumSpacing))

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