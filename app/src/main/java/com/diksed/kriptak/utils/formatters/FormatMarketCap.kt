package com.diksed.kriptak.utils.formatters

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

val decimalFormat = DecimalFormat("#0.00")

fun formatMarketCap(value: Double): String {
    val symbols = DecimalFormatSymbols().apply {
        decimalSeparator = ','
        groupingSeparator = '.'
    }

    val formatter = DecimalFormat("#,##0.00", symbols)
    return "$ ${formatter.format(value)}"
}