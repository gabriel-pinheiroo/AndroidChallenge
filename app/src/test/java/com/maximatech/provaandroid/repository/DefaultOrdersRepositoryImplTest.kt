package com.maximatech.provaandroid.repository

import com.maximatech.provaandroid.core.data.local.datasource.OrderLocalDataSource
import com.maximatech.provaandroid.core.data.network.NetworkConnectivityManager
import com.maximatech.provaandroid.core.data.remote.dto.ApiOrdersResponse
import com.maximatech.provaandroid.core.data.remote.service.ApiService
import com.maximatech.provaandroid.core.data.repository.DefaultOrdersRepositoryImpl
import com.maximatech.provaandroid.factory.TestOrderFactory
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

class DefaultOrdersRepositoryImplTest {

    @MockK
    private lateinit var apiService: ApiService

    @MockK
    private lateinit var localDataSource: OrderLocalDataSource

    @MockK
    private lateinit var networkConnectivityManager: NetworkConnectivityManager

    private lateinit var repository: DefaultOrdersRepositoryImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repository = DefaultOrdersRepositoryImpl(
            api = apiService,
            localDataSource = localDataSource,
            networkConnectivityManager = networkConnectivityManager
        )
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `when connected to internet, should fetch from network and save locally`() = runBlocking {
        val mockOrders = TestOrderFactory.createMultipleOrders(2)
        val mockResponse = mockk<ApiOrdersResponse>()

        coEvery { networkConnectivityManager.isCurrentlyConnected() } returns true
        coEvery { apiService.getOrders() } returns mockResponse
        every { mockResponse.getPedidos() } returns mockOrders.map { order ->
            mockk(relaxed = true) {
                every { toOrder() } returns order
            }
        }
        coEvery { localDataSource.saveOrders(any()) } just Runs

        val result = repository.getOrders()

        assertTrue(result.isSuccess)
        assertEquals(mockOrders, result.getOrNull())
        assertEquals(2, result.getOrNull()?.size)
        coVerify(exactly = 1) { apiService.getOrders() }
        coVerify(exactly = 1) { localDataSource.saveOrders(mockOrders) }
    }

    @Test
    fun `when not connected to internet, should fetch from local data source`() = runBlocking {
        val localOrders = TestOrderFactory.createMultipleOrders(3)
        coEvery { networkConnectivityManager.isCurrentlyConnected() } returns false
        coEvery { localDataSource.getOrders() } returns localOrders

        val result = repository.getOrders()

        assertTrue(result.isSuccess)
        assertEquals(localOrders, result.getOrNull())
        assertEquals(3, result.getOrNull()?.size)
        coVerify(exactly = 0) { apiService.getOrders() }
        coVerify(exactly = 1) { localDataSource.getOrders() }
    }

    @Test
    fun `when network call fails, should fallback to local data source`() = runBlocking {
        val localOrders = TestOrderFactory.createMultipleOrders(1)
        coEvery { networkConnectivityManager.isCurrentlyConnected() } returns true
        coEvery { apiService.getOrders() } throws Exception("Network error")
        coEvery { localDataSource.getOrders() } returns localOrders

        val result = repository.getOrdersFromNetwork()

        assertTrue(result.isSuccess)
        assertEquals(localOrders, result.getOrNull())
        assertEquals(1, result.getOrNull()?.size)
        coVerify(exactly = 1) { apiService.getOrders() }
        coVerify(exactly = 1) { localDataSource.getOrders() }
    }

    @Test
    fun `when api returns null pedidos, should save empty list and return success`() = runBlocking {
        val mockResponse = mockk<ApiOrdersResponse>()
        coEvery { networkConnectivityManager.isCurrentlyConnected() } returns true
        coEvery { apiService.getOrders() } returns mockResponse
        every { mockResponse.getPedidos() } returns null
        coEvery { localDataSource.saveOrders(any()) } just Runs

        val result = repository.getOrdersFromNetwork()

        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()?.isEmpty() == true)
        coVerify(exactly = 1) { localDataSource.saveOrders(emptyList()) }
    }

    @Test
    fun `when api returns orders with different statuses, should handle correctly`() = runBlocking {
        val pendingOrder = TestOrderFactory.createOrderWithStatus("pendente")
        val completedOrder = TestOrderFactory.createOrderWithStatus("finalizado")
        val mixedOrders = listOf(pendingOrder, completedOrder)
        val mockResponse = mockk<ApiOrdersResponse>()

        coEvery { networkConnectivityManager.isCurrentlyConnected() } returns true
        coEvery { apiService.getOrders() } returns mockResponse
        every { mockResponse.getPedidos() } returns mixedOrders.map { order ->
            mockk(relaxed = true) {
                every { toOrder() } returns order
            }
        }
        coEvery { localDataSource.saveOrders(any()) } just Runs

        val result = repository.getOrdersFromNetwork()

        assertTrue(result.isSuccess)
        assertEquals(2, result.getOrNull()?.size)
        assertEquals("pendente", result.getOrNull()?.get(0)?.status)
        assertEquals("finalizado", result.getOrNull()?.get(1)?.status)
        coVerify(exactly = 1) { localDataSource.saveOrders(mixedOrders) }
    }
}