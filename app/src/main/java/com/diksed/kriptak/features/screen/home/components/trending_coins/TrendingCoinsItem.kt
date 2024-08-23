package com.diksed.kriptak.features.screen.home.components.trending_coins

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.diksed.kriptak.data.model.Coin
import com.diksed.kriptak.data.model.CoinResponse
import com.diksed.kriptak.features.component.BoxShape
import com.diksed.kriptak.features.component.KripTakText
import com.diksed.kriptak.features.screen.crypto.components.CryptoNameSymbolColumn
import com.diksed.kriptak.features.ui.theme.boxColor
import com.diksed.kriptak.utils.COIN_IMAGE_URL
import com.diksed.kriptak.utils.SPARKLINE_URL
import com.diksed.kriptak.utils.components.getCornerRadius
import com.diksed.kriptak.utils.formatters.formatPrice

@Composable
fun <T> TrendingCoinsItem(
    navigateToCryptoDetails: (Coin) -> Unit = {},
    trendCoin: T,
    boxShape: BoxShape
) {
    val coinData = when (trendCoin) {
        is CoinResponse -> trendCoin.data[0]
        is Coin -> trendCoin
        else -> throw IllegalArgumentException("Unsupported coin type")
    }

    val cornerRadius = getCornerRadius(boxShape)
    val percentChange24h = coinData.quote.usd.percentChange24h
    val price = coinData.quote.usd.price
    val formattedPrice = "$${formatPrice(price)}"
    val imageUrl = COIN_IMAGE_URL + coinData.id + ".png"
    val sparkLine = SPARKLINE_URL + coinData.id + ".png"

    Box(
        modifier = Modifier
            .background(boxColor, shape = cornerRadius)
            .padding(horizontal = 4.dp, vertical = 8.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                navigateToCryptoDetails.invoke(coinData)
            }
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CoinImage(imageUrl = imageUrl, modifier = Modifier.scale(0.9f))
            Spacer(modifier = Modifier.width(4.dp))
            CryptoNameSymbolColumn(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1f),
                cryptoName = coinData.name,
                cryptoSymbol = coinData.symbol
            )
            Spacer(modifier = Modifier.width(5.dp))
            CoinSparklineImage(
                sparkLine = sparkLine,
                percentChange24h = percentChange24h,
                modifier = Modifier.weight(1.1f)
            )
            Spacer(modifier = Modifier.width(2.dp))
            KripTakText(
                textAlign = TextAlign.Center,
                text = formattedPrice,
                fontSize = 13.sp,
                modifier = Modifier.weight(0.9f)
            )
            PercentChangeRow(percentChange24h = percentChange24h, modifier = Modifier.weight(1.3f))
        }
    }
}
