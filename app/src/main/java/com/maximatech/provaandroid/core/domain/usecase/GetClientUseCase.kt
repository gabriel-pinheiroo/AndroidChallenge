package com.maximatech.provaandroid.core.domain.usecase

import com.maximatech.provaandroid.core.domain.repository.ClientRepository
import com.maximatech.provaandroid.core.domain.model.Client

class GetClientUseCase(
    private val clientRepository: ClientRepository
) {
    suspend operator fun invoke(): Result<Client> {
        return clientRepository.getClient()
    }
}