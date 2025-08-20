import com.maximatech.provaandroid.core.data.local.datasource.ClientLocalDataSource
import com.maximatech.provaandroid.core.data.remote.service.ApiService
import com.maximatech.provaandroid.core.domain.repository.ClientRepository
import com.maximatech.provaandroid.core.domain.model.Client
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext

class DefaultClientRepositoryImpl(
    private val api: ApiService,
    private val localDataSource: ClientLocalDataSource
) : ClientRepository {

    override suspend fun getClient(): Result<Client> {
        return try {
            val response = api.getClient()
            val client = response.cliente?.toClient() ?: Client.EMPTY

            localDataSource.saveClient(client)

            Result.success(client)
        } catch (exception: Throwable) {
            coroutineContext.ensureActive()
            Result.failure(exception)
        }
    }

    suspend fun getClientFromLocal(): Result<Client> {
        return try {
            val client = localDataSource.getClient() ?: Client.EMPTY
            Result.success(client)
        } catch (exception: Throwable) {
            Result.failure(exception)
        }
    }
}