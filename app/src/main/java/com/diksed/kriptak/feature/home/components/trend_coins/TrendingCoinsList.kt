package com.diksed.kriptak.feature.home.components.trend_coins

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.diksed.kriptak.core.data.model.CoinGeckoTrendCoinResponse
import com.diksed.kriptak.ui.components.CryptoBox

@Composable
fun TrendingCoinsList(trendingCoins: List<CoinGeckoTrendCoinResponse.CoinItemDetails>) {
    LazyColumn(content = {
        items(trendingCoins.take(3).size) { index ->
            CryptoBox(trendingCoins[index])
        }
    }, verticalArrangement = Arrangement.spacedBy(5.dp))
}
