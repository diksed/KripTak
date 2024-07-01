package com.diksed.kriptak.features.screen.home

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.diksed.kriptak.data.model.Article
import com.diksed.kriptak.data.model.CoinListResponse
import com.diksed.kriptak.features.component.KripTakScaffold
import com.diksed.kriptak.features.component.KripTakTopBar
import com.diksed.kriptak.features.component.shimmer.CurrentNewsShimmerEffect
import com.diksed.kriptak.features.screen.home.components.current_news.CurrentNewsBox


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToNews: () -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    val viewState by viewModel.uiState.collectAsState()

    KripTakScaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        content = {
            Content(
                navigateToNews,
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
    trendingCoins: List<CoinListResponse?>,
    currentNews: List<Article>,
    isLoading: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp),
    ) {
        Column {
            KripTakTopBar()
            if (isLoading) {
                CurrentNewsShimmerEffect()
            } else {
                CurrentNewsBox(currentNews = currentNews, navigateToNews = navigateToNews)
            }
        }
    }
}