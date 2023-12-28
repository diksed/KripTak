package com.diksed.kriptak.feature.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.diksed.kriptak.R
import com.diksed.kriptak.feature.home.HomeUiState
import com.diksed.kriptak.feature.home.components.trend_coins.TrendingCoinsList
import com.diksed.kriptak.ui.components.CustomLottieAnimation

@Composable
fun DailyTrendCoins(homeUiState: HomeUiState, modifier: Modifier = Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        Text(
            text = stringResource(id = R.string.daily_trends),
            style = MaterialTheme.typography.titleSmall,
        )
        if (homeUiState.isLoading) {
            CustomLottieAnimation(
                animation = R.raw.kriptak_loading,
                modifier = Modifier
                    .size(200.dp)
                    .scale(1.5f)
            )
        } else {
            TrendingCoinsList(trendingCoins = homeUiState.trendingCoins)
        }
    }
}
