package com.diksed.kriptak.utils.formatters

import androidx.compose.runtime.Composable
import com.diksed.kriptak.utils.Currency
import com.diksed.kriptak.utils.LocalKripTakApp
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

val decimalFormat = DecimalFormat("#0.00")

@Composable
fun formatMarketCap(valueUsd: Double): String {
    val app = LocalKripTakApp.current
    val currency = app?.currency?.value ?: Currency.USD
    val rate = app?.usdToTryRate?.value
    val value = if (currency == Currency.TRY && rate != null) valueUsd * rate else valueUsd

    val symbols = DecimalFormatSymbols().apply {
        decimalSeparator = ','
        groupingSeparator = '.'
    }

    val formatter = DecimalFormat("#,##0.00", symbols)
    return "${currency.symbol} ${formatter.format(value)}"
}
