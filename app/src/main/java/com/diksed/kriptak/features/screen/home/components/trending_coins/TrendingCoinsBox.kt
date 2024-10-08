package com.diksed.kriptak.features.screen.home.components.trending_coins

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.diksed.kriptak.R
import com.diksed.kriptak.data.model.Coin
import com.diksed.kriptak.features.component.BoxShape
import com.diksed.kriptak.features.component.KripTakCurrentBoxTextButton
import com.diksed.kriptak.features.component.KripTakCurrentBoxTitle

@Composable
fun <T> TrendingCoinsBox(
    navigateToCryptoDetails: (Coin) -> Unit = {},
    trendingCoins: List<T?>,
    navigateToCrypto: () -> Unit = {},
    isDailyCoins: Boolean = false,
    titleId: Int = R.string.trendsOfDay
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (isDailyCoins) KripTakCurrentBoxTitle(title = stringResource(id = titleId))
        trendingCoins.forEachIndexed { index, coin ->
            val boxShape = when (index) {
                0 -> BoxShape.TOP
                trendingCoins.size - 1 -> BoxShape.BOTTOM
                else -> BoxShape.MIDDLE
            }
            if (coin != null) {
                TrendingCoinsItem(
                    trendCoin = coin,
                    boxShape = boxShape,
                    navigateToCryptoDetails = navigateToCryptoDetails
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
        }
        if (isDailyCoins && titleId == R.string.trendsOfDay) {
            KripTakCurrentBoxTextButton(
                text = stringResource(id = R.string.allCrypto),
                navigateTo = navigateToCrypto
            )
        }
    }
}
