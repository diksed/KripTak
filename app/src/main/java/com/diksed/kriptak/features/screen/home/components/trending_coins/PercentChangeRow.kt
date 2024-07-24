package com.diksed.kriptak.features.screen.home.components.trending_coins

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.diksed.kriptak.R
import java.text.DecimalFormat

@Composable
fun PercentChangeRow(
    percentChange24h: Double,
    modifier: Modifier,
    isDetailsScreen: Boolean = false
) {
    val decimalFormat = DecimalFormat("#0.00")
    val formattedPercentChange = decimalFormat.format(percentChange24h)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = modifier
    ) {
        val iconResId = when {
            percentChange24h > 0 -> R.drawable.ic_up_arrow
            percentChange24h < 0 -> R.drawable.ic_down_arrow
            else -> R.drawable.ic_minus
        }
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            tint = if (percentChange24h > 0) Color.Green else if (percentChange24h < 0) Color.Red else Color.White
        )
        Text(
            text = "% $formattedPercentChange" + if (isDetailsScreen) " (24h)" else "",
            color = if (percentChange24h > 0) Color.Green else if (percentChange24h < 0) Color.Red else Color.White,
            fontSize = 13.sp
        )
    }
}
