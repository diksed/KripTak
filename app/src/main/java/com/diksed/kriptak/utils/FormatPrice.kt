package com.diksed.kriptak.utils

import java.math.BigDecimal

fun formatPrice(price: Double): String {
    val priceString = BigDecimal(price).toPlainString()
    val parts = priceString.split(".")
    val wholeNumber = parts[0]
    val decimalNumber = if (parts.size > 1) parts[1] else ""

    val firstNonZeroIndex = decimalNumber.indexOfFirst { it != '0' }

    if (firstNonZeroIndex == -1) {
        return "$wholeNumber,0"
    }

    val formattedDecimal = decimalNumber.substring(0, firstNonZeroIndex + 1)

    return if (formattedDecimal.isNotEmpty()) {
        "$wholeNumber,$formattedDecimal"
    } else {
        wholeNumber
    }
}