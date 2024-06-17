package com.diksed.kriptak.features.screen.news.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.diksed.kriptak.features.screen.news.NewsScreen
import com.google.accompanist.navigation.animation.composable

const val newsNavigationRoute = "news_route"

fun NavController.navigateToNews(
    navOptions: NavOptions? = null
) {
    this.navigate(newsNavigationRoute, navOptions)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.newsScreen() {
    composable(
        newsNavigationRoute,
        content = {
            NewsScreen(
                viewModel = hiltViewModel(),
            )
        },
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(200)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(200)
            )
        }
    )
}
