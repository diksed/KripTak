package com.diksed.kriptak.features.screen.crypto

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberScaffoldState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.diksed.kriptak.data.model.Coin
import com.diksed.kriptak.features.component.KripTakScaffold
import com.diksed.kriptak.features.component.KripTakTopBar
import com.diksed.kriptak.features.component.shimmer.trending_coins.TrendingCoinsShimmerEffect
import com.diksed.kriptak.features.screen.home.components.trending_coins.TrendingCoinsBox

@Composable
fun CryptoScreen(
    viewModel: CryptoViewModel = hiltViewModel(),
) {
    val scaffoldState = rememberScaffoldState()
    val viewState by viewModel.uiState.collectAsState()

    KripTakScaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        content = {
            Content(
                isLoading = viewState.isLoading,
                coins = viewState.coins
            )
        },
    )

}

@Composable
private fun Content(
    coins: List<Coin?>,
    isLoading: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp, bottom = 80.dp),
    ) {
        println("coins: ${coins.size}")
        LazyColumn {
            item {
                KripTakTopBar()
            }
            item {
                if (isLoading) {
                    TrendingCoinsShimmerEffect()
                } else {
                    TrendingCoinsBox(trendingCoins = coins)
                }
            }
        }
    }
}
