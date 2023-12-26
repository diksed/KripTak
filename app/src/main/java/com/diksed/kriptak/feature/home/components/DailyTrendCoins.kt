package com.diksed.kriptak.feature.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.diksed.kriptak.R
import com.diksed.kriptak.feature.home.HomeUiState
import com.diksed.kriptak.ui.components.CryptoBox

@Composable
fun DailyTrendCoins(homeUiState: HomeUiState) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(id = R.string.daily_trends),
            style = MaterialTheme.typography.titleSmall,
        )
        LazyColumn(content = {
            items(homeUiState.trendingCoins.take(3).size) { index ->
                CryptoBox(coin = homeUiState.trendingCoins[index])
            }
        }, verticalArrangement = Arrangement.spacedBy(5.dp))
    }
}