package com.maximatech.provaandroid.presentation.features.clients

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maximatech.provaandroid.core.domain.usecase.GetClientUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import androidx.compose.runtime.Immutable
import com.maximatech.provaandroid.core.domain.model.Client

@Immutable
data class ClientState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val client: Client = Client.EMPTY,
    val errorMessage: String = ""
) {
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
            _state.update {
                it.copy(
                    isLoading = true,
                    hasError = false,
                    errorMessage = ""
                )
            }

            try {
                val client = getClientUseCase().getOrThrow()
                _state.update {
                    it.copy(
                        isLoading = false,
                        client = client,
                        hasError = false,
                        errorMessage = ""
                    )
                }
            } catch (exception: Throwable) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        hasError = true,
                        errorMessage = exception.message ?: "Erro ao carregar cliente"
                    )
                }
            }
        }
    }

    fun clearError() {
        _state.update {
            it.copy(
                hasError = false,
                errorMessage = ""
            )
        }
    }
}