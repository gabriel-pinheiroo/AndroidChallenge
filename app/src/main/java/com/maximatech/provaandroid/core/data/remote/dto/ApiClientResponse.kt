package com.maximatech.provaandroid.core.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.maximatech.provaandroid.core.domain.model.Client
import com.maximatech.provaandroid.core.domain.model.Contact

data class ApiClientResponse(
    @SerializedName("cliente")
    val cliente: ApiClient? = null
)

data class ApiClient(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("codigo")
    val codigo: String? = null,
    @SerializedName("razao_social")
    val razaoSocial: String? = null,
    @SerializedName("nomeFantasia")
    val nomeFantasia: String? = null,
    @SerializedName("cnpj")
    val cnpj: String? = null,
    @SerializedName("ramo_atividade")
    val ramoAtividade: String? = null,
    @SerializedName("endereco")
    val endereco: String? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("contatos")
    val contatos: List<ApiContact>? = null
){
    fun toClient(): Client {
        return Client(
            id = id ?: 0,
            codigo = codigo.orEmpty(),
            razaoSocial = razaoSocial.orEmpty(),
            nomeFantasia = nomeFantasia.orEmpty(),
            cnpj = cnpj.orEmpty(),
            ramoAtividade = ramoAtividade.orEmpty(),
            endereco = endereco.orEmpty(),
            status = status.orEmpty(),
            contatos = contatos?.map { it.toContact() } ?: emptyList()
        )
    }
}

data class ApiContact(
    @SerializedName("nome")
    val nome: String? = null,
    @SerializedName("telefone")
    val telefone: String? = null,
    @SerializedName("celular")
    val celular: String? = null,
    @SerializedName("conjuge")
    val conjuge: String? = null,
    @SerializedName("tipo")
    val tipo: String? = null,
    @SerializedName("time")
    val time: String? = null,
    @SerializedName("e_mail")
    val email: String? = null,
    @SerializedName("data_nascimento")
    val dataNascimento: String? = null,
    @SerializedName("dataNascimentoConjuge")
    val dataNascimentoConjuge: String? = null,
    @SerializedName("hobbie")
    val hobbie: String? = null
){
    fun toContact(): Contact {
        return Contact(
            nome = nome.orEmpty(),
            telefone = telefone.orEmpty(),
            celular = celular.orEmpty(),
            hobbie = hobbie.orEmpty(),
            conjuge = conjuge.orEmpty(),
            tipo = tipo.orEmpty(),
            time = time.orEmpty(),
            email = email.orEmpty(),
            dataNascimento = dataNascimento.orEmpty(),
            dataNascimentoConjuge = dataNascimentoConjuge
        )
    }
}