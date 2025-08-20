package com.maximatech.provaandroid.core.domain.model

data class Client(
    val id: Int,
    val codigo: String,
    val razaoSocial: String,
    val nomeFantasia: String,
    val cnpj: String,
    val ramoAtividade: String,
    val endereco: String,
    val status: String,
    val cpf: String,
    val contatos: List<Contact>
) {
    companion object {
        val EMPTY = Client(
            id = 0,
            codigo = "",
            razaoSocial = "",
            nomeFantasia = "",
            cnpj = "",
            ramoAtividade = "",
            endereco = "",
            status = "",
            contatos = emptyList(),
            cpf = ""
        )
    }
}

data class Contact(
    val nome: String,
    val telefone: String,
    val celular: String,
    val conjuge: String,
    val tipo: String,
    val time: String,
    val email: String,
    val hobbie: String = "",
    val dataNascimento: String,
    val dataNascimentoConjuge: String?
) {
    companion object {
        val EMPTY = Contact(
            nome = "",
            telefone = "",
            celular = "",
            conjuge = "",
            tipo = "",
            time = "",
            email = "",
            dataNascimento = "",
            dataNascimentoConjuge = null
        )
    }
}