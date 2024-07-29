package com.diksed.kriptak.features.screen.crypto_details

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberScaffoldState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.diksed.kriptak.R
import com.diksed.kriptak.data.model.Article
import com.diksed.kriptak.data.model.Coin
import com.diksed.kriptak.features.component.KripTakFavoriteButton
import com.diksed.kriptak.features.component.KripTakScaffold
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
                }
            )
        },
    )
}

@Composable
fun Content(
    selectedCoin: Coin?,
    cryptoNews: List<Article>,
    isCryptoLoading: Boolean,
    isFavorite: Boolean,
    navigateToBack: () -> Unit,
    fetchNews: (String) -> Unit,
    toggleFavorite: (String) -> Unit
) {
    val imageUrl = COIN_IMAGE_URL + (selectedCoin?.id ?: "") + ".png"
    val percentChange24h = selectedCoin?.quote?.usd?.percentChange24h
    val price = selectedCoin?.quote?.usd?.price
    val formattedPrice = "$${price?.let { formatPrice(it) }}"

    var selectedTab by remember { mutableStateOf("details") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp, top = 10.dp),
    ) {
        LazyColumn {
            item {
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

                    Text(
                        text = "${selectedCoin?.name}",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.White,
                        fontSize = 25.sp
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    KripTakFavoriteButton(isFavorite = isFavorite) {
                        selectedCoin?.symbol?.let { toggleFavorite(it) }
                    }
                }
            }
            item {
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
            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .background(Color.Gray)
                ) {
                    Text(
                        text = "Chart Placeholder",
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.White
                    )
                }
            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .background(bottomAppBarColor, shape = RoundedCornerShape(10.dp))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        TabButton(
                            modifier = Modifier.weight(1f),
                            "Details",
                            selectedTab == "details"
                        ) { selectedTab = "details" }
                        TabButton(
                            modifier = Modifier.weight(1f),
                            "Market",
                            selectedTab == "market"
                        ) { selectedTab = "market" }
                        TabButton(
                            modifier = Modifier.weight(1f),
                            "News",
                            selectedTab == "news"
                        ) {
                            selectedTab = "news"
                            selectedCoin?.name?.let {
                                fetchNews(it)
                            }
                        }
                    }
                }
            }
            item {
                when (selectedTab) {
                    "details" -> CoinDetailsContent(selectedCoin = selectedCoin!!)
                    "market" -> CoinDetailsMarketContent(selectedCoin = selectedCoin!!)
                    "news" -> CoinDetailsNewsContent(
                        cryptoNews = cryptoNews,
                        isCryptoLoading = isCryptoLoading
                    )
                }
            }
        }
    }
}
