package com.maximatech.provaandroid.core.data.remote.dto;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ApiOrdersResponse {
    @SerializedName("pedidos")
    private List<ApiOrder> pedidos;

    public ApiOrdersResponse() {}

    public ApiOrdersResponse(List<ApiOrder> pedidos) {
        this.pedidos = pedidos;
    }

    public List<ApiOrder> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<ApiOrder> pedidos) {
        this.pedidos = pedidos;
    }
}

