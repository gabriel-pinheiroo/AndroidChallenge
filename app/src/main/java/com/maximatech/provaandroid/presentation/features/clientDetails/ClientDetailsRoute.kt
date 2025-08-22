package com.maximatech.provaandroid.presentation.features.clientDetails

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.maximatech.provaandroid.app.LocalTopBarManager
import com.maximatech.provaandroid.R
import com.maximatech.provaandroid.core.domain.model.Client
import com.maximatech.provaandroid.core.domain.model.Contact
import com.maximatech.provaandroid.core.utils.formatBrazilianCellPhone
import com.maximatech.provaandroid.core.utils.formatBrazilianDateWithValidation
import com.maximatech.provaandroid.features.clientDetails.ClientDetailsState
import com.maximatech.provaandroid.features.clientDetails.ClientDetailsViewModel
import com.maximatech.provaandroid.presentation.designSystem.tokens.AppColors
import com.maximatech.provaandroid.presentation.designSystem.tokens.*
import org.koin.androidx.compose.koinViewModel

@Composable
fun ClientDetailsRoute(
    clientName: String,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ClientDetailsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getClientDetails()
    }

    ClientDetailsScreen(
        modifier = modifier,
        state = state,
        clientName = clientName,
        onNavigateBack = onNavigateBack,
        onRetry = viewModel::getClientDetails,
        onVerifyStatus = viewModel::verifyClientStatusWithSnackbar,
        onHideSnackbar = viewModel::hideSnackbar
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ClientDetailsScreen(
    modifier: Modifier = Modifier,
    state: ClientDetailsState,
    clientName: String,
    onNavigateBack: () -> Unit = {},
    onRetry: () -> Unit = {},
    onVerifyStatus: () -> Unit = {},
    onHideSnackbar: () -> Unit = {}
) {
    val topBarManager = LocalTopBarManager.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        topBarManager.showTopBar()
        topBarManager.setTopBarConfig {
            this.title = clientName
            showTitle = true
            showBackButton = true
            this.onBackClicked = onNavigateBack
        }
    }

    LaunchedEffect(state.showStatusSnackbar) {
        if (state.showStatusSnackbar) {
            snackbarHostState.showSnackbar(
                message = state.clientStatus,
                duration = SnackbarDuration.Short
            )
            onHideSnackbar()
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
        Scaffold(
            snackbarHost = {
                SnackbarHost(snackbarHostState) { data ->
                    Card(
                        modifier = Modifier
                            .padding(horizontal = MediumSpacing, vertical = SmallSpacing),
                        colors = CardDefaults.cardColors(
                            containerColor = AppColors.Primary
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = HighElevation),
                        shape = RoundedCornerShape(MediumRadius)
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(MediumSpacing),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = data.visuals.message,
                                color = AppColors.OnPrimary,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                ),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            },
            containerColor = AppColors.CardBackground
        ) {
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
                            verticalArrangement = Arrangement.spacedBy(MediumSpacing)
                        ) {
                            item {
                                ClientDataSection(client = state.client)
                            }

                            item {
                                ContactsSection(client = state.client)
                            }

                            item {
                                VerifyStatusButton(
                                    onVerifyClick = onVerifyStatus
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ClientDataSection(
    client: Client,
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
                .padding(MediumSpacing)
        ) {
            Text(
                text = "Dados do cliente",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = SubtitleFontSize
                ),
                color = AppColors.OnSurfaceHigh,
            )

            HorizontalDivider(
                thickness = SingleSpacing,
                color = AppColors.OnSurfaceLight.copy(0.3f)
            )

            Spacer(modifier = Modifier.height(MediumSpacing))

            Text(
                text = "${client.id} - ${client.razaoSocial}",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = NormalFontSize
                ),
                color = AppColors.OnSurfaceHigh,
                modifier = Modifier.padding(bottom = DoubleSpacing)
            )

            Text(
                text = client.nomeFantasia,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = NormalFontSize
                ),
                color = AppColors.OnSurfaceMedium,
                modifier = Modifier.padding(bottom = SmallMediumSpacing)
            )

            ClientDataRow(label = "CPF:", value = client.cpf.ifEmpty { "Não informado" })
            ClientDataRow(label = "CNPJ:", value = client.cnpj.ifEmpty { "Não informado" })
            ClientDataRow(label = "Ramo de atividade:", value = client.ramoAtividade.ifEmpty { "Não informado" })
            ClientDataRow(label = "Endereços:", value = client.endereco.ifEmpty { "Não informado" })
        }
    }
}

@Composable
private fun ClientDataRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = DoubleSpacing),
        horizontalArrangement = Arrangement.spacedBy(TinySpacing)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = SmallFontSize,
                fontWeight = FontWeight.Medium
            ),
            color = AppColors.OnSurfaceLight,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = SmallFontSize,
                fontWeight = FontWeight.Medium
            ),
            color = AppColors.OnSurfaceHigh,
        )
    }
}

@Composable
private fun ContactsSection(
    client: Client,
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
                .padding(MediumSpacing)
        ) {
            Text(
                text = "Contatos",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = SubtitleFontSize
                ),
                color = AppColors.OnSurfaceHigh,
            )

            HorizontalDivider(
                thickness = SingleSpacing,
                color = AppColors.OnSurfaceLight.copy(0.3f)
            )

            Spacer(modifier = Modifier.height(MediumSpacing))

            if (client.contatos.isNotEmpty()) {
                client.contatos.forEachIndexed { index, contact ->
                    ContactItem(contact = contact)

                    if (index < client.contatos.size - 1) {
                        Spacer(modifier = Modifier.height(MediumSpacing))
                        HorizontalDivider(
                            color = AppColors.OnSurfaceLight.copy(alpha = 0.2f),
                            thickness = SingleSpacing
                        )
                        Spacer(modifier = Modifier.height(MediumSpacing))
                    }
                }
            } else {
                Text(
                    text = "Nenhum contato encontrado",
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppColors.OnSurfaceMedium,
                    modifier = Modifier.padding(vertical = MediumSpacing)
                )
            }
        }
    }
}

@Composable
private fun ContactItem(
    contact: Contact,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = contact.nome.ifEmpty { "Nome não informado" },
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = SubtitleFontSize
            ),
            color = AppColors.OnSurfaceHigh,
            modifier = Modifier.padding(bottom = SmallSpacing)
        )

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(TinySpacing)
                ) {
                    ContactDetailRow(
                        label = "Telefone:",
                        value = contact.telefone.ifEmpty { "Não informado" }
                    )
                    Image(
                        painter = painterResource(R.drawable.ic_maxima_telefone),
                        contentDescription = "Telefone",
                        modifier = Modifier
                            .size(IconSizeMedium)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(TinySpacing)
                ) {
                    ContactDetailRow(
                        label = "Celular:",
                        value = formatBrazilianCellPhone(contact.celular.ifEmpty { "Não informado" })
                    )
                    Image(
                        painter = painterResource(R.drawable.ic_maxima_telefone),
                        contentDescription = "Telefone",
                        modifier = Modifier
                            .size(IconSizeMedium)
                    )
                }
                ContactDetailRow(
                    label = "Cônjuge:",
                    value = contact.conjuge.ifEmpty { "Não informado" }
                )
                ContactDetailRow(
                    label = "Tipo:",
                    value = contact.tipo.ifEmpty { "Não informado" }
                )
                ContactDetailRow(
                    label = "Hobbie:",
                    value = contact.hobbie.ifEmpty { "Não informado" }
                )
            }

            Spacer(modifier = Modifier.width(SmallSpacing))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(TinySpacing),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        ContactDetailRow(
                            label = "E-mail:",
                            value = contact.email.ifEmpty { "Não informado" }
                        )
                    }
                    Image(
                        painter = painterResource(R.drawable.ic_maxima_email),
                        contentDescription = "E-mail",
                        modifier = Modifier
                            .size(IconSizeMedium)
                    )
                }
                ContactDetailRow(
                    label = "Data Nasc.:",
                    value = formatBrazilianDateWithValidation(contact.dataNascimento.ifEmpty { "Não informado" })
                )
                ContactDetailRow(
                    label = "Data Nasc. Cônjuge:",
                    value = contact.dataNascimentoConjuge ?: "Não informado"
                )
                ContactDetailRow(
                    label = "Time:",
                    value = contact.time.ifEmpty { "Não informado" }
                )
            }
        }
    }
}

@Composable
private fun ContactDetailRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(vertical = SingleSpacing)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = TinyFontSize,
                fontWeight = FontWeight.Medium
            ),
            color = AppColors.OnSurfaceLight
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = TinyFontSize,
                fontWeight = FontWeight.Medium
            ),
            color = AppColors.OnSurfaceHigh,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun VerifyStatusButton(
    onVerifyClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onVerifyClick,
        modifier = modifier
            .fillMaxWidth()
            .height(ButtonHeight),
        colors = ButtonDefaults.buttonColors(
            containerColor = AppColors.VerifyAction
        ),
        shape = RoundedCornerShape(SmallRadius)
    ) {
        Text(
            text = "Verificar status do cliente",
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            color = AppColors.Surface
        )
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
                text = "Erro ao carregar detalhes do cliente",
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