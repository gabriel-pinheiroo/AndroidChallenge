package com.maximatech.provaandroid.core.data.local.datasource

import com.maximatech.provaandroid.core.data.local.dao.ClientDao
import com.maximatech.provaandroid.core.data.local.entity.ClientEntity
import com.maximatech.provaandroid.core.data.local.entity.ContactEntity
import com.maximatech.provaandroid.core.domain.model.Client

class ClientLocalDataSource(
    private val clientDao: ClientDao
) {

    suspend fun saveClient(client: Client) {
        val clientEntity = ClientEntity.fromClient(client)
        val contactEntities = client.contatos.map {
            ContactEntity.fromContact(it, client.id)
        }
        clientDao.saveClientWithContacts(clientEntity, contactEntities)
    }

    suspend fun getClient(): Client? {
        val clientEntity = clientDao.getClient() ?: return null
        val contacts = clientDao.getContactsByClientId(clientEntity.id)
            .map { it.toContact() }
        return clientEntity.toClient(contacts)
    }
}