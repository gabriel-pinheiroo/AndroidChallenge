package com.maximatech.provaandroid.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.maximatech.provaandroid.core.domain.usecase.GetOrdersUseCase
import com.maximatech.provaandroid.factory.TestOrderFactory
import com.maximatech.provaandroid.presentation.features.orders.OrdersViewModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*

@OptIn(ExperimentalCoroutinesApi::class)
class OrdersViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    @MockK
    private lateinit var getOrdersUseCase: GetOrdersUseCase

    private lateinit var viewModel: OrdersViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = OrdersViewModel(getOrdersUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `when viewModel is initialized, should have idle state`() = runTest {
        val state = viewModel.state.first()

        assertFalse(state.isLoading)
        assertFalse(state.hasError)
        assertTrue(state.allOrders.isEmpty())
        assertTrue(state.filteredOrders.isEmpty())
        assertEquals("", state.searchQuery)
        assertEquals("", state.errorMessage)
        assertFalse(state.isSearching)
    }

    @Test
    fun `when getOrders is called successfully, should update state with orders`() = runTest {
        val expectedOrders = TestOrderFactory.createMultipleOrders(3)
        coEvery { getOrdersUseCase() } returns Result.success(expectedOrders)

        viewModel.getOrders()
        advanceUntilIdle()

        val state = viewModel.state.first()

        assertFalse(state.isLoading)
        assertFalse(state.hasError)
        assertEquals(expectedOrders, state.allOrders)
        assertEquals(expectedOrders, state.filteredOrders)
        assertEquals("", state.errorMessage)
        coVerify(exactly = 1) { getOrdersUseCase() }
    }

    @Test
    fun `when getOrders fails, should update state with error`() = runTest {
        val expectedException = Exception("Network error")
        coEvery { getOrdersUseCase() } returns Result.failure(expectedException)

        viewModel.getOrders()
        advanceUntilIdle()

        val state = viewModel.state.first()

        assertFalse(state.isLoading)
        assertTrue(state.hasError)
        assertTrue(state.allOrders.isEmpty())
        assertEquals("Network error", state.errorMessage)
        coVerify(exactly = 1) { getOrdersUseCase() }
    }

    @Test
    fun `when updateSearchQuery is called, should update search query immediately`() = runTest {
        val searchQuery = "123"

        viewModel.updateSearchQuery(searchQuery)

        val state = viewModel.state.first()
        assertEquals(searchQuery, state.searchQuery)
    }

    @Test
    fun `when search is performed with debounce, should filter orders correctly`() = runTest {
        val orders = listOf(
            TestOrderFactory.createOrderWithNumber(123),
            TestOrderFactory.createOrderWithNumber(456),
            TestOrderFactory.createOrderWithNumber(789)
        )
        coEvery { getOrdersUseCase() } returns Result.success(orders)

        viewModel.getOrders()
        advanceUntilIdle()

        viewModel.updateSearchQuery("123")
        advanceUntilIdle()

        val state = viewModel.state.first()

        assertEquals("123", state.searchQuery)
        assertEquals(1, state.filteredOrders.size)
        assertEquals(123, state.filteredOrders.first().numeroPedRca)
        assertFalse(state.isSearching)
    }

    @Test
    fun `when search query is empty, should show all orders`() = runTest {
        val orders = TestOrderFactory.createMultipleOrders(3)
        coEvery { getOrdersUseCase() } returns Result.success(orders)

        viewModel.getOrders()
        advanceUntilIdle()

        viewModel.updateSearchQuery("")
        advanceUntilIdle()

        val state = viewModel.state.first()

        assertEquals("", state.searchQuery)
        assertEquals(orders.size, state.filteredOrders.size)
        assertEquals(orders, state.filteredOrders)
    }

    @Test
    fun `when clearSearch is called, should reset search query and show all orders`() = runTest {
        val orders = TestOrderFactory.createMultipleOrders(3)
        coEvery { getOrdersUseCase() } returns Result.success(orders)

        viewModel.getOrders()
        advanceUntilIdle()

        viewModel.updateSearchQuery("123")
        advanceUntilIdle()

        viewModel.clearSearch()
        advanceUntilIdle()

        val state = viewModel.state.first()

        assertEquals("", state.searchQuery)
        assertEquals(orders, state.filteredOrders)
    }

    @Test
    fun `when clearError is called, should reset error state`() = runTest {
        val expectedException = Exception("Network error")
        coEvery { getOrdersUseCase() } returns Result.failure(expectedException)

        viewModel.getOrders()
        advanceUntilIdle()

        viewModel.clearError()

        val state = viewModel.state.first()

        assertFalse(state.hasError)
        assertEquals("", state.errorMessage)
    }

    @Test
    fun `when search finds no results, should return empty filtered list`() = runTest {
        val orders = TestOrderFactory.createMultipleOrders(3)
        coEvery { getOrdersUseCase() } returns Result.success(orders)

        viewModel.getOrders()
        advanceUntilIdle()

        viewModel.updateSearchQuery("999")
        advanceUntilIdle()

        val state = viewModel.state.first()

        assertEquals("999", state.searchQuery)
        assertTrue(state.filteredOrders.isEmpty())
        assertEquals(orders.size, state.allOrders.size)
    }

    @Test
    fun `when search is performed after orders are loaded, should apply filter correctly`() = runTest {
        val orders = TestOrderFactory.createMultipleOrders(5)
        coEvery { getOrdersUseCase() } returns Result.success(orders)

        viewModel.updateSearchQuery("103")

        viewModel.getOrders()
        advanceUntilIdle()

        val state = viewModel.state.first()

        assertEquals("103", state.searchQuery)
        assertEquals(1, state.filteredOrders.size)
        assertEquals(103, state.filteredOrders.first().numeroPedRca)
    }
}