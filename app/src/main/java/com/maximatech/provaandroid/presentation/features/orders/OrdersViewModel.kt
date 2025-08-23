package com.maximatech.provaandroid.presentation.features.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maximatech.provaandroid.core.domain.usecase.GetOrdersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import androidx.compose.runtime.Immutable
import com.maximatech.provaandroid.core.domain.model.Order

@Immutable
data class OrdersState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val allOrders: List<Order> = emptyList(),
    val filteredOrders: List<Order> = emptyList(),
    val searchQuery: String = "",
    val errorMessage: String = "",
    val isSearching: Boolean = false
) {
    companion object {
        internal val Idle = OrdersState()
    }
}

class OrdersViewModel(
    private val getOrdersUseCase: GetOrdersUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(OrdersState.Idle)
    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = OrdersState.Idle,
    )

    private val searchQueryFlow = MutableStateFlow("")

    init {
        setupSearchDebounce()
    }

    private fun setupSearchDebounce() {
        viewModelScope.launch {
            searchQueryFlow
                .debounce(300)
                .distinctUntilChanged()
                .onEach { query ->
                    if (query.isNotBlank()) {
                        _state.update {
                            it.copy(isSearching = true)
                        }
                    }
                }
                .collect { query ->
                    performSearch(query)
                }
        }
    }

    private fun performSearch(query: String) {
        val currentState = _state.value
        val filtered = if (query.isBlank()) {
            currentState.allOrders
        } else {
            currentState.allOrders.filter { order ->
                order.numeroPedRca.toString().contains(query, ignoreCase = true)
            }
        }

        _state.update {
            it.copy(
                filteredOrders = filtered,
                isSearching = false
            )
        }
    }

    fun getOrders() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    hasError = false,
                    errorMessage = ""
                )
            }

            try {
                val orders = getOrdersUseCase().getOrThrow()
                _state.update {
                    it.copy(
                        isLoading = false,
                        allOrders = orders,
                        filteredOrders = if (it.searchQuery.isBlank()) orders else it.filteredOrders,
                        hasError = false,
                        errorMessage = ""
                    )
                }
                if (searchQueryFlow.value.isNotBlank()) {
                    performSearch(searchQueryFlow.value)
                }
            } catch (exception: Throwable) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        hasError = true,
                        errorMessage = exception.message ?: "Erro ao carregar pedidos"
                    )
                }
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _state.update {
            it.copy(searchQuery = query)
        }
        searchQueryFlow.value = query
    }

    fun clearSearch() {
        searchQueryFlow.value = ""
        _state.update {
            it.copy(
                searchQuery = "",
                filteredOrders = it.allOrders
            )
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