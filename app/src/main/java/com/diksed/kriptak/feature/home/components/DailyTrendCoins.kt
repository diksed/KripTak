package com.diksed.kriptak.feature.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.diksed.kriptak.R
import com.diksed.kriptak.feature.home.HomeUiState
import com.diksed.kriptak.feature.home.components.trend_coins.TrendingCoinsList
import com.diksed.kriptak.ui.utils.AnimatedShimmer

@Composable
fun DailyTrendCoins(homeUiState: HomeUiState, modifier: Modifier = Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        Text(
            text = stringResource(id = R.string.daily_trends),
            style = MaterialTheme.typography.titleSmall,
        )
        if (homeUiState.isLoading) {
            AnimatedShimmer()
        } else {
            TrendingCoinsList(trendingCoins = homeUiState.trendingCoins)
        }
    }
}
