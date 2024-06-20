package com.diksed.kriptak.features.screen.news

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberScaffoldState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.diksed.kriptak.R
import com.diksed.kriptak.features.component.KripTakScaffold
import com.diksed.kriptak.features.component.KripTakTopBar
import com.diksed.kriptak.features.screen.crypto.CryptoViewModel

@Composable
fun NewsScreen(
    viewModel: CryptoViewModel = hiltViewModel(),
) {
    val scaffoldState = rememberScaffoldState()
    val viewState by viewModel.uiState.collectAsState()

    KripTakScaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        content = {
            Content(
            )
        },
    )

}

@Composable
private fun Content(
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp),
    ) {
        Column {
            KripTakTopBar()
            Text(
                text = stringResource(id = R.string.news),
                color = androidx.compose.material.MaterialTheme.colors.onPrimary,
            )
        }
    }
}
