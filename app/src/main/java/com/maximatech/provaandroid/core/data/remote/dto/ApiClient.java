package com.maximatech.provaandroid.core.data.remote.dto;

import com.google.gson.annotations.SerializedName;
import com.maximatech.provaandroid.core.domain.model.Client;
import com.maximatech.provaandroid.core.domain.model.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ApiClient {
    @SerializedName("id")
    private Integer id;

    @SerializedName("codigo")
    private String codigo;

    @SerializedName("razao_social")
    private String razaoSocial;

    @SerializedName("nomeFantasia")
    private String nomeFantasia;

    @SerializedName("cnpj")
    private String cnpj;

    @SerializedName("ramo_atividade")
    private String ramoAtividade;

    @SerializedName("endereco")
    private String endereco;

    @SerializedName("status")
    private String status;

    @SerializedName("contatos")
    private List<ApiContact> contatos;

    @SerializedName("cpf")
    private String cpf;

    public ApiClient() {}

    public Client toClient() {
        List<Contact> contactList = new ArrayList<>();
        if (contatos != null) {
            contactList = contatos.stream()
                    .map(ApiContact::toContact)
                    .collect(Collectors.toList());
        }

        return new Client(
                id != null ? id : 0,
                codigo != null ? codigo : "",
                razaoSocial != null ? razaoSocial : "",
                nomeFantasia != null ? nomeFantasia : "",
                cnpj != null ? cnpj : "",
                ramoAtividade != null ? ramoAtividade : "",
                endereco != null ? endereco : "",
                status != null ? status : "",
                cpf != null ? cpf : "",
                contactList
        );
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getRazaoSocial() { return razaoSocial; }
    public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }

    public String getNomeFantasia() { return nomeFantasia; }
    public void setNomeFantasia(String nomeFantasia) { this.nomeFantasia = nomeFantasia; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getRamoAtividade() { return ramoAtividade; }
    public void setRamoAtividade(String ramoAtividade) { this.ramoAtividade = ramoAtividade; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<ApiContact> getContatos() { return contatos; }
    public void setContatos(List<ApiContact> contatos) { this.contatos = contatos; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
}
