// features/client/ClientViewModel.kt
package com.maximatech.provaandroid.features.client

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
data class ClientState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val client: Client = Client.EMPTY,
    val errorMessage: String = ""
) {
    fun onLoading() = copy(isLoading = true, hasError = false, errorMessage = "")
    fun onLoadingFinished() = copy(isLoading = false)
    fun onSuccess(client: Client) = copy(client = client, hasError = false, errorMessage = "")
    fun onError(message: String) = copy(hasError = true, errorMessage = message)

    companion object {
        internal val Idle = ClientState()
    }
}

class ClientViewModel(
    private val getClientUseCase: GetClientUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ClientState.Idle)
    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = ClientState.Idle,
    )

    fun getClient() {
        viewModelScope.launch {
            _state.update { onLoading() }

            try {
                val client = getClientUseCase.execute().getOrThrow()
                _state.update {
                    onLoadingFinished().onSuccess(client)
                }
            } catch (exception: Throwable) {
                _state.update {
                    onLoadingFinished().onError(
                        exception.message ?: "Erro ao carregar cliente"
                    )
                }
            }
        }
    }

    fun clearError() {
        _state.update { copy(hasError = false, errorMessage = "") }
    }
}