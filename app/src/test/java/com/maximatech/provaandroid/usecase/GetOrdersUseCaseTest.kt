package com.maximatech.provaandroid.usecase

import com.maximatech.provaandroid.core.domain.model.Order
import com.maximatech.provaandroid.core.domain.repository.OrdersRepository
import com.maximatech.provaandroid.core.domain.usecase.GetOrdersUseCase
import com.maximatech.provaandroid.factory.TestOrderFactory
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

class GetOrdersUseCaseTest {

    @MockK
    private lateinit var ordersRepository: OrdersRepository

    private lateinit var getOrdersUseCase: GetOrdersUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        getOrdersUseCase = GetOrdersUseCase(ordersRepository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `when repository returns orders successfully, should return success result`() = runBlocking {
        val expectedOrders = TestOrderFactory.createMultipleOrders(3)
        coEvery { ordersRepository.getOrders() } returns Result.success(expectedOrders)

        val result = getOrdersUseCase()

        assertTrue(result.isSuccess)
        assertEquals(expectedOrders, result.getOrNull())
        assertEquals(3, result.getOrNull()?.size)
        assertEquals(101, result.getOrNull()?.first()?.numeroPedRca)
        coVerify(exactly = 1) { ordersRepository.getOrders() }
    }

    @Test
    fun `when repository returns empty list, should return success with empty list`() = runBlocking {
        val emptyList = emptyList<Order>()
        coEvery { ordersRepository.getOrders() } returns Result.success(emptyList)

        val result = getOrdersUseCase()

        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()?.isEmpty() == true)
        coVerify(exactly = 1) { ordersRepository.getOrders() }
    }

    @Test
    fun `when repository returns failure, should return failure result`() = runBlocking {
        val expectedException = Exception("Database error")
        coEvery { ordersRepository.getOrders() } returns Result.failure(expectedException)

        val result = getOrdersUseCase()

        assertTrue(result.isFailure)
        assertEquals(expectedException, result.exceptionOrNull())
        coVerify(exactly = 1) { ordersRepository.getOrders() }
    }

    @Test
    fun `when repository returns single order, should return success with correct data`() = runBlocking {
        val singleOrder = TestOrderFactory.createDefaultOrder()
        coEvery { ordersRepository.getOrders() } returns Result.success(listOf(singleOrder))

        val result = getOrdersUseCase()

        assertTrue(result.isSuccess)
        assertEquals(1, result.getOrNull()?.size)
        assertEquals(123, result.getOrNull()?.first()?.numeroPedRca)
        assertEquals("ERP-001", result.getOrNull()?.first()?.numeroPedErp)
        assertEquals("Cliente Padrão", result.getOrNull()?.first()?.nomeCliente)
        coVerify(exactly = 1) { ordersRepository.getOrders() }
    }
}