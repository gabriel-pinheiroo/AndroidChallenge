package com.maximatech.provaandroid.presentation.features.orders;

import com.maximatech.provaandroid.R;

public class OrderDialogLegendIconHelper {

    public static Integer getLegendaIconResource(String legenda) {
        if (legenda == null) return null;

        switch (legenda) {
            case "PEDIDO_SOFREU_CORTE":
                return R.drawable.ic_maxima_legenda_corte;
            case "PEDIDO_COM_FALTA":
                return R.drawable.ic_maxima_legenda_falta;
            case "PEDIDO_CANCELADO_ERP":
                return R.drawable.ic_maxima_legenda_cancelamento;
            case "PEDIDO_COM_DEVOLUCAO":
                return R.drawable.ic_maxima_legenda_devolucao;
            case "PEDIDO_FEITO_TELEMARKETING":
                return R.drawable.ic_maxima_legenda_telemarketing;
            default:
                return null;
        }
    }

    public static boolean hasIcon(String legenda) {
        return getLegendaIconResource(legenda) != null;
    }
}