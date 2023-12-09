package com.diksed.kriptak.feature.news

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
internal fun NewsScreenRoute(
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = hiltViewModel(),
) {
    val newsUiState by viewModel.uiState.collectAsStateWithLifecycle()

    NewsScreen(
        modifier = modifier,
    )
}

@Composable
fun NewsScreen(
    modifier: Modifier = Modifier,
) {
    MainAppScaffold(
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = "NewsScreen", style = MaterialTheme.typography.titleMedium)
    }
}
