package com.diksed.kriptak.feature.crypto_list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.diksed.kriptak.ui.components.MainAppScaffold

@Composable
internal fun CryptoListScreenRoute(
    modifier: Modifier = Modifier,
    viewModel: CryptoListViewModel = hiltViewModel(),
) {
    val cryptoListUiState by viewModel.uiState.collectAsStateWithLifecycle()

    CryptoListScreen(
        modifier = modifier,
    )
}

@Composable
fun CryptoListScreen(
    modifier: Modifier = Modifier,
) {
    MainAppScaffold(
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = "CryptoListScreen", style = MaterialTheme.typography.titleMedium)
    }
}