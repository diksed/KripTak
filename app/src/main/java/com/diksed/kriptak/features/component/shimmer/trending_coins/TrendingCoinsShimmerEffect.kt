package com.diksed.kriptak.features.component.shimmer.trending_coins

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.diksed.kriptak.R
import com.diksed.kriptak.features.component.KripTakCurrentBoxTextButton
import com.diksed.kriptak.features.component.KripTakCurrentBoxTitle
import com.diksed.kriptak.features.screen.crypto.components.CryptoListItemShimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun TrendingCoinsShimmerEffect(
    isDailyCoins: Boolean = false,
    coinsCount: Int = 10,
    titleId: Int = R.string.trendsOfDay
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (isDailyCoins) {
            KripTakCurrentBoxTitle(
                title = stringResource(id = titleId),
                modifier = Modifier.shimmer()
            )
        }
        repeat(coinsCount) {
            CryptoListItemShimmer()
            Spacer(modifier = Modifier.height(5.dp))
        }
        if (isDailyCoins && titleId == R.string.trendsOfDay) {
            KripTakCurrentBoxTextButton(
                text = stringResource(id = R.string.allCrypto),
                modifier = Modifier.shimmer()
            )
        }
    }
}
