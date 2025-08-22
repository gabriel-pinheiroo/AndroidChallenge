package com.maximatech.provaandroid.repository

import com.maximatech.provaandroid.core.data.local.datasource.ClientLocalDataSource
import com.maximatech.provaandroid.core.data.network.NetworkConnectivityManager
import com.maximatech.provaandroid.core.data.remote.dto.ApiClientResponse
import com.maximatech.provaandroid.core.data.remote.service.ApiService
import com.maximatech.provaandroid.core.data.repository.DefaultClientRepositoryImpl
import com.maximatech.provaandroid.core.domain.model.Client
import com.maximatech.provaandroid.factory.TestClientFactory
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

class DefaultClientRepositoryImplTest {

    @MockK
    private lateinit var apiService: ApiService

    @MockK
    private lateinit var localDataSource: ClientLocalDataSource

    @MockK
    private lateinit var networkConnectivityManager: NetworkConnectivityManager

    private lateinit var repository: DefaultClientRepositoryImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repository = DefaultClientRepositoryImpl(
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
        val mockClient = TestClientFactory.createDefaultClient()
        val mockResponse = mockk<ApiClientResponse>()
        coEvery { networkConnectivityManager.isCurrentlyConnected() } returns true
        coEvery { apiService.getClient() } returns mockResponse
        every { mockResponse.getCliente()?.toClient() } returns mockClient
        coEvery { localDataSource.saveClient(any()) } just Runs

        val result = repository.getClient()

        assertTrue(result.isSuccess)
        assertEquals(mockClient, result.getOrNull())
        assertEquals("001", result.getOrNull()?.codigo)
        coVerify(exactly = 1) { apiService.getClient() }
        coVerify(exactly = 1) { localDataSource.saveClient(mockClient) }
    }

    @Test
    fun `when not connected to internet, should fetch from local data source`() = runBlocking {
        val mockClient = TestClientFactory.createClientWithCode("LOCAL-001")
        coEvery { networkConnectivityManager.isCurrentlyConnected() } returns false
        coEvery { localDataSource.getClient() } returns mockClient

        val result = repository.getClient()

        assertTrue(result.isSuccess)
        assertEquals(mockClient, result.getOrNull())
        assertEquals("LOCAL-001", result.getOrNull()?.codigo)
        coVerify(exactly = 0) { apiService.getClient() }
        coVerify(exactly = 1) { localDataSource.getClient() }
    }

    @Test
    fun `when network call fails but local data exists, should return local data`() = runBlocking {
        val localClient = TestClientFactory.createClientWithStatus("local")
        coEvery { networkConnectivityManager.isCurrentlyConnected() } returns true
        coEvery { apiService.getClient() } throws Exception("Network error")
        coEvery { localDataSource.getClient() } returns localClient

        val result = repository.getClientFromNetwork()

        assertTrue(result.isSuccess)
        assertEquals(localClient, result.getOrNull())
        assertEquals("local", result.getOrNull()?.status)
        coVerify(exactly = 1) { apiService.getClient() }
        coVerify(exactly = 1) { localDataSource.getClient() }
    }

    @Test
    fun `when both network and local fail, should return failure`() = runBlocking {
        val networkException = Exception("Network error")
        val localException = Exception("Local error")
        coEvery { networkConnectivityManager.isCurrentlyConnected() } returns true
        coEvery { apiService.getClient() } throws networkException
        coEvery { localDataSource.getClient() } throws localException

        val result = repository.getClientFromNetwork()

        assertTrue(result.isFailure)
        assertEquals(networkException, result.exceptionOrNull())
    }

    @Test
    fun `when api returns null cliente, should return empty client`() = runBlocking {
        val mockResponse = mockk<ApiClientResponse>()
        coEvery { networkConnectivityManager.isCurrentlyConnected() } returns true
        coEvery { apiService.getClient() } returns mockResponse
        every { mockResponse.getCliente() } returns null
        coEvery { localDataSource.saveClient(any()) } just Runs

        val result = repository.getClientFromNetwork()

        assertTrue(result.isSuccess)
        assertEquals(Client.EMPTY, result.getOrNull())
        coVerify(exactly = 1) { localDataSource.saveClient(Client.EMPTY) }
    }
}