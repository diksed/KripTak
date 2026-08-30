package com.diksed.kriptak.features.screen.favorites.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.diksed.kriptak.data.model.Coin
import com.diksed.kriptak.features.screen.favorites.FavoritesScreen

const val favoritesNavigationRoute = "favorites_route"

fun NavController.navigateToFavorites(
    navOptions: NavOptions? = null
) {
    this.navigate(favoritesNavigationRoute, navOptions)
}

fun NavGraphBuilder.favoritesScreen(
    navigateToDetail: (Coin) -> Unit,
    navigateToCrypto: () -> Unit
) {
    composable(
        favoritesNavigationRoute,
        content = {
            FavoritesScreen(
                viewModel = hiltViewModel(),
                navigateToCryptoDetails = { navigateToDetail.invoke(it) },
                navigateToCrypto = navigateToCrypto
            )
        },
    )
}
