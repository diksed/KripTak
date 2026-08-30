package com.diksed.kriptak.features.screen.home.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.diksed.kriptak.data.model.Coin
import com.diksed.kriptak.features.screen.home.HomeScreen

const val homeNavigationRoute = "home_route"

fun NavController.navigateToHome(
    navOptions: NavOptions? = null
) {
    this.navigate(homeNavigationRoute, navOptions)
}

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
