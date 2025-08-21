package com.maximatech.provaandroid.presentation.features.orders;

import android.util.Log;

import java.util.List;

public class OrderLegendIconHelper {

    public static class LegendIconResult {
        public final long backgroundColor;
        public final long textColor;
        public final String text;
        public final Integer iconResource;
        public final boolean showIcon;

        public LegendIconResult(long backgroundColor, long textColor, String text, Integer iconResource, boolean showIcon) {
            this.backgroundColor = backgroundColor;
            this.textColor = textColor;
            this.text = text;
            this.iconResource = iconResource;
            this.showIcon = showIcon;
        }
    }

    public static LegendIconResult getLegendIconConfig(List<String> legendas, String tipo, String status) {
        long whiteText = 0xFFFFFFFFL;
        long blackText = 0xFF000000L;

        if (status != null && status.toLowerCase().contains("processamento")) {
            return new LegendIconResult(0xFF9E9E9EL, whiteText, null,
                    com.maximatech.provaandroid.R.drawable.ic_maxima_em_processamento, true);
        }

        if (legendas.contains("PEDIDO_RECUSADO_ERP")) {
            return new LegendIconResult(0xFFFFC107L, blackText, "!", null, false);
        }

        if (status != null && status.equalsIgnoreCase("Pendente")) {
            return new LegendIconResult(0xFF757575L, whiteText, "P", null, false);
        }

        if (legendas.contains("PEDIDO_PENDENTE_ERP")) {
            return new LegendIconResult(0xFF757575L, whiteText, "P", null, false);
        }

        if (legendas.contains("PEDIDO_BLOQUEADO_ERP")) {
            return new LegendIconResult(0xFF9C27B0L, whiteText, "B", null, false);
        }

        if (legendas.contains("PEDIDO_LIBERADO_ERP")) {
            return new LegendIconResult(0xFF2196F3L, whiteText, "L", null, false);
        }

        if (legendas.contains("PEDIDO_MONTADO_ERP")) {
            return new LegendIconResult(0xFF4CAF50L, whiteText, "M", null, false);
        }

        if (legendas.contains("PEDIDO_FATURADO_ERP")) {
            return new LegendIconResult(0xFF388E3CL, whiteText, "F", null, false);
        }

        if (legendas.contains("PEDIDO_CANCELADO_ERP")) {
            return new LegendIconResult(0xFFF44336L, whiteText, "C", null, false);
        }

        if (tipo != null && tipo.equalsIgnoreCase("ORCAMENTO")) {
            return new LegendIconResult(0xFF424242L, whiteText, "O", null, false);
        }

        String firstChar = (status != null && !status.isEmpty())
                ? status.substring(0, 1).toUpperCase()
                : "?";
        return new LegendIconResult(0xFF2196F3L, whiteText, firstChar, null, false);
    }
}
