package com.maximatech.provaandroid.core.utils

fun formatTime(dateString: String): String {
    return try {
        if (dateString.contains("T") && dateString.length >= 16) {
            dateString.substring(11, 16)
        } else {
            "00:00"
        }
    } catch (e: Exception) {
        "00:00"
    }
}