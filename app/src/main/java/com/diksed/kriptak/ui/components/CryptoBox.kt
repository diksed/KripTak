package com.diksed.kriptak.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.diksed.kriptak.R
import com.diksed.kriptak.core.data.model.CoinGeckoTrendCoinResponse
import com.diksed.kriptak.ui.theme.boxColor
import com.diksed.kriptak.ui.utils.SvgImage
import com.diksed.kriptak.ui.utils.coinPriceFormat

@Composable
fun CryptoBox(
    coin: CoinGeckoTrendCoinResponse.CoinItemDetails
) {
    val formattedPrice = coinPriceFormat(coin.data.price.substring(1).toDouble())
    val formattedPercentage = String.format("%.2f", coin.data.price_change_percentage_24h.usd)
    val priceChangePercentage = coin.data.price_change_percentage_24h.usd
    val icon = if (priceChangePercentage >= 0) {
        painterResource(id = R.drawable.baseline_arrow_drop_up_24)
    } else {
        painterResource(id = R.drawable.baseline_arrow_drop_down_24)
    }

    Card(
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(containerColor = boxColor, contentColor = boxColor),
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(80.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = coin.large,
                contentDescription = null,
                modifier = Modifier
                    .size(45.dp)
                    .weight(1f)
            )

            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = coin.name,
                    style = TextStyle(color = Color.White)
                )

                Text(
                    text = coin.symbol,
                    style = TextStyle(color = Color.Gray)
                )
            }

            SvgImage(
                image = "${"https://"}graphsv2.coinpaprika.com/currency/chart/${coin.symbol}-${coin.id}/24h/chart.svg",
                modifier = Modifier
                    .weight(1f)
            )

            Text(
                text = "${formattedPrice}$",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp)
            )

            Icon(
                painter = icon,
                contentDescription = null,
                tint = if (priceChangePercentage >= 0) Color.Green else Color.Red,
                modifier = Modifier
                    .size(24.dp)
                    .scale(1.5f)
                    .weight(0.3f)
            )
            Text(
                text = "%$formattedPercentage",
                style = TextStyle(
                    color = if (priceChangePercentage >= 0) Color.Green else Color.Red,
                ),
                modifier = Modifier
                    .weight(0.7f)
            )
        }
    }
}
