package com.diksed.kriptak.features.screen.crypto_details

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberScaffoldState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.diksed.kriptak.R
import com.diksed.kriptak.data.model.Article
import com.diksed.kriptak.data.model.Coin
import com.diksed.kriptak.features.component.KripTakErrorScreen
import com.diksed.kriptak.features.component.KripTakFavoriteButton
import com.diksed.kriptak.features.component.KripTakScaffold
import com.diksed.kriptak.features.component.KripTakText
import com.diksed.kriptak.features.screen.crypto.components.CryptoNameSymbolColumn
import com.diksed.kriptak.features.screen.crypto.components.CryptoPricePercentChangeColumn
import com.diksed.kriptak.features.screen.crypto_details.components.CoinDetailsContent
import com.diksed.kriptak.features.screen.crypto_details.components.CoinDetailsMarketContent
import com.diksed.kriptak.features.screen.crypto_details.components.CoinDetailsNewsContent
import com.diksed.kriptak.features.screen.crypto_details.components.TabButton
import com.diksed.kriptak.features.screen.home.components.trending_coins.CoinImage
import com.diksed.kriptak.features.ui.theme.bottomAppBarColor
import com.diksed.kriptak.utils.COIN_IMAGE_URL
import com.diksed.kriptak.utils.formatters.formatPrice
import com.diksed.kriptak.utils.springPagerFlingBehavior
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@Composable
fun CryptoDetailsScreen(
    viewModel: CryptoDetailsViewModel = hiltViewModel(),
    navigateToBack: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val viewState by viewModel.uiState.collectAsState()

    LaunchedEffect(viewState.selectedCoin) {
        viewState.selectedCoin?.symbol?.let { viewModel.checkFavoriteStatus(it) }
    }

    KripTakScaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        content = {
            Content(
                viewState.selectedCoin,
                viewState.cryptoNews,
                viewState.isCryptoLoading,
                viewState.isFavorite,
                navigateToBack = navigateToBack,
                fetchNews = { coinId -> viewModel.fetchNews(coinId) },
                toggleFavorite = { coinId ->
                    viewModel.toggleFavorite(coinId)
                },
                isError = viewState.isError
            )
        },
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Content(
    selectedCoin: Coin?,
    cryptoNews: List<Article>,
    isCryptoLoading: Boolean,
    isFavorite: Boolean,
    navigateToBack: () -> Unit,
    fetchNews: (String) -> Unit,
    toggleFavorite: (String) -> Unit,
    isError: Boolean = false
) {
    val imageUrl = COIN_IMAGE_URL + (selectedCoin?.id ?: "") + ".png"
    val percentChange24h = selectedCoin?.quote?.usd?.percentChange24h
    val price = selectedCoin?.quote?.usd?.price
    val formattedPrice = "$${price?.let { formatPrice(it) }}"

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage == 2) {
            selectedCoin?.name?.let {
                fetchNews(it)
            }
        }
    }

    if (isError) {
        KripTakErrorScreen()
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 10.dp, top = 10.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { navigateToBack() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back_arrow),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                KripTakText(
                    text = "${selectedCoin?.name}",
                    maxLines = 1,
                    fontSize = 25.sp
                )

                Spacer(modifier = Modifier.weight(1f))

                KripTakFavoriteButton(
                    isFavorite = isFavorite,
                    coinName = selectedCoin?.name ?: "",
                ) {
                    selectedCoin?.symbol?.let { toggleFavorite(it) }
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, end = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    CoinImage(
                        imageUrl = imageUrl, modifier = Modifier.scale(0.7f)
                    )
                    CryptoNameSymbolColumn(
                        cryptoName = "${selectedCoin?.name}",
                        cryptoNameFontSize = 20.sp,
                        cryptoSymbol = "${selectedCoin?.symbol}",
                        cryptoSymbolFontSize = 15.sp,
                        modifier = Modifier.weight(1f)
                    )
                }
                CryptoPricePercentChangeColumn(
                    formattedPrice = formattedPrice,
                    percentChange24h = percentChange24h,
                    modifier = Modifier.weight(1f)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .background(bottomAppBarColor, shape = RoundedCornerShape(10.dp))
            ) {
                TabRow(
                    selectedTabIndex = pagerState.currentPage,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp),
                    containerColor = Color.Transparent,
                    divider = { },
                    indicator = { },
                    tabs = {
                        TabButton(
                            text = stringResource(id = R.string.details),
                            isSelected = pagerState.currentPage == 0,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(0)
                                }
                            },
                        )
                        TabButton(
                            text = stringResource(id = R.string.market),
                            isSelected = pagerState.currentPage == 1,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(1)
                                }
                            }
                        )
                        TabButton(
                            text = stringResource(id = R.string.news),
                            isSelected = pagerState.currentPage == 2,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(2)
                                }
                                selectedCoin?.name?.let {
                                    fetchNews(it)
                                }
                            }
                        )
                    }
                )
            }

            HorizontalPager(
                count = 3,
                state = pagerState,
                flingBehavior = springPagerFlingBehavior(pagerState = pagerState),
                modifier = Modifier.fillMaxSize()
            ) { page ->
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    when (page) {
                        0 -> CoinDetailsContent(selectedCoin = selectedCoin!!)
                        1 -> CoinDetailsMarketContent(selectedCoin = selectedCoin!!)
                        2 -> CoinDetailsNewsContent(
                            cryptoNews = cryptoNews,
                            isCryptoLoading = isCryptoLoading
                        )
                    }
                }
            }
        }
    }
}
