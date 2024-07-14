package com.diksed.kriptak.features.screen.news

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberScaffoldState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.diksed.kriptak.R
import com.diksed.kriptak.data.model.Article
import com.diksed.kriptak.features.component.KripTakScaffold
import com.diksed.kriptak.features.component.KripTakTopBar
import com.diksed.kriptak.features.component.shimmer.current_news.CurrentNewsShimmerEffect
import com.diksed.kriptak.features.screen.home.components.current_news.CurrentNewsBox
import com.diksed.kriptak.features.screen.news.components.ChangeDailyNewsButton
import com.diksed.kriptak.features.ui.theme.boxColor
import com.diksed.kriptak.features.ui.theme.selectedButtonColor

@Composable
fun NewsScreen(
    viewModel: NewsViewModel = hiltViewModel(),
) {
    val scaffoldState = rememberScaffoldState()
    val viewState by viewModel.uiState.collectAsState()

    KripTakScaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        content = {
            Content(
                turkishNews = viewState.turkishNews,
                englishNews = viewState.englishNews,
                isLoading = viewState.isLoading
            )
        },
    )
}

@Composable
private fun Content(
    turkishNews: List<Article>,
    englishNews: List<Article>,
    isLoading: Boolean
) {
    var selectedLanguage by remember { mutableStateOf("TR") }
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
                Row(
                    modifier = Modifier
                        .padding(vertical = 10.dp, horizontal = 20.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ChangeDailyNewsButton(
                        text = stringResource(id = R.string.turkishNews),
                        isSelected = selectedLanguage == "TR",
                        onClick = { selectedLanguage = "TR" },
                        selectedColor = selectedButtonColor,
                        unselectedColor = boxColor
                    )
                    ChangeDailyNewsButton(
                        text = stringResource(id = R.string.englishNews),
                        isSelected = selectedLanguage == "EN",
                        onClick = { selectedLanguage = "EN" },
                        selectedColor = selectedButtonColor,
                        unselectedColor = boxColor
                    )
                }
            }
            item {
                if (isLoading) {
                    CurrentNewsShimmerEffect(isDailyNews = false, newsCount = 15)
                } else {
                    CurrentNewsBox(
                        currentNews = if (selectedLanguage == "TR") turkishNews else englishNews,
                        isDailyNews = false
                    )
                }
            }
        }
    }
}
