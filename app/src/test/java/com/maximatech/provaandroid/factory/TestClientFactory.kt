package com.maximatech.provaandroid.factory

import com.maximatech.provaandroid.core.domain.model.Client

object TestClientFactory {

    fun createDefaultClient(): Client {
        return Client(
            1,
            "001",
            "João Silva Ltda",
            "João Silva",
            "12345678000190",
            "Tecnologia",
            "Rua Principal, 123",
            "ativo",
            "12345678901",
            mutableListOf()
        )
    }

    fun createClientWithStatus(status: String): Client {
        return Client(
            2,
            "002",
            "Cliente $status Ltda",
            "Cliente $status",
            "98765432000111",
            "Comércio",
            "Rua Secundária, 456",
            status,
            "98765432109",
            mutableListOf()
        )
    }

    fun createClientWithCode(codigo: String): Client {
        return Client(
            3,
            codigo,
            "Empresa $codigo",
            "Fantasia $codigo",
            "11122233000144",
            "Serviços",
            "Av. Teste, 789",
            "ativo",
            "11122233456",
            mutableListOf()
        )
    }

    fun createEmptyClient(): Client {
        return Client.EMPTY
    }
}