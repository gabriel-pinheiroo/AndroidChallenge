package com.maximatech.provaandroid.usecase

import com.maximatech.provaandroid.core.domain.model.Client
import com.maximatech.provaandroid.core.domain.repository.ClientRepository
import com.maximatech.provaandroid.core.domain.usecase.GetClientUseCase
import com.maximatech.provaandroid.factory.TestClientFactory
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

class GetClientUseCaseTest {

    @MockK
    private lateinit var clientRepository: ClientRepository

    private lateinit var getClientUseCase: GetClientUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        getClientUseCase = GetClientUseCase(clientRepository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `when repository returns client successfully, should return success result`() = runBlocking {
        val expectedClient = TestClientFactory.createDefaultClient()
        coEvery { clientRepository.getClient() } returns Result.success(expectedClient)

        val result = getClientUseCase()

        assertTrue(result.isSuccess)
        assertEquals(expectedClient, result.getOrNull())
        assertEquals("001", result.getOrNull()?.codigo)
        assertEquals("João Silva Ltda", result.getOrNull()?.razaoSocial)
        coVerify(exactly = 1) { clientRepository.getClient() }
    }

    @Test
    fun `when repository returns failure, should return failure result`() = runBlocking {
        val expectedException = Exception("Network error")
        coEvery { clientRepository.getClient() } returns Result.failure(expectedException)

        val result = getClientUseCase()

        assertTrue(result.isFailure)
        assertEquals(expectedException, result.exceptionOrNull())
        coVerify(exactly = 1) { clientRepository.getClient() }
    }

    @Test
    fun `when repository returns empty client, should return success with empty client`() = runBlocking {
        val emptyClient = TestClientFactory.createEmptyClient()
        coEvery { clientRepository.getClient() } returns Result.success(emptyClient)

        val result = getClientUseCase()

        assertTrue(result.isSuccess)
        assertEquals(Client.EMPTY, result.getOrNull())
        assertEquals(0, result.getOrNull()?.id)
        coVerify(exactly = 1) { clientRepository.getClient() }
    }

    @Test
    fun `when repository returns inactive client, should return success with inactive status`() = runBlocking {
        val inactiveClient = TestClientFactory.createClientWithStatus("inativo")
        coEvery { clientRepository.getClient() } returns Result.success(inactiveClient)

        val result = getClientUseCase()

        assertTrue(result.isSuccess)
        assertEquals("inativo", result.getOrNull()?.status)
        assertEquals("002", result.getOrNull()?.codigo)
        coVerify(exactly = 1) { clientRepository.getClient() }
    }
}