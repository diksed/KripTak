package com.diksed.kriptak.feature.favorites.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.diksed.kriptak.feature.favorites.FavoritesScreenRoute

const val FavoritesNavigationRoute = "favorites_route"

fun NavController.navigateToFavorites(navOptions: NavOptions? = null) {
    this.navigate(FavoritesNavigationRoute, navOptions)
}

fun NavGraphBuilder.favoritesScreen(
) {
    composable(route = FavoritesNavigationRoute) {
        FavoritesScreenRoute()
    }
}
