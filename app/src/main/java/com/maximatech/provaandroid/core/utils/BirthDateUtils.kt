package com.maximatech.provaandroid.core.utils

import java.text.SimpleDateFormat
import java.util.*

fun formatBrazilianDateWithValidation(isoDate: String?): String {
    if (isoDate.isNullOrBlank()) return "Não informado"

    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        val date = inputFormat.parse(isoDate)
        date?.let { outputFormat.format(it) } ?: isoDate
    } catch (e: Exception) {
        isoDate
    }
}