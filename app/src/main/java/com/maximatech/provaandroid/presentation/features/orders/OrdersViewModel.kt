// features/orders/OrdersViewModel.kt
package com.maximatech.provaandroid.features.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maximatech.provaandroid.core.domain.usecase.GetOrdersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
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
    val errorMessage: String = ""
) {
    fun onLoading() = copy(isLoading = true, hasError = false, errorMessage = "")
    fun onLoadingFinished() = copy(isLoading = false)

    fun onSuccess(orders: List<Order>) = copy(
        allOrders = orders,
        filteredOrders = orders,
        hasError = false,
        errorMessage = ""
    )

    fun onError(message: String) = copy(hasError = true, errorMessage = message)

    fun onSearchQueryChanged(query: String): OrdersState {
        val filtered = if (query.isBlank()) {
            allOrders
        } else {
            allOrders.filter { order ->
                order.numeroPedRca.toString().contains(query, ignoreCase = true)
            }
        }
        return copy(searchQuery = query, filteredOrders = filtered)
    }

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

    fun getOrders() {
        viewModelScope.launch {
            _state.update { onLoading() }

            try {
                val orders = getOrdersUseCase().getOrThrow()
                _state.update {
                    onLoadingFinished().onSuccess(orders)
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
    }

    fun clearSearch() {
        _state.update { onSearchQueryChanged("") }
    }

    fun clearError() {
        _state.update { copy(hasError = false, errorMessage = "") }
    }
}