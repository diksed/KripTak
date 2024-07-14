package com.diksed.kriptak.features.screen.home

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.diksed.kriptak.data.model.Article
import com.diksed.kriptak.data.model.CoinResponse
import com.diksed.kriptak.features.component.KripTakScaffold
import com.diksed.kriptak.features.component.KripTakTopBar
import com.diksed.kriptak.features.component.shimmer.current_news.CurrentNewsShimmerEffect
import com.diksed.kriptak.features.component.shimmer.trending_coins.TrendingCoinsShimmerEffect
import com.diksed.kriptak.features.screen.home.components.current_news.CurrentNewsBox
import com.diksed.kriptak.features.screen.home.components.trending_coins.TrendingCoinsBox


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToNews: () -> Unit,
    navigateToCrypto: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val viewState by viewModel.uiState.collectAsState()

    KripTakScaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        content = {
            Content(
                navigateToNews,
                navigateToCrypto,
                viewState.dailyTrendingCoins,
                viewState.dailyNews,
                viewState.isLoading
            )
        },
    )
}

@Composable
private fun Content(
    navigateToNews: () -> Unit,
    navigateToCrypto: () -> Unit,
    trendingCoins: List<CoinResponse?>,
    currentNews: List<Article>,
    isLoading: Boolean
) {
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
                    TrendingCoinsShimmerEffect(isDailyCoins = true, coinsCount = 3)
                    Spacer(modifier = Modifier.height(20.dp))
                    CurrentNewsShimmerEffect(isDailyNews = true, newsCount = 3)
                } else {
                    TrendingCoinsBox(
                        isDailyCoins = true,
                        trendingCoins = trendingCoins,
                        navigateToCrypto = navigateToCrypto
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    CurrentNewsBox(
                        isDailyNews = true,
                        currentNews = currentNews,
                        navigateToNews = navigateToNews
                    )
                }
            }
        }
    }
}
