package com.maximatech.provaandroid.core.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.maximatech.provaandroid.core.data.repository.DefaultClientRepositoryImpl
import com.maximatech.provaandroid.core.data.repository.DefaultOrdersRepositoryImpl
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.koin.core.context.GlobalContext

class DataSyncWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    companion object {
        private val syncMutex = Mutex()

        @Volatile
        private var isSyncing = false
    }

    override suspend fun doWork(): Result {
        if (isSyncing) {
            return Result.success()
        }

        return syncMutex.withLock {
            if (isSyncing) {
                return Result.success()
            }

            isSyncing = true

            try {
                val koin = GlobalContext.get()
                val clientRepo = koin.get<DefaultClientRepositoryImpl>()
                val ordersRepo = koin.get<DefaultOrdersRepositoryImpl>()

                val clientResult = clientRepo.syncClientFromNetwork()
                val ordersResult = ordersRepo.syncOrdersFromNetwork()

                when {
                    clientResult.isSuccess && ordersResult.isSuccess -> {
                        Result.success()
                    }
                    clientResult.isSuccess || ordersResult.isSuccess -> {
                        Result.success()
                    }
                    else -> {
                        println("Falha na sincronização")
                        Result.retry()
                    }
                }
            } catch (e: Exception) {
                println("Erro na sincronização: ${e.message}")
                Result.failure()
            } finally {
                isSyncing = false
            }
        }
    }
}