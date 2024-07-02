package com.diksed.kriptak.features.screen.home.components.trending_coins

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.diksed.kriptak.data.model.CoinResponse
import com.diksed.kriptak.features.component.BoxShape
import com.diksed.kriptak.features.ui.theme.boxColor
import com.diksed.kriptak.utils.COIN_IMAGE_URL
import com.diksed.kriptak.utils.SPARKLINE_URL
import com.diksed.kriptak.utils.components.getCornerRadius
import com.diksed.kriptak.utils.formatPrice

@Composable
fun TrendingCoinsItem(trendCoin: CoinResponse, boxShape: BoxShape) {
    val coinData = trendCoin.data[0]
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
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CoinImage(imageUrl = imageUrl, modifier = Modifier.weight(1f).background(Color.Red))
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .align(Alignment.CenterVertically).background(Color.Green).weight(1f)
            ) {
                Text(
                    text = coinData.name,
                    color = Color.White,
                    fontSize = 15.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = coinData.symbol,
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.width(5.dp))
            CoinSparklineImage(
                sparkLine = sparkLine,
                percentChange24h = percentChange24h,
                modifier = Modifier.weight(1.1f)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                textAlign = TextAlign.Center,
                text = formattedPrice,
                color = Color.White,
                fontSize = 13.sp,
                modifier = Modifier
                    .weight(1f)
            )
            PercentChangeRow(percentChange24h = percentChange24h, modifier = Modifier.weight(1.2f))
        }
    }
}
