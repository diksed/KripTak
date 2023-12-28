package com.diksed.kriptak.feature.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.diksed.kriptak.R
import com.diksed.kriptak.feature.home.HomeUiState
import com.diksed.kriptak.ui.components.CryptoBox

@Composable
fun DailyTrendCoins(homeUiState: HomeUiState, modifier: Modifier = Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        Text(
            text = stringResource(id = R.string.daily_trends),
            style = MaterialTheme.typography.titleSmall,
        )
        if (homeUiState.isLoading) {
            val composition by rememberLottieComposition(
                spec = LottieCompositionSpec.RawRes(R.raw.kriptak_loading)
            )
            LottieAnimation(
                modifier = Modifier.size(200.dp).scale(1.5f),
                composition = composition,
                iterations = LottieConstants.IterateForever,
            )
        } else {
            LazyColumn(content = {
                items(homeUiState.trendingCoins.take(3).size) { index ->
                    CryptoBox(coin = homeUiState.trendingCoins[index])
                }
            }, verticalArrangement = Arrangement.spacedBy(5.dp))
        }
    }
}