package com.diksed.kriptak.features.screen.crypto

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberScaffoldState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.diksed.kriptak.data.model.Coin
import com.diksed.kriptak.features.component.BoxShape
import com.diksed.kriptak.features.component.KripTakCircularProgressIndicator
import com.diksed.kriptak.features.component.KripTakScaffold
import com.diksed.kriptak.features.component.KripTakSearchField
import com.diksed.kriptak.features.component.KripTakTopBar
import com.diksed.kriptak.features.component.SortDirection
import com.diksed.kriptak.features.component.SortType
import com.diksed.kriptak.features.component.coin_filter.KripTakSortRow
import com.diksed.kriptak.features.component.shimmer.trending_coins.TrendingCoinsShimmerEffect
import com.diksed.kriptak.features.screen.home.components.trending_coins.TrendingCoinsItem

@Composable
fun CryptoScreen(
    viewModel: CryptoViewModel = hiltViewModel(),
) {
    val scaffoldState = rememberScaffoldState()
    val viewState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val sortType by viewModel.sortType.collectAsState()
    val sortDirection by viewModel.sortDirection.collectAsState()

    KripTakScaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        content = {
            Content(
                isLoading = viewState.isLoading,
                coins = viewState.coins,
                onLoadMore = { viewModel.fetchNextPage() },
                query = searchQuery,
                sortType = sortType,
                sortDirection = sortDirection,
                onQueryChange = { viewModel.updateSearchQuery(it) },
                onSortChange = { viewModel.updateSortType(it) }
            )
        },
    )
}

@Composable
private fun Content(
    coins: List<Coin?>,
    isLoading: Boolean,
    onLoadMore: () -> Unit,
    query: String,
    sortType: SortType,
    sortDirection: SortDirection,
    onQueryChange: (String) -> Unit,
    onSortChange: (SortType) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp, bottom = 80.dp),
    ) {
        LazyColumn {
            item {
                KripTakTopBar(spacerHeight = 0)
            }
            item {
                KripTakSearchField(query = query, onQueryChange = onQueryChange)
            }
            item {
                KripTakSortRow(
                    onSortChange = onSortChange,
                    sortTypeByName = sortType,
                    sortTypeByPrice = sortType,
                    sortTypeByPercentage = sortType,
                    sortDirection = sortDirection,
                )
            }
            itemsIndexed(coins.filter {
                it?.name?.contains(
                    query,
                    ignoreCase = true
                ) == true
            }) { index, coin ->
                val boxShape = when (index) {
                    0 -> BoxShape.TOP
                    coins.size - 1 -> BoxShape.BOTTOM
                    else -> BoxShape.MIDDLE
                }
                if (coin != null) {
                    TrendingCoinsItem(trendCoin = coin, boxShape = boxShape)
                }
                Spacer(modifier = Modifier.height(5.dp))

                if (index == coins.size - 1 && !isLoading) {
                    onLoadMore()
                    KripTakCircularProgressIndicator()
                }
            }
            item {
                if (isLoading) {
                    TrendingCoinsShimmerEffect()
                }
            }
        }
    }
}
