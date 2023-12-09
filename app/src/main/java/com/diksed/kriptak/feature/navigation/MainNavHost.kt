package com.diksed.kriptak.feature.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.diksed.kriptak.feature.connection.navigation.connectionScreen
import com.diksed.kriptak.feature.crypto_list.navigation.cryptoListScreen
import com.diksed.kriptak.feature.favorites.navigation.favoritesScreen
import com.diksed.kriptak.feature.home.navigation.HomeNavigationRoute
import com.diksed.kriptak.feature.home.navigation.homeScreen
import com.diksed.kriptak.feature.news.navigation.newsScreen

@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = HomeNavigationRoute,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        homeScreen()
        favoritesScreen()
        cryptoListScreen()
        newsScreen()
        connectionScreen()
    }
}
