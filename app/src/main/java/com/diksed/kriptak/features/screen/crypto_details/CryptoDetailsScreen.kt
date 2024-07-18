package com.diksed.kriptak.features.screen.crypto_details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.diksed.kriptak.data.model.Coin
import com.diksed.kriptak.features.component.KripTakScaffold
import com.diksed.kriptak.features.component.KripTakTopBar


@Composable
fun CryptoDetailsScreen(viewModel: CryptoDetailsViewModel = hiltViewModel()) {
    val scaffoldState = rememberScaffoldState()
    val viewState by viewModel.uiState.collectAsState()

    KripTakScaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        content = {
            Content(viewState.selectedCoin)
        },
    )
}

@Composable
fun Content(selectedCoin: Coin?) {
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
                Text(text = "Crypto Details")
            }
            selectedCoin?.let { coin ->
                item {
                    Text(text = "Name: ${coin.name}")
                }
                item {
                    Text(text = "Symbol: ${coin.symbol}")
                }
                item {
                    Text(text = "Price: ${coin.quote.usd.price}")
                }
                // Add more details as needed
            }
        }
    }
}