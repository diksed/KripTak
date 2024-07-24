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
import com.diksed.kriptak.utils.formatters.formatMarketCap

@Composable
fun CoinDetailsContent(selectedCoin: Coin) {
    val formattedMarketCapDominance =
        decimalFormat.format(selectedCoin.quote.usd.marketCapDominance)
    val formattedVolumeChange = decimalFormat.format(selectedCoin.quote.usd.volumeChange24h)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 12.dp)
    ) {
        DetailRow(
            icon = painterResource(id = R.drawable.ic_attach_money),
            label = stringResource(id = R.string.marketCap),
            value = formatMarketCap(selectedCoin.quote.usd.marketCap)
        )
        DetailRow(
            icon = painterResource(id = R.drawable.ic_pie_chart),
            label = stringResource(id = R.string.marketCapDominance),
            value = "% $formattedMarketCapDominance"
        )
        DetailRow(
            icon = painterResource(id = R.drawable.ic_trending_flat),
            label = stringResource(id = R.string.volume24h),
            value = formatMarketCap(selectedCoin.quote.usd.volume24h)
        )
        DetailRow(
            icon = painterResource(id = if (selectedCoin.quote.usd.volumeChange24h > 0) R.drawable.ic_trending_up else R.drawable.ic_trending_down),
            label = stringResource(id = R.string.volume24hChange),
            value = "% $formattedVolumeChange",
            valueColor = when {
                selectedCoin.quote.usd.volumeChange24h > 0 -> Color.Green
                selectedCoin.quote.usd.volumeChange24h < 0 -> Color.Red
                else -> Color.White
            }
        )
        DetailRow(
            icon = painterResource(id = R.drawable.ic_wallet),
            label = stringResource(id = R.string.circulatingSupply),
            value = formatMarketCap(selectedCoin.circulatingSupply)
        )
    }
}
