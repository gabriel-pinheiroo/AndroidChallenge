package com.maximatech.provaandroid.core.domain.model;

import java.util.Objects;

public class Contact {
    private String nome;
    private String telefone;
    private String celular;
    private String conjuge;
    private String tipo;
    private String time;
    private String email;
    private String hobbie;
    private String dataNascimento;
    private String dataNascimentoConjuge;

    public static final Contact EMPTY = new Contact(
            "", "", "", "", "", "", "", "", "", null
    );

    public Contact() {
        this.hobbie = "";
    }

    public Contact(String nome, String telefone, String celular, String conjuge,
                   String tipo, String time, String email, String hobbie,
                   String dataNascimento, String dataNascimentoConjuge) {
        this.nome = nome;
        this.telefone = telefone;
        this.celular = celular;
        this.conjuge = conjuge;
        this.tipo = tipo;
        this.time = time;
        this.email = email;
        this.hobbie = hobbie != null ? hobbie : "";
        this.dataNascimento = dataNascimento;
        this.dataNascimentoConjuge = dataNascimentoConjuge;
    }

    public String getNome() { return nome; }
    public String getTelefone() { return telefone; }
    public String getCelular() { return celular; }
    public String getConjuge() { return conjuge; }
    public String getTipo() { return tipo; }
    public String getTime() { return time; }
    public String getEmail() { return email; }
    public String getHobbie() { return hobbie; }
    public String getDataNascimento() { return dataNascimento; }
    public String getDataNascimentoConjuge() { return dataNascimentoConjuge; }

    public void setNome(String nome) { this.nome = nome; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public void setCelular(String celular) { this.celular = celular; }
    public void setConjuge(String conjuge) { this.conjuge = conjuge; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setTime(String time) { this.time = time; }
    public void setEmail(String email) { this.email = email; }
    public void setHobbie(String hobbie) { this.hobbie = hobbie != null ? hobbie : ""; }
    public void setDataNascimento(String dataNascimento) { this.dataNascimento = dataNascimento; }
    public void setDataNascimentoConjuge(String dataNascimentoConjuge) {
        this.dataNascimentoConjuge = dataNascimentoConjuge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(nome, contact.nome) &&
                Objects.equals(telefone, contact.telefone) &&
                Objects.equals(celular, contact.celular) &&
                Objects.equals(conjuge, contact.conjuge) &&
                Objects.equals(tipo, contact.tipo) &&
                Objects.equals(time, contact.time) &&
                Objects.equals(email, contact.email) &&
                Objects.equals(hobbie, contact.hobbie) &&
                Objects.equals(dataNascimento, contact.dataNascimento) &&
                Objects.equals(dataNascimentoConjuge, contact.dataNascimentoConjuge);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, telefone, celular, conjuge, tipo, time,
                email, hobbie, dataNascimento, dataNascimentoConjuge);
    }

    @Override
    public String toString() {
        return "Contact{" +
                "nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", celular='" + celular + '\'' +
                ", conjuge='" + conjuge + '\'' +
                ", tipo='" + tipo + '\'' +
                ", time='" + time + '\'' +
                ", email='" + email + '\'' +
                ", hobbie='" + hobbie + '\'' +
                ", dataNascimento='" + dataNascimento + '\'' +
                ", dataNascimentoConjuge='" + dataNascimentoConjuge + '\'' +
                '}';
    }
}