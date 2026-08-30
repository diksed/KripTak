package com.diksed.kriptak.features.screen.crypto

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberScaffoldState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.diksed.kriptak.data.model.Coin
import com.diksed.kriptak.features.component.BoxShape
import com.diksed.kriptak.features.component.CurrencyToggleButton
import com.diksed.kriptak.features.component.KripTakCircularProgressIndicator
import com.diksed.kriptak.features.component.KripTakErrorScreen
import com.diksed.kriptak.features.component.KripTakScaffold
import com.diksed.kriptak.features.component.KripTakSearchField
import com.diksed.kriptak.features.component.KripTakTopBar
import com.diksed.kriptak.features.component.SortDirection
import com.diksed.kriptak.features.component.SortType
import com.diksed.kriptak.features.component.coin_filter.KripTakSortRow
import com.diksed.kriptak.features.component.shimmer.trending_coins.TrendingCoinsShimmerEffect
import com.diksed.kriptak.features.screen.home.components.trending_coins.TrendingCoinsItem
import com.diksed.kriptak.utils.vibrate

@Composable
fun CryptoScreen(
    viewModel: CryptoViewModel = hiltViewModel(),
    navigateToCryptoDetails: (Coin) -> Unit
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
                navigateToCryptoDetails = { navigateToCryptoDetails(it) },
                isLoading = viewState.isLoading,
                isRefreshing = viewState.isRefreshing,
                coins = viewState.coins,
                onLoadMore = { viewModel.fetchNextPage() },
                onRefresh = { viewModel.refresh() },
                query = searchQuery,
                sortType = sortType,
                sortDirection = sortDirection,
                onQueryChange = { viewModel.updateSearchQuery(it) },
                onSortChange = { viewModel.updateSortType(it) },
                isError = viewState.isError
            )
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    navigateToCryptoDetails: (Coin) -> Unit,
    coins: List<Coin?>,
    isLoading: Boolean,
    isRefreshing: Boolean,
    onLoadMore: () -> Unit,
    onRefresh: () -> Unit,
    query: String,
    sortType: SortType,
    sortDirection: SortDirection,
    onSortChange: (SortType) -> Unit,
    onQueryChange: (String) -> Unit,
    isError: Boolean
) {
    val context = LocalContext.current
    var wasRefreshing by remember { mutableStateOf(false) }
    LaunchedEffect(isRefreshing) {
        if (wasRefreshing && !isRefreshing) {
            vibrate(context)
        }
        wasRefreshing = isRefreshing
    }

    if (isError) {
        KripTakErrorScreen()
    } else {
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 10.dp, bottom = 80.dp),
        ) {
            LazyColumn {
                item {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        KripTakTopBar()
                        CurrencyToggleButton(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(top = 8.dp)
                        )
                    }
                }
                item {
                    KripTakSearchField(query = query, onQueryChange = onQueryChange)
                }
                item {
                    KripTakSortRow(
                        onSortChange = onSortChange,
                        sortType = sortType,
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
                        TrendingCoinsItem(
                            navigateToCryptoDetails = { navigateToCryptoDetails.invoke(it) },
                            trendCoin = coin,
                            boxShape = boxShape
                        )
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
}
