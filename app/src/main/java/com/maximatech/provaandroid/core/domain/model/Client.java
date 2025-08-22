package com.maximatech.provaandroid.core.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Client {
    private int id;
    private String codigo;
    private String razaoSocial;
    private String nomeFantasia;
    private String cnpj;
    private String ramoAtividade;
    private String endereco;
    private String status;
    private String cpf;
    private List<Contact> contatos;

    public static final Client EMPTY = new Client(
            0, "", "", "", "", "", "", "", "", new ArrayList<>()
    );

    public Client() {
        this.contatos = new ArrayList<>();
    }

    public Client(int id, String codigo, String razaoSocial, String nomeFantasia,
                  String cnpj, String ramoAtividade, String endereco, String status,
                  String cpf, List<Contact> contatos) {
        this.id = id;
        this.codigo = codigo;
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
        this.cnpj = cnpj;
        this.ramoAtividade = ramoAtividade;
        this.endereco = endereco;
        this.status = status;
        this.cpf = cpf;
        this.contatos = contatos != null ? contatos : new ArrayList<>();
    }

    public int getId() { return id; }
    public String getCodigo() { return codigo; }
    public String getRazaoSocial() { return razaoSocial; }
    public String getNomeFantasia() { return nomeFantasia; }
    public String getCnpj() { return cnpj; }
    public String getRamoAtividade() { return ramoAtividade; }
    public String getEndereco() { return endereco; }
    public String getStatus() { return status; }
    public String getCpf() { return cpf; }
    public List<Contact> getContatos() { return contatos; }

    public void setId(int id) { this.id = id; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }
    public void setNomeFantasia(String nomeFantasia) { this.nomeFantasia = nomeFantasia; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }
    public void setRamoAtividade(String ramoAtividade) { this.ramoAtividade = ramoAtividade; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public void setStatus(String status) { this.status = status; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public void setContatos(List<Contact> contatos) {
        this.contatos = contatos != null ? contatos : new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id == client.id &&
                Objects.equals(codigo, client.codigo) &&
                Objects.equals(razaoSocial, client.razaoSocial) &&
                Objects.equals(nomeFantasia, client.nomeFantasia) &&
                Objects.equals(cnpj, client.cnpj) &&
                Objects.equals(ramoAtividade, client.ramoAtividade) &&
                Objects.equals(endereco, client.endereco) &&
                Objects.equals(status, client.status) &&
                Objects.equals(cpf, client.cpf) &&
                Objects.equals(contatos, client.contatos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codigo, razaoSocial, nomeFantasia, cnpj,
                ramoAtividade, endereco, status, cpf, contatos);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", razaoSocial='" + razaoSocial + '\'' +
                ", nomeFantasia='" + nomeFantasia + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", ramoAtividade='" + ramoAtividade + '\'' +
                ", endereco='" + endereco + '\'' +
                ", status='" + status + '\'' +
                ", cpf='" + cpf + '\'' +
                ", contatos=" + contatos +
                '}';
    }
}