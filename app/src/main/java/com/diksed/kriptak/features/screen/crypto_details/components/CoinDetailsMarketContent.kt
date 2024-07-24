package com.diksed.kriptak.features.screen.crypto_details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.diksed.kriptak.R
import com.diksed.kriptak.data.model.Coin
import com.diksed.kriptak.utils.formatters.decimalFormat
import com.diksed.kriptak.utils.formatters.formatLastUpdated

@Composable
fun CoinDetailsMarketContent(selectedCoin: Coin) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 12.dp)
    ) {
        val formatted1hChange = decimalFormat.format(selectedCoin.quote.usd.percentChange1h)
        val formatted24hChange = decimalFormat.format(selectedCoin.quote.usd.percentChange24h)
        val formatted7dChange = decimalFormat.format(selectedCoin.quote.usd.percentChange7d)

        DetailRow(
            icon = painterResource(id = R.drawable.ic_hourglass_empty),
            label = stringResource(id = R.string.percentChange1h),
            value = "% $formatted1hChange",
            valueColor = when {
                selectedCoin.quote.usd.percentChange1h > 0 -> Color.Green
                selectedCoin.quote.usd.percentChange1h < 0 -> Color.Red
                else -> Color.White
            }
        )
        DetailRow(
            icon = painterResource(id = R.drawable.ic_calendar_today),
            label = stringResource(id = R.string.percentChange24h),
            value = "% $formatted24hChange",
            valueColor = when {
                selectedCoin.quote.usd.percentChange24h > 0 -> Color.Green
                selectedCoin.quote.usd.percentChange24h < 0 -> Color.Red
                else -> Color.White
            }
        )
        DetailRow(
            icon = painterResource(id = R.drawable.ic_calendar),
            label = stringResource(id = R.string.percentChange7d),
            value = "% $formatted7dChange",
            valueColor = when {
                selectedCoin.quote.usd.percentChange7d > 0 -> Color.Green
                selectedCoin.quote.usd.percentChange7d < 0 -> Color.Red
                else -> Color.White
            }
        )

        DetailRow(
            icon = painterResource(id = R.drawable.ic_clock),
            label = stringResource(id = R.string.lastUpdated),
            value = formatLastUpdated(selectedCoin.quote.usd.lastUpdated)
        )
    }
}
