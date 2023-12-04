package com.diksed.kriptak.feature.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.diksed.kriptak.ui.components.MainAppScaffold

@Composable
internal fun HomeScreenRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val homeUiState by viewModel.uiState.collectAsStateWithLifecycle()


    HomeScreen(
        homeUiState = homeUiState,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeUiState: HomeUiState,
    modifier: Modifier = Modifier,
) {
    MainAppScaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = {
                    Text(text = homeUiState.title)
                },
            )
        },
    ) {
    }
}
