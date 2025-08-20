package com.maximatech.provaandroid.core.utils

fun formatBrazilianCellPhone(phoneNumber: String): String {
    return when {
        phoneNumber.length == 11 && phoneNumber[2] == '9' -> {
            val ddd = phoneNumber.substring(0, 2)
            val firstDigit = phoneNumber.substring(2, 3)
            val firstPart = phoneNumber.substring(3, 7)
            val secondPart = phoneNumber.substring(7, 11)

            "($ddd) $firstDigit $firstPart-$secondPart"
        }
        else -> phoneNumber
    }
}