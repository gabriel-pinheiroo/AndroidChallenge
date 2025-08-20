package com.maximatech.provaandroid.core.data.local.dao

import androidx.room.*
import com.maximatech.provaandroid.core.data.local.entity.ClientEntity
import com.maximatech.provaandroid.core.data.local.entity.ContactEntity

@Dao
interface ClientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClient(client: ClientEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContacts(contacts: List<ContactEntity>)

    @Query("SELECT * FROM clients LIMIT 1")
    suspend fun getClient(): ClientEntity?

    @Query("SELECT * FROM contacts WHERE clientId = :clientId")
    suspend fun getContactsByClientId(clientId: Int): List<ContactEntity>

    @Query("DELETE FROM clients")
    suspend fun deleteAllClients()

    @Query("DELETE FROM contacts")
    suspend fun deleteAllContacts()

    @Transaction
    suspend fun saveClientWithContacts(client: ClientEntity, contacts: List<ContactEntity>) {
        deleteAllClients()
        deleteAllContacts()
        insertClient(client)
        insertContacts(contacts)
    }
}