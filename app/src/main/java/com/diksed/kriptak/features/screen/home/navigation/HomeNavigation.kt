package com.diksed.kriptak.features.screen.home.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.diksed.kriptak.data.model.Coin
import com.diksed.kriptak.features.screen.home.HomeScreen
import com.google.accompanist.navigation.animation.composable

const val homeNavigationRoute = "home_route"

fun NavController.navigateToHome(
    navOptions: NavOptions? = null
) {
    this.navigate(homeNavigationRoute, navOptions)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.homeScreen(navigateToNews: () -> Unit, navigateToCrypto: () -> Unit, navigateToCryptoDetails: (Coin) -> Unit) {
    composable(
        homeNavigationRoute,
        content = {
            HomeScreen(
                viewModel = hiltViewModel(),
                navigateToNews = navigateToNews,
                navigateToCrypto = navigateToCrypto,
                navigateToCryptoDetails = navigateToCryptoDetails
            )
        },
    )
}
