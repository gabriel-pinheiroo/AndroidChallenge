package com.maximatech.provaandroid.presentation.features.clients

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maximatech.provaandroid.LocalTopBarManager
import com.maximatech.provaandroid.core.domain.model.Client
import com.maximatech.provaandroid.features.client.ClientViewModel
import com.maximatech.provaandroid.presentation.designSystem.tokens.AppColors
import org.koin.androidx.compose.koinViewModel
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import com.maximatech.provaandroid.presentation.designSystem.components.legends.LegendsDialog

@Composable
fun ClientsRoute(
    modifier: Modifier = Modifier,
    viewModel: ClientViewModel = koinViewModel(),
    navigateToClientDetails: () -> Unit = {},
) {
    val topBarManager = LocalTopBarManager.current
    val state by viewModel.state.collectAsState()
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

        viewModel.getClient()
    }

    Column(
        modifier = modifier
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
                    onRetry = { viewModel.getClient() }
                )
            }

            else -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        ClientCard(
                            client = state.client,
                            onClick = {
                                navigateToClientDetails()
                            }
                        )
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
private fun ClientCard(
    client: Client,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = AppColors.Surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "${client.id} - ${client.razaoSocial}",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = AppColors.OnSurfaceHigh,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = client.nomeFantasia,
                    fontSize = 14.sp,
                    color = AppColors.OnSurfaceLight,
                )
            }

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Ver detalhes",
                tint = AppColors.OnSurfaceHigh,
                modifier = Modifier.size(32.dp)
            )
        }
    }
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
                text = "Erro ao carregar cliente",
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