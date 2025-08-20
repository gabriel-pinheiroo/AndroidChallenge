package com.maximatech.provaandroid.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maximatech.provaandroid.core.domain.model.Client
import com.maximatech.provaandroid.core.domain.model.Contact

@Entity(tableName = "clients")
data class ClientEntity(
    @PrimaryKey
    val id: Int,
    val codigo: String,
    val razaoSocial: String,
    val nomeFantasia: String,
    val cnpj: String,
    val ramoAtividade: String,
    val endereco: String,
    val status: String,
    val cpf: String
) {
    fun toClient(contacts: List<Contact>): Client {
        return Client(
            id = id,
            codigo = codigo,
            razaoSocial = razaoSocial,
            nomeFantasia = nomeFantasia,
            cnpj = cnpj,
            ramoAtividade = ramoAtividade,
            endereco = endereco,
            status = status,
            cpf = cpf,
            contatos = contacts
        )
    }

    companion object {
        fun fromClient(client: Client): ClientEntity {
            return ClientEntity(
                id = client.id,
                codigo = client.codigo,
                razaoSocial = client.razaoSocial,
                nomeFantasia = client.nomeFantasia,
                cnpj = client.cnpj,
                ramoAtividade = client.ramoAtividade,
                endereco = client.endereco,
                status = client.status,
                cpf = client.cpf
            )
        }
    }
}

@Entity(
    tableName = "contacts",
    foreignKeys = [
        androidx.room.ForeignKey(
            entity = ClientEntity::class,
            parentColumns = ["id"],
            childColumns = ["clientId"],
            onDelete = androidx.room.ForeignKey.CASCADE
        )
    ]
)
data class ContactEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val clientId: Int,
    val nome: String,
    val telefone: String,
    val celular: String,
    val conjuge: String,
    val tipo: String,
    val time: String,
    val email: String,
    val hobbie: String,
    val dataNascimento: String,
    val dataNascimentoConjuge: String?
) {
    fun toContact(): Contact {
        return Contact(
            nome = nome,
            telefone = telefone,
            celular = celular,
            conjuge = conjuge,
            tipo = tipo,
            time = time,
            email = email,
            hobbie = hobbie,
            dataNascimento = dataNascimento,
            dataNascimentoConjuge = dataNascimentoConjuge
        )
    }

    companion object {
        fun fromContact(contact: Contact, clientId: Int): ContactEntity {
            return ContactEntity(
                clientId = clientId,
                nome = contact.nome,
                telefone = contact.telefone,
                celular = contact.celular,
                conjuge = contact.conjuge,
                tipo = contact.tipo,
                time = contact.time,
                email = contact.email,
                hobbie = contact.hobbie,
                dataNascimento = contact.dataNascimento,
                dataNascimentoConjuge = contact.dataNascimentoConjuge
            )
        }
    }
}
