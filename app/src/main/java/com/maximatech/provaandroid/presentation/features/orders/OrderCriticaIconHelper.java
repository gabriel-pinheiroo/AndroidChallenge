package com.maximatech.provaandroid.presentation.features.orders;

import com.maximatech.provaandroid.R;

public class OrderCriticaIconHelper {

    public static int getCriticaIconResource(String critica) {
        if (critica == null || critica.trim().isEmpty()) {
            return R.drawable.ic_maxima_aguardando_critica;
        }

        switch (critica.toUpperCase().trim()) {
            case "SUCESSO":
                return R.drawable.ic_maxima_critica_sucesso;
            case "FALHA_PARCIAL":
                return R.drawable.ic_maxima_critica_alerta;
            case "FALHA_TOTAL":
                return R.drawable.ic_maxima_legenda_cancelamento;
            default:
                return R.drawable.ic_maxima_aguardando_critica;
        }
    }

    public static String getCriticaDescription(String critica) {
        if (critica == null || critica.trim().isEmpty()) {
            return "Aguardando crítica";
        }

        switch (critica.toUpperCase().trim()) {
            case "SUCESSO":
                return "Crítica bem-sucedida";
            case "FALHA_PARCIAL":
                return "Falha parcial na crítica";
            case "FALHA_TOTAL":
                return "Falha total na crítica";
            default:
                return "Status de crítica desconhecido";
        }
    }
}