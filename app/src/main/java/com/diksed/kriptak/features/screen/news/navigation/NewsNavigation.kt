package com.diksed.kriptak.features.screen.news.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.diksed.kriptak.features.screen.news.NewsScreen

const val newsNavigationRoute = "news_route"

fun NavController.navigateToNews(
    navOptions: NavOptions? = null
) {
    this.navigate(newsNavigationRoute, navOptions)
}

fun NavGraphBuilder.newsScreen() {
    composable(
        newsNavigationRoute,
        content = {
            NewsScreen(
                viewModel = hiltViewModel(),
            )
        },
    )
}
