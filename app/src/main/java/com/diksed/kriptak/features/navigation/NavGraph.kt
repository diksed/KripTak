package com.diksed.kriptak.features.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.diksed.kriptak.features.component.KripTakBottomAppBar
import com.diksed.kriptak.features.component.KripTakScaffold
import com.diksed.kriptak.features.component.navigateToBottomNavDestination
import com.diksed.kriptak.features.screen.crypto.navigation.cryptoScreen
import com.diksed.kriptak.features.screen.crypto_details.navigation.cryptoDetailsScreen
import com.diksed.kriptak.features.screen.crypto_details.navigation.navigateToCryptoDetails
import com.diksed.kriptak.features.screen.favorites.navigation.favoritesScreen
import com.diksed.kriptak.features.screen.home.navigation.homeNavigationRoute
import com.diksed.kriptak.features.screen.home.navigation.homeScreen
import com.diksed.kriptak.features.screen.news.navigation.newsScreen
import com.diksed.kriptak.features.ui.theme.scaffoldBackgroundColor
import com.diksed.kriptak.utils.Utility.toJson


@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val currentDestination = navController
        .currentBackStackEntryAsState().value?.destination

    KripTakScaffold(
        bottomBar = {
            BottomNav.entries.forEach { navItem ->
                if (navItem.route == currentRoute) {
                    KripTakBottomAppBar(
                        navController = navController,
                        currentDestination = currentDestination
                    )
                }
            }
        },
        backgroundColor = MaterialTheme.colorScheme.background,
    ) {
        NavHost(
            navController = navController,
            modifier = Modifier.background(scaffoldBackgroundColor),
            startDestination = homeNavigationRoute,
            popExitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
        ) {
            homeScreen(navigateToNews = {
                navigateToBottomNavDestination(
                    BottomNav.NEWS,
                    navController,
                )
            }, navigateToCrypto = {
                navigateToBottomNavDestination(
                    BottomNav.CRYPTO,
                    navController,
                )
            })
            favoritesScreen()
            cryptoScreen { navController.navigateToCryptoDetails(it.toJson()) }
            cryptoDetailsScreen { navController.popBackStack() }
            newsScreen()
        }
    }
}