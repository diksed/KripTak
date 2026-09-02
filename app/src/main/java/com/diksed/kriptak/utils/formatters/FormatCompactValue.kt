package com.diksed.kriptak.utils.formatters

import androidx.compose.runtime.Composable
import com.diksed.kriptak.utils.Currency
import com.diksed.kriptak.utils.LocalKripTakApp

/**
 * Abbreviates a large USD amount (trillions/billions/millions) for compact
 * display, e.g. in the market overview card - "$2.71T" style, but using the
 * same comma-decimal convention as formatPrice/formatMarketCap.
 */
@Composable
fun formatCompactValue(valueUsd: Double): String {
    val app = LocalKripTakApp.current
    val currency = app?.currency?.value ?: Currency.USD
    val rate = app?.usdToTryRate?.value
    val value = if (currency == Currency.TRY && rate != null) valueUsd * rate else valueUsd

    val absValue = kotlin.math.abs(value)
    val (scaled, suffix) = when {
        absValue >= 1_000_000_000_000.0 -> value / 1_000_000_000_000.0 to "T"
        absValue >= 1_000_000_000.0 -> value / 1_000_000_000.0 to "B"
        absValue >= 1_000_000.0 -> value / 1_000_000.0 to "M"
        else -> value to ""
    }

    val formatted = "%.2f".format(scaled).replace('.', ',')
    return "${currency.symbol}$formatted$suffix"
}
