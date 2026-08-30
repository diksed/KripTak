package com.diksed.kriptak.ui.utils

import java.util.Locale

fun coinPriceFormat(price: Double): String {
    val formattedPrice = "%.2f".format(Locale.US, price)

    return if (formattedPrice.endsWith("00")) {
        formattedPrice.dropLast(1) + "1"
    } else {
        formattedPrice
    }
}