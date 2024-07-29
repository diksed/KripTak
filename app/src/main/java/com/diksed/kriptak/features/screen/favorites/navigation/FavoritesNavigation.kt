package com.diksed.kriptak.features.screen.favorites.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.diksed.kriptak.data.model.Coin
import com.diksed.kriptak.features.screen.favorites.FavoritesScreen
import com.google.accompanist.navigation.animation.composable

const val favoritesNavigationRoute = "favorites_route"

fun NavController.navigateToFavorites(
    navOptions: NavOptions? = null
) {
    this.navigate(favoritesNavigationRoute, navOptions)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.favoritesScreen(navigateToDetail: (Coin) -> Unit) {
    composable(
        favoritesNavigationRoute,
        content = {
            FavoritesScreen(
                viewModel = hiltViewModel(),
                navigateToCryptoDetails = { navigateToDetail.invoke(it) }
            )
        },
    )
}
