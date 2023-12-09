package com.diksed.kriptak.feature.news.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.diksed.kriptak.feature.news.NewsScreenRoute

const val NewsNavigationRoute = "news_route"

fun NavController.navigateToNews(navOptions: NavOptions? = null) {
    this.navigate(NewsNavigationRoute, navOptions)
}

fun NavGraphBuilder.newsScreen(
) {
    composable(route = NewsNavigationRoute) {
        NewsScreenRoute()
    }
}
