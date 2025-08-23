package com.maximatech.provaandroid.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.maximatech.provaandroid.core.domain.model.Client
import com.maximatech.provaandroid.core.domain.usecase.GetClientUseCase
import com.maximatech.provaandroid.factory.TestClientFactory
import com.maximatech.provaandroid.presentation.features.clients.ClientViewModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*

@OptIn(ExperimentalCoroutinesApi::class)
class ClientViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    @MockK
    private lateinit var getClientUseCase: GetClientUseCase

    private lateinit var viewModel: ClientViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = ClientViewModel(getClientUseCase)
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
        assertEquals(Client.EMPTY, state.client)
        assertEquals("", state.errorMessage)
    }

    @Test
    fun `when getClient is called successfully, should update state with client data`() = runTest {
        val expectedClient = TestClientFactory.createDefaultClient()
        coEvery { getClientUseCase() } returns Result.success(expectedClient)

        viewModel.getClient()
        advanceUntilIdle()

        val state = viewModel.state.first()

        assertFalse(state.isLoading)
        assertFalse(state.hasError)
        assertEquals(expectedClient, state.client)
        assertEquals("001", state.client.codigo)
        assertEquals("João Silva Ltda", state.client.razaoSocial)
        assertEquals("", state.errorMessage)
        coVerify(exactly = 1) { getClientUseCase() }
    }

    @Test
    fun `when getClient fails, should update state with error`() = runTest {
        val expectedException = Exception("Network connection failed")
        coEvery { getClientUseCase() } returns Result.failure(expectedException)

        viewModel.getClient()
        advanceUntilIdle()

        val state = viewModel.state.first()

        assertFalse(state.isLoading)
        assertTrue(state.hasError)
        assertEquals(Client.EMPTY, state.client)
        assertEquals("Network connection failed", state.errorMessage)
        coVerify(exactly = 1) { getClientUseCase() }
    }

    @Test
    fun `when getClient fails with null message, should use default error message`() = runTest {
        val expectedException = Exception()
        coEvery { getClientUseCase() } returns Result.failure(expectedException)

        viewModel.getClient()
        advanceUntilIdle()

        val state = viewModel.state.first()

        assertFalse(state.isLoading)
        assertTrue(state.hasError)
        assertEquals("Erro ao carregar cliente", state.errorMessage)
        coVerify(exactly = 1) { getClientUseCase() }
    }

    @Test
    fun `when clearError is called, should reset error state`() = runTest {
        val expectedException = Exception("Test error")
        coEvery { getClientUseCase() } returns Result.failure(expectedException)

        viewModel.getClient()
        advanceUntilIdle()

        var state = viewModel.state.first()
        assertTrue(state.hasError)
        assertEquals("Test error", state.errorMessage)

        viewModel.clearError()

        state = viewModel.state.first()
        assertFalse(state.hasError)
        assertEquals("", state.errorMessage)
    }

    @Test
    fun `when getClient returns inactive client, should update state correctly`() = runTest {
        val inactiveClient = TestClientFactory.createClientWithStatus("inativo")
        coEvery { getClientUseCase() } returns Result.success(inactiveClient)

        viewModel.getClient()
        advanceUntilIdle()

        val state = viewModel.state.first()

        assertFalse(state.isLoading)
        assertFalse(state.hasError)
        assertEquals(inactiveClient, state.client)
        assertEquals("inativo", state.client.status)
        assertEquals("002", state.client.codigo)
        coVerify(exactly = 1) { getClientUseCase() }
    }

    @Test
    fun `when getClient returns empty client, should update state correctly`() = runTest {
        val emptyClient = TestClientFactory.createEmptyClient()
        coEvery { getClientUseCase() } returns Result.success(emptyClient)

        viewModel.getClient()
        advanceUntilIdle()

        val state = viewModel.state.first()

        assertFalse(state.isLoading)
        assertFalse(state.hasError)
        assertEquals(Client.EMPTY, state.client)
        assertEquals(0, state.client.id)
        assertEquals("", state.client.codigo)
        coVerify(exactly = 1) { getClientUseCase() }
    }

    @Test
    fun `during getClient execution, should show loading state`() = runTest {
        val expectedClient = TestClientFactory.createDefaultClient()
        coEvery { getClientUseCase() } coAnswers {
            delay(100)
            Result.success(expectedClient)
        }

        viewModel.getClient()

        val loadingState = viewModel.state.first()
        assertTrue(loadingState.isLoading)
        assertFalse(loadingState.hasError)
        assertEquals("", loadingState.errorMessage)

        advanceUntilIdle()

        val finalState = viewModel.state.first()
        assertFalse(finalState.isLoading)
        assertEquals(expectedClient, finalState.client)
    }
}