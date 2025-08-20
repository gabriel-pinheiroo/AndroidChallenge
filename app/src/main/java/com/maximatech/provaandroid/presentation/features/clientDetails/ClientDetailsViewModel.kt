package com.maximatech.provaandroid.features.clientDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maximatech.provaandroid.core.domain.usecase.GetClientUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import androidx.compose.runtime.Immutable
import com.maximatech.provaandroid.core.utils.update
import com.maximatech.provaandroid.core.domain.model.Client

@Immutable
data class ClientDetailsState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val client: Client = Client.EMPTY,
    val errorMessage: String = "",
    val showStatusToast: Boolean = false,
    val showStatusSnackbar: Boolean = false,
    val clientStatus: String = ""
) {
    fun onLoading() = copy(isLoading = true, hasError = false, errorMessage = "")
    fun onLoadingFinished() = copy(isLoading = false)
    fun onSuccess(client: Client) = copy(client = client, hasError = false, errorMessage = "")
    fun onError(message: String) = copy(hasError = true, errorMessage = message)
    fun onShowStatusSnackbar(status: String) = copy(showStatusSnackbar = true, clientStatus = status)
    fun onHideSnackbar() = copy(showStatusSnackbar = false)

    companion object {
        internal val Idle = ClientDetailsState()
    }
}

class ClientDetailsViewModel(
    private val getClientUseCase: GetClientUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ClientDetailsState.Idle)
    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = ClientDetailsState.Idle,
    )

    fun getClientDetails() {
        viewModelScope.launch {
            _state.update { onLoading() }

            try {
                val client = getClientUseCase().getOrThrow()
                _state.update {
                    onLoadingFinished().onSuccess(client)
                }
            } catch (exception: Throwable) {
                _state.update {
                    onLoadingFinished().onError(
                        exception.message ?: "Erro ao carregar detalhes do cliente"
                    )
                }
            }
        }
    }

    fun verifyClientStatusWithSnackbar() {
        val status = state.value.client.status
        _state.update { onShowStatusSnackbar(status) }
    }

    fun hideSnackbar() {
        _state.update { onHideSnackbar() }
    }

    fun clearError() {
        _state.update { copy(hasError = false, errorMessage = "") }
    }
}