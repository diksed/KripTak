package com.diksed.kriptak.features.screen.favorites

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
import com.diksed.kriptak.R
import com.diksed.kriptak.data.model.Coin
import com.diksed.kriptak.features.component.KripTakScaffold
import com.diksed.kriptak.features.component.KripTakTopBar
import com.diksed.kriptak.features.component.shimmer.trending_coins.TrendingCoinsShimmerEffect
import com.diksed.kriptak.features.screen.home.components.trending_coins.TrendingCoinsBox

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = hiltViewModel(),
) {
    val scaffoldState = rememberScaffoldState()
    val viewState by viewModel.uiState.collectAsState()

    KripTakScaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        content = {
            Content(
                isLoading = viewState.isLoading,
                favorites = viewState.favorites,
                favoriteCount = viewState.favoriteCount
            )
        },
    )
}

@Composable
private fun Content(
    isLoading: Boolean,
    favorites: List<Coin?>,
    favoriteCount: Int
) {
    val shimmerCount = if (isLoading) favoriteCount else favorites.size
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp, bottom = 80.dp),
    ) {
        LazyColumn {
            item {
                KripTakTopBar()
            }
            item {
                if (isLoading) {
                    TrendingCoinsShimmerEffect(
                        coinsCount = shimmerCount,
                        isDailyCoins = true,
                        titleId = R.string.favoritesCrypto
                    )
                } else {
                    TrendingCoinsBox(
                        trendingCoins = favorites,
                        isDailyCoins = true,
                        titleId = R.string.favoritesCrypto
                    )
                }
            }
        }
    }
}
