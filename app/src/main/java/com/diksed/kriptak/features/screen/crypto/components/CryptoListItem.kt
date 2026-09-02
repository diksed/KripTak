package com.diksed.kriptak.features.screen.crypto.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.diksed.kriptak.data.model.Coin
import com.diksed.kriptak.data.model.CoinResponse
import com.diksed.kriptak.features.component.KripTakText
import com.diksed.kriptak.features.screen.home.components.trending_coins.CoinImage
import com.diksed.kriptak.features.screen.home.components.trending_coins.CoinSparklineImage
import com.diksed.kriptak.features.ui.theme.PaleViolet
import com.diksed.kriptak.features.ui.theme.boxColor
import com.diksed.kriptak.utils.COIN_IMAGE_URL
import com.diksed.kriptak.utils.SPARKLINE_URL
import com.diksed.kriptak.utils.formatters.decimalFormat
import com.diksed.kriptak.utils.formatters.formatPrice
import com.diksed.kriptak.utils.rememberDominantColor

/**
 * Row design (colored accent bar, self-contained rounded card per row, tinted
 * percent-change pill) shared by the Crypto List, Home's trending coins, and
 * Favorites - anywhere a coin appears in a list. Generic like the old
 * TrendingCoinsItem it replaced: CoinResponse (single-coin API responses,
 * used by Home) and Coin (list/favorites responses) both resolve to the same
 * Coin shape underneath.
 */
@Composable
fun <T> CryptoListItem(trendCoin: T, navigateToCryptoDetails: (Coin) -> Unit = {}) {
    val coin = when (trendCoin) {
        is CoinResponse -> trendCoin.data[0]
        is Coin -> trendCoin
        else -> throw IllegalArgumentException("Unsupported coin type")
    }
    val percentChange24h = coin.quote.usd.percentChange24h
    val price = coin.quote.usd.price
    val formattedPrice = formatPrice(price)
    val imageUrl = COIN_IMAGE_URL + coin.id + ".png"
    val sparkLine = SPARKLINE_URL + coin.id + ".png"
    val accentColor = rememberDominantColor(imageUrl = imageUrl, fallback = PaleViolet)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clip(RoundedCornerShape(12.dp))
            .background(boxColor)
            .clickable { navigateToCryptoDetails(coin) }
    ) {
        Box(
            modifier = Modifier
                .width(4.dp)
                .fillMaxHeight()
                .background(accentColor)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CoinImage(imageUrl = imageUrl, modifier = Modifier.scale(0.85f))
            Spacer(modifier = Modifier.width(8.dp))
            CryptoNameSymbolColumn(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1f),
                cryptoName = coin.name,
                cryptoSymbol = coin.symbol
            )
            Spacer(modifier = Modifier.width(6.dp))
            CoinSparklineImage(
                sparkLine = sparkLine,
                percentChange24h = percentChange24h,
                modifier = Modifier.weight(0.9f)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.weight(1.3f)
            ) {
                KripTakText(
                    text = formattedPrice,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(4.dp))
                PercentChangePill(percentChange24h = percentChange24h)
            }
        }
    }
}

@Composable
private fun PercentChangePill(percentChange24h: Double) {
    val color = when {
        percentChange24h > 0 -> Color.Green
        percentChange24h < 0 -> Color.Red
        else -> Color.White
    }
    val formatted = decimalFormat.format(percentChange24h)

    KripTakText(
        text = "%$formatted",
        color = color,
        fontSize = 12.sp,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
            .background(color.copy(alpha = 0.15f), shape = RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 3.dp)
    )
}
