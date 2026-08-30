package com.diksed.kriptak.utils.formatters

import androidx.compose.runtime.Composable
import com.diksed.kriptak.utils.Currency
import com.diksed.kriptak.utils.LocalKripTakApp
import java.math.BigDecimal

/**
 * Formats a USD price for display, converting to the app's currently selected
 * currency (see LocalKripTakApp) and prefixing the right symbol.
 */
@Composable
fun formatPrice(priceUsd: Double): String {
    val app = LocalKripTakApp.current
    val currency = app?.currency?.value ?: Currency.USD
    val rate = app?.usdToTryRate?.value
    val price = if (currency == Currency.TRY && rate != null) priceUsd * rate else priceUsd

    val priceString = BigDecimal(price).toPlainString()
    val parts = priceString.split(".")
    val wholeNumber = parts[0]
    val decimalNumber = if (parts.size > 1) parts[1] else ""

    val firstNonZeroIndex = decimalNumber.indexOfFirst { it != '0' }

    val formattedNumber = if (firstNonZeroIndex == -1) {
        "$wholeNumber,0"
    } else {
        val formattedDecimal = decimalNumber.substring(0, firstNonZeroIndex + 1)
        if (formattedDecimal.isNotEmpty()) "$wholeNumber,$formattedDecimal" else wholeNumber
    }

    return "${currency.symbol}$formattedNumber"
}
