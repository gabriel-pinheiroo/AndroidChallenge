package com.maximatech.provaandroid.core.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {
    private int numeroPedRca;
    private String numeroPedErp;
    private String codigoCliente;
    private String nomeCliente;
    private String data;
    private String status;
    private String critica;
    private String tipo;
    private List<String> legendas;

    public static final Order EMPTY = new Order(
            0, "", "", "", "", "", "", "", new ArrayList<>()
    );

    public Order() {
        this.legendas = new ArrayList<>();
    }

    public Order(int numeroPedRca, String numeroPedErp, String codigoCliente,
                 String nomeCliente, String data, String status, String critica,
                 String tipo, List<String> legendas) {
        this.numeroPedRca = numeroPedRca;
        this.numeroPedErp = numeroPedErp;
        this.codigoCliente = codigoCliente;
        this.nomeCliente = nomeCliente;
        this.data = data;
        this.status = status;
        this.critica = critica;
        this.tipo = tipo;
        this.legendas = legendas != null ? legendas : new ArrayList<>();
    }

    public int getNumeroPedRca() { return numeroPedRca; }
    public String getNumeroPedErp() { return numeroPedErp; }
    public String getCodigoCliente() { return codigoCliente; }
    public String getNomeCliente() { return nomeCliente; }
    public String getData() { return data; }
    public String getStatus() { return status; }
    public String getCritica() { return critica; }
    public String getTipo() { return tipo; }
    public List<String> getLegendas() { return legendas; }

    public void setNumeroPedRca(int numeroPedRca) { this.numeroPedRca = numeroPedRca; }
    public void setNumeroPedErp(String numeroPedErp) { this.numeroPedErp = numeroPedErp; }
    public void setCodigoCliente(String codigoCliente) { this.codigoCliente = codigoCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }
    public void setData(String data) { this.data = data; }
    public void setStatus(String status) { this.status = status; }
    public void setCritica(String critica) { this.critica = critica; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setLegendas(List<String> legendas) {
        this.legendas = legendas != null ? legendas : new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return numeroPedRca == order.numeroPedRca &&
                Objects.equals(numeroPedErp, order.numeroPedErp) &&
                Objects.equals(codigoCliente, order.codigoCliente) &&
                Objects.equals(nomeCliente, order.nomeCliente) &&
                Objects.equals(data, order.data) &&
                Objects.equals(status, order.status) &&
                Objects.equals(critica, order.critica) &&
                Objects.equals(tipo, order.tipo) &&
                Objects.equals(legendas, order.legendas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroPedRca, numeroPedErp, codigoCliente, nomeCliente,
                data, status, critica, tipo, legendas);
    }

    @Override
    public String toString() {
        return "Order{" +
                "numeroPedRca=" + numeroPedRca +
                ", numeroPedErp='" + numeroPedErp + '\'' +
                ", codigoCliente='" + codigoCliente + '\'' +
                ", nomeCliente='" + nomeCliente + '\'' +
                ", data='" + data + '\'' +
                ", status='" + status + '\'' +
                ", critica='" + critica + '\'' +
                ", tipo='" + tipo + '\'' +
                ", legendas=" + legendas +
                '}';
    }
}