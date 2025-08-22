package com.maximatech.provaandroid.core.data.remote.dto;

import com.google.gson.annotations.SerializedName;
import com.maximatech.provaandroid.core.domain.model.Contact;

public class ApiContact {
    @SerializedName("nome")
    private String nome;

    @SerializedName("telefone")
    private String telefone;

    @SerializedName("celular")
    private String celular;

    @SerializedName("conjuge")
    private String conjuge;

    @SerializedName("tipo")
    private String tipo;

    @SerializedName("time")
    private String time;

    @SerializedName("e_mail")
    private String email;

    @SerializedName("data_nascimento")
    private String dataNascimento;

    @SerializedName("dataNascimentoConjuge")
    private String dataNascimentoConjuge;

    @SerializedName("hobbie")
    private String hobbie;

    public ApiContact() {}

    public Contact toContact() {
        return new Contact(
                nome != null ? nome : "",
                telefone != null ? telefone : "",
                celular != null ? celular : "",
                conjuge != null ? conjuge : "",
                tipo != null ? tipo : "",
                time != null ? time : "",
                email != null ? email : "",
                hobbie != null ? hobbie : "",
                dataNascimento != null ? dataNascimento : "",
                dataNascimentoConjuge
        );
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getCelular() { return celular; }
    public void setCelular(String celular) { this.celular = celular; }

    public String getConjuge() { return conjuge; }
    public void setConjuge(String conjuge) { this.conjuge = conjuge; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(String dataNascimento) { this.dataNascimento = dataNascimento; }

    public String getDataNascimentoConjuge() { return dataNascimentoConjuge; }
    public void setDataNascimentoConjuge(String dataNascimentoConjuge) {
        this.dataNascimentoConjuge = dataNascimentoConjuge;
    }

    public String getHobbie() { return hobbie; }
    public void setHobbie(String hobbie) { this.hobbie = hobbie; }
}
