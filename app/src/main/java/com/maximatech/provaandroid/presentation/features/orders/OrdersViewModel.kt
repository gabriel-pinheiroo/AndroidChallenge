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
import kotlinx.coroutines.launch
import androidx.compose.runtime.Immutable
import com.maximatech.provaandroid.core.domain.model.Order
import com.maximatech.provaandroid.core.utils.update

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
    fun onLoading() = copy(isLoading = true, hasError = false, errorMessage = "")
    fun onLoadingFinished() = copy(isLoading = false)

    fun onSuccess(orders: List<Order>) = copy(
        allOrders = orders,
        filteredOrders = if (searchQuery.isBlank()) orders else filteredOrders,
        hasError = false,
        errorMessage = ""
    )

    fun onError(message: String) = copy(hasError = true, errorMessage = message)

    fun onSearchStarted() = copy(isSearching = true)

    fun onSearchFinished(filteredOrders: List<Order>) = copy(
        filteredOrders = filteredOrders,
        isSearching = false
    )

    fun onSearchQueryChanged(query: String) = copy(searchQuery = query)

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
                        _state.update { onSearchStarted() }
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
            onSearchFinished(filtered)
        }
    }

    fun getOrders() {
        viewModelScope.launch {
            _state.update { onLoading() }

            try {
                val orders = getOrdersUseCase().getOrThrow()
                _state.update {
                    onLoadingFinished().onSuccess(orders)
                }
                if (searchQueryFlow.value.isNotBlank()) {
                    performSearch(searchQueryFlow.value)
                }
            } catch (exception: Throwable) {
                _state.update {
                    onLoadingFinished().onError(
                        exception.message ?: "Erro ao carregar pedidos"
                    )
                }
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _state.update { onSearchQueryChanged(query) }
        searchQueryFlow.value = query
    }

    fun clearSearch() {
        searchQueryFlow.value = ""
        _state.update {
            onSearchQueryChanged("").onSearchFinished(allOrders)
        }
    }

    fun clearError() {
        _state.update { copy(hasError = false, errorMessage = "") }
    }
}