package com.maximatech.provaandroid.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.maximatech.provaandroid.core.domain.model.Client
import com.maximatech.provaandroid.core.domain.usecase.GetClientUseCase
import com.maximatech.provaandroid.factory.TestClientFactory
import com.maximatech.provaandroid.presentation.features.clientDetails.ClientDetailsViewModel
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
class ClientDetailsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    @MockK
    private lateinit var getClientUseCase: GetClientUseCase

    private lateinit var viewModel: ClientDetailsViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = ClientDetailsViewModel(getClientUseCase)
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
        assertFalse(state.showStatusToast)
        assertFalse(state.showStatusSnackbar)
        assertEquals("", state.clientStatus)
    }

    @Test
    fun `when getClientDetails is called successfully, should update state with client data`() = runTest {
        val expectedClient = TestClientFactory.createDefaultClient()
        coEvery { getClientUseCase() } returns Result.success(expectedClient)

        viewModel.getClientDetails()
        advanceUntilIdle()

        val state = viewModel.state.first()

        assertFalse(state.isLoading)
        assertFalse(state.hasError)
        assertEquals(expectedClient, state.client)
        assertEquals("001", state.client.codigo)
        assertEquals("João Silva Ltda", state.client.razaoSocial)
        assertEquals("ativo", state.client.status)
        assertEquals("", state.errorMessage)
        coVerify(exactly = 1) { getClientUseCase() }
    }

    @Test
    fun `when getClientDetails fails, should update state with error`() = runTest {
        val expectedException = Exception("Database connection error")
        coEvery { getClientUseCase() } returns Result.failure(expectedException)

        viewModel.getClientDetails()
        advanceUntilIdle()

        val state = viewModel.state.first()

        assertFalse(state.isLoading)
        assertTrue(state.hasError)
        assertEquals(Client.EMPTY, state.client)
        assertEquals("Database connection error", state.errorMessage)
        coVerify(exactly = 1) { getClientUseCase() }
    }

    @Test
    fun `when getClientDetails fails with null message, should use default error message`() = runTest {
        val expectedException = Exception()
        coEvery { getClientUseCase() } returns Result.failure(expectedException)

        viewModel.getClientDetails()
        advanceUntilIdle()

        val state = viewModel.state.first()

        assertFalse(state.isLoading)
        assertTrue(state.hasError)
        assertEquals("Erro ao carregar detalhes do cliente", state.errorMessage)
        coVerify(exactly = 1) { getClientUseCase() }
    }

    @Test
    fun `when verifyClientStatusWithSnackbar is called, should show snackbar with client status`() = runTest {
        val clientWithStatus = TestClientFactory.createClientWithStatus("bloqueado")
        coEvery { getClientUseCase() } returns Result.success(clientWithStatus)

        viewModel.getClientDetails()
        advanceUntilIdle()

        var state = viewModel.state.first()
        assertEquals(clientWithStatus, state.client)
        assertEquals("bloqueado", state.client.status)

        viewModel.verifyClientStatusWithSnackbar()

        state = viewModel.state.first()

        assertTrue(state.showStatusSnackbar)
        assertEquals("bloqueado", state.clientStatus)
        assertEquals(clientWithStatus, state.client)
    }


    @Test
    fun `when hideSnackbar is called, should hide snackbar`() = runTest {
        val clientWithStatus = TestClientFactory.createClientWithStatus("ativo")
        coEvery { getClientUseCase() } returns Result.success(clientWithStatus)

        viewModel.getClientDetails()
        advanceUntilIdle()

        var state = viewModel.state.first()
        assertEquals("ativo", state.client.status)

        viewModel.verifyClientStatusWithSnackbar()

        state = viewModel.state.first()
        assertTrue(state.showStatusSnackbar)
        assertEquals("ativo", state.clientStatus)

        viewModel.hideSnackbar()

        state = viewModel.state.first()
        assertFalse(state.showStatusSnackbar)
        assertEquals("ativo", state.clientStatus)
    }

    @Test
    fun `when clearError is called, should reset error state`() = runTest {
        val expectedException = Exception("Test error")
        coEvery { getClientUseCase() } returns Result.failure(expectedException)

        viewModel.getClientDetails()
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
    fun `when verifyClientStatusWithSnackbar is called with empty client, should use empty status`() = runTest {
        viewModel.verifyClientStatusWithSnackbar()

        val state = viewModel.state.first()

        assertTrue(state.showStatusSnackbar)
        assertEquals("", state.clientStatus)
        assertEquals(Client.EMPTY, state.client)
    }

    @Test
    fun `during getClientDetails execution, should show loading state`() = runTest {
        val expectedClient = TestClientFactory.createDefaultClient()
        coEvery { getClientUseCase() } coAnswers {
            delay(100)
            Result.success(expectedClient)
        }

        viewModel.getClientDetails()

        val loadingState = viewModel.state.first()
        assertTrue(loadingState.isLoading)
        assertFalse(loadingState.hasError)
        assertEquals("", loadingState.errorMessage)

        advanceUntilIdle()

        val finalState = viewModel.state.first()
        assertFalse(finalState.isLoading)
        assertEquals(expectedClient, finalState.client)
    }

    @Test
    fun `when client status changes, verifyClientStatusWithSnackbar should reflect new status`() = runTest {
        val activeClient = TestClientFactory.createClientWithStatus("ativo")
        coEvery { getClientUseCase() } returns Result.success(activeClient)

        viewModel.getClientDetails()
        advanceUntilIdle()

        var state = viewModel.state.first()
        assertEquals(activeClient, state.client)
        assertEquals("ativo", state.client.status)

        viewModel.verifyClientStatusWithSnackbar()

        state = viewModel.state.first()
        assertEquals("ativo", state.clientStatus)

        clearMocks(getClientUseCase)
        val blockedClient = TestClientFactory.createClientWithStatus("bloqueado")
        coEvery { getClientUseCase() } returns Result.success(blockedClient)

        viewModel.getClientDetails()
        advanceUntilIdle()
        viewModel.verifyClientStatusWithSnackbar()

        state = viewModel.state.first()
        assertEquals("bloqueado", state.clientStatus)
        assertEquals(blockedClient, state.client)
    }
}