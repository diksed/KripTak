package com.diksed.kriptak.features.screen.crypto

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberScaffoldState
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.diksed.kriptak.R
import com.diksed.kriptak.data.model.Coin
import com.diksed.kriptak.features.component.KripTakCircularProgressIndicator
import com.diksed.kriptak.features.component.KripTakErrorScreen
import com.diksed.kriptak.features.component.KripTakScaffold
import com.diksed.kriptak.features.component.KripTakSearchField
import com.diksed.kriptak.features.component.KripTakText
import com.diksed.kriptak.features.component.KripTakTopBar
import com.diksed.kriptak.features.component.SortDirection
import com.diksed.kriptak.features.component.SortType
import com.diksed.kriptak.features.component.coin_filter.KripTakSortRow
import com.diksed.kriptak.features.screen.crypto.components.CryptoListItem
import com.diksed.kriptak.features.screen.crypto.components.CryptoListItemShimmer
import com.diksed.kriptak.features.screen.crypto.components.MarketOverviewCard
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
                totalMarketCap = viewState.totalMarketCap,
                activeCryptocurrencies = viewState.activeCryptocurrencies,
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
    totalMarketCap: Double?,
    activeCryptocurrencies: Int?,
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
                    KripTakTopBar()
                }
                item {
                    MarketOverviewCard(
                        totalMarketCap = totalMarketCap,
                        activeCryptocurrencies = activeCryptocurrencies,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
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
                item {
                    KripTakText(
                        text = stringResource(id = R.string.allCrypto),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                if (isRefreshing) {
                    items(6) {
                        CryptoListItemShimmer()
                        Spacer(modifier = Modifier.height(6.dp))
                    }
                } else {
                    val filteredCoins = coins.filter {
                        it?.name?.contains(query, ignoreCase = true) == true
                    }
                    itemsIndexed(filteredCoins) { index, coin ->
                        if (coin != null) {
                            CryptoListItem(
                                trendCoin = coin,
                                navigateToCryptoDetails = { navigateToCryptoDetails.invoke(it) }
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))

                        if (index == filteredCoins.size - 1 && !isLoading) {
                            onLoadMore()
                            KripTakCircularProgressIndicator()
                        }
                    }
                    if (isLoading) {
                        item {
                            CryptoListItemShimmer()
                        }
                    }
                }
            }
        }
    }
}
