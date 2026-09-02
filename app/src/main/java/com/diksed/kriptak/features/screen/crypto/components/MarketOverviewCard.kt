package com.diksed.kriptak.features.screen.crypto.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.diksed.kriptak.R
import com.diksed.kriptak.features.component.KripTakText
import com.diksed.kriptak.features.ui.theme.Gray69
import com.diksed.kriptak.features.ui.theme.PaleViolet
import com.diksed.kriptak.features.ui.theme.boxColor
import com.diksed.kriptak.utils.formatters.formatCompactValue
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

/**
 * Global market snapshot from CoinMarketCap's /v1/global-metrics/quotes/latest
 * (see CryptoViewModel.fetchGlobalMetrics). Hidden until that call succeeds -
 * we'd rather show nothing than a stale/wrong number.
 */
@Composable
fun MarketOverviewCard(
    totalMarketCap: Double?,
    activeCryptocurrencies: Int?,
    modifier: Modifier = Modifier
) {
    if (totalMarketCap == null && activeCryptocurrencies == null) return

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clip(RoundedCornerShape(12.dp))
            .background(boxColor)
    ) {
        Box(
            modifier = Modifier
                .width(4.dp)
                .fillMaxHeight()
                .background(PaleViolet)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (totalMarketCap != null) {
                OverviewStat(
                    label = stringResource(id = R.string.marketCap),
                    value = formatCompactValue(totalMarketCap),
                    modifier = Modifier.weight(1f)
                )
            }
            if (totalMarketCap != null && activeCryptocurrencies != null) {
                OverviewDivider()
            }
            if (activeCryptocurrencies != null) {
                OverviewStat(
                    label = stringResource(id = R.string.coinCount),
                    value = formatCoinCount(activeCryptocurrencies),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun OverviewStat(label: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        KripTakText(text = label, fontSize = 12.sp, color = Gray69)
        KripTakText(text = value, fontSize = 18.sp)
    }
}

@Composable
private fun OverviewDivider() {
    // Needs its own horizontal breathing room - sitting flush against the
    // weighted stat columns on either side made it look like a stray "|"
    // glued to the next label instead of a separator.
    Box(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .width(1.dp)
            .height(32.dp)
            .background(Gray69)
    )
}

private fun formatCoinCount(count: Int): String {
    val symbols = DecimalFormatSymbols().apply { groupingSeparator = '.' }
    return DecimalFormat("#,###", symbols).format(count)
}
