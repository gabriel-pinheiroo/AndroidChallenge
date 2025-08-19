package com.maximatech.provaandroid.presentation.designSystem.components.legends

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.maximatech.provaandroid.R
import com.maximatech.provaandroid.presentation.designSystem.tokens.AppColors
import com.maximatech.provaandroid.presentation.designSystem.tokens.AppColors.Background
import com.maximatech.provaandroid.presentation.designSystem.tokens.AppColors.OnSurfaceMedium
import com.maximatech.provaandroid.presentation.designSystem.tokens.OrderStatusColors.Blocked
import com.maximatech.provaandroid.presentation.designSystem.tokens.OrderStatusColors.Budget
import com.maximatech.provaandroid.presentation.designSystem.tokens.OrderStatusColors.Canceled
import com.maximatech.provaandroid.presentation.designSystem.tokens.OrderStatusColors.Invoiced
import com.maximatech.provaandroid.presentation.designSystem.tokens.OrderStatusColors.Mounted
import com.maximatech.provaandroid.presentation.designSystem.tokens.OrderStatusColors.Pending
import com.maximatech.provaandroid.presentation.designSystem.tokens.OrderStatusColors.Processing
import com.maximatech.provaandroid.presentation.designSystem.tokens.OrderStatusColors.Rejected
import com.maximatech.provaandroid.presentation.designSystem.tokens.OrderStatusColors.Released

@Composable
fun LegendsDialog(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = AppColors.Surface
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = "Legendas",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    ),
                    color = AppColors.OnSurfaceHigh,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "Status do pedido no ERP",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    ),
                    color = AppColors.OnSurfaceHigh,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    StatusLegendItem(
                        icon = { ProcessingIcon() },
                        text = "Em processamento por parte do FV"
                    )
                    StatusLegendItem(
                        icon = { StatusCircle("!", Rejected) },
                        text = "Pedido recusado pelo ERP"
                    )
                    StatusLegendItem(
                        icon = { StatusCircle("P", Pending) },
                        text = "Posição no ERP Pendente"
                    )
                    StatusLegendItem(
                        icon = { StatusCircle("B", Blocked) },
                        text = "Posição no ERP Bloqueado"
                    )
                    StatusLegendItem(
                        icon = { StatusCircle("L", Released) },
                        text = "Posição no ERP Liberado"
                    )
                    StatusLegendItem(
                        icon = { StatusCircle("M", Mounted) },
                        text = "Posição no ERP Montado"
                    )
                    StatusLegendItem(
                        icon = { StatusCircle("F", Invoiced) },
                        text = "Posição no ERP Faturado"
                    )
                    StatusLegendItem(
                        icon = { StatusCircle("C", Canceled) },
                        text = "Posição no ERP Cancelado"
                    )
                    StatusLegendItem(
                        icon = { StatusCircle("O", Budget) },
                        text = "Orçamento"
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Crítica",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    ),
                    color = AppColors.OnSurfaceHigh,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CriticaLegendItem(
                        iconRes = R.drawable.ic_maxima_aguardando_critica,
                        text = "Aguardando retorno do ERP"
                    )
                    CriticaLegendItem(
                        iconRes = R.drawable.ic_maxima_critica_sucesso,
                        text = "Sucesso"
                    )
                    CriticaLegendItem(
                        iconRes = R.drawable.ic_maxima_critica_alerta,
                        text = "Falha parcial"
                    )
                    CriticaLegendItem(
                        iconRes = R.drawable.ic_maxima_legenda_cancelamento,
                        text = "Falha total"
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Legendas",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    ),
                    color = AppColors.OnSurfaceHigh,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    LegendaLegendItem(
                        iconRes = R.drawable.ic_maxima_legenda_corte,
                        text = "Pedido sofreu corte"
                    )
                    LegendaLegendItem(
                        iconRes = R.drawable.ic_maxima_legenda_falta,
                        text = "Pedido com falta"
                    )
                    LegendaLegendItem(
                        iconRes = R.drawable.ic_maxima_legenda_cancelamento,
                        text = "Pedido cancelado no ERP"
                    )
                    LegendaLegendItem(
                        iconRes = R.drawable.ic_maxima_legenda_devolucao,
                        text = "Pedido com devolução"
                    )
                    LegendaLegendItem(
                        iconRes = R.drawable.ic_maxima_legenda_telemarketing,
                        text = "Pedido feito pelo Telemarketing"
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .height(48.dp)
                        .align(Alignment.End),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Background
                    ),
                ) {
                    Text(
                        text = "FECHAR",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = OnSurfaceMedium
                    )
                }
            }
        }
    }
}

@Composable
private fun StatusLegendItem(
    icon: @Composable () -> Unit,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon()
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            ),
            color = AppColors.OnSurfaceHigh
        )
    }
}

@Composable
private fun CriticaLegendItem(
    iconRes: Int,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = text,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            ),
            color = AppColors.OnSurfaceHigh
        )
    }
}

@Composable
private fun LegendaLegendItem(
    iconRes: Int,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = text,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            ),
            color = AppColors.OnSurfaceHigh
        )
    }
}

@Composable
private fun StatusCircle(
    text: String,
    backgroundColor: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            ),
            color = Background,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ProcessingIcon(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(Processing),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_maxima_em_processamento),
            contentDescription = "Em processamento",
            modifier = Modifier.size(16.dp)
        )
    }
}