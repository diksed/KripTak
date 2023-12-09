package com.diksed.kriptak.feature.favorites

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
internal fun FavoritesScreenRoute(
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel = hiltViewModel(),
) {
    val favoritesUiState by viewModel.uiState.collectAsStateWithLifecycle()

    FavoritesScreen(
        modifier = modifier,
    )
}

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
) {
    MainAppScaffold(
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = "FavoritesScreen", style = MaterialTheme.typography.titleMedium)
    }
}
