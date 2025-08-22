package com.maximatech.provaandroid.core.data.remote.dto;

import com.google.gson.annotations.SerializedName;
import com.maximatech.provaandroid.core.domain.model.Order;

import java.util.ArrayList;
import java.util.List;

public class ApiOrder {
    @SerializedName("numero_ped_Rca")
    private Integer numeroPedRca;

    @SerializedName("numero_ped_erp")
    private String numeroPedErp;

    @SerializedName("codigoCliente")
    private String codigoCliente;

    @SerializedName("NOMECLIENTE")
    private String nomeCliente;

    @SerializedName("data")
    private String data;

    @SerializedName("status")
    private String status;

    @SerializedName("critica")
    private String critica;

    @SerializedName("tipo")
    private String tipo;

    @SerializedName("legendas")
    private List<String> legendas;

    public ApiOrder() {}

    public Order toOrder() {
        return new Order(
                numeroPedRca != null ? numeroPedRca : 0,
                numeroPedErp != null ? numeroPedErp : "",
                codigoCliente != null ? codigoCliente : "",
                nomeCliente != null ? nomeCliente : "",
                data != null ? data : "",
                status != null ? status : "",
                critica != null ? critica : "",
                tipo != null ? tipo : "",
                legendas != null ? legendas : new ArrayList<>()
        );
    }

    public Integer getNumeroPedRca() { return numeroPedRca; }
    public void setNumeroPedRca(Integer numeroPedRca) { this.numeroPedRca = numeroPedRca; }

    public String getNumeroPedErp() { return numeroPedErp; }
    public void setNumeroPedErp(String numeroPedErp) { this.numeroPedErp = numeroPedErp; }

    public String getCodigoCliente() { return codigoCliente; }
    public void setCodigoCliente(String codigoCliente) { this.codigoCliente = codigoCliente; }

    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCritica() { return critica; }
    public void setCritica(String critica) { this.critica = critica; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public List<String> getLegendas() { return legendas; }
    public void setLegendas(List<String> legendas) { this.legendas = legendas; }
}
