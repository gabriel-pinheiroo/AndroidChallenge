package com.maximatech.provaandroid.core.data.remote.dto;

import com.google.gson.annotations.SerializedName;

public class ApiClientResponse {
    @SerializedName("cliente")
    private ApiClient cliente;

    public ApiClientResponse() {}

    public ApiClientResponse(ApiClient cliente) {
        this.cliente = cliente;
    }

    public ApiClient getCliente() {
        return cliente;
    }

    public void setCliente(ApiClient cliente) {
        this.cliente = cliente;
    }
}

