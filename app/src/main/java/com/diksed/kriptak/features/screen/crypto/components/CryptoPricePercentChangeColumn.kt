package com.diksed.kriptak.features.screen.crypto.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.diksed.kriptak.features.component.KripTakText
import com.diksed.kriptak.features.screen.home.components.trending_coins.PercentChangeRow

@Composable
fun CryptoPricePercentChangeColumn(
    formattedPrice: String,
    percentChange24h: Double?,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End,
        modifier = modifier
    ) {
        KripTakText(
            text = formattedPrice,
            fontSize = 20.sp
        )
        if (percentChange24h != null) {
            PercentChangeRow(
                percentChange24h = percentChange24h,
                modifier = Modifier,
                isDetailsScreen = true
            )
        }
    }
}