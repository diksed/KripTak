package com.diksed.kriptak.feature.appstate

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.diksed.kriptak.core.util.NetworkMonitor
import com.diksed.kriptak.feature.connection.navigation.navigateToConnection
import com.diksed.kriptak.feature.crypto_list.navigation.navigateToCryptoList
import com.diksed.kriptak.feature.favorites.navigation.navigateToFavorites
import com.diksed.kriptak.feature.home.navigation.HomeNavigationRoute
import com.diksed.kriptak.feature.home.navigation.navigateToHome
import com.diksed.kriptak.feature.navigation.TopLevelDestination
import com.diksed.kriptak.feature.news.navigation.navigateToNews
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Composable
fun rememberMainAppState(
    networkMonitor: NetworkMonitor,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): MainAppState {
    return remember(navController, coroutineScope, networkMonitor) {
        MainAppState(navController, coroutineScope, networkMonitor)
    }
}

@Stable
class MainAppState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope,
    networkMonitor: NetworkMonitor,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )

    val shouldShowBottomBar: Boolean
        @Composable get() = currentDestination?.hierarchy?.any { destination ->
            topLevelDestinations.any {
                destination.route?.contains(it.route) ?: false
            }
        } ?: false

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.values().asList()

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            popUpTo(HomeNavigationRoute) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (topLevelDestination) {
            TopLevelDestination.HOME -> navController.navigateToHome(topLevelNavOptions)
            TopLevelDestination.FAVORITES -> navController.navigateToFavorites(topLevelNavOptions)
            TopLevelDestination.CRYPTO_LIST -> navController.navigateToCryptoList(topLevelNavOptions)
            TopLevelDestination.NEWS -> navController.navigateToNews(topLevelNavOptions)
        }
    }

    fun navigateToConnectionScreen() {
        navController.navigateToConnection()
    }

    fun navigateToHomeScreen() {
        navController.navigateToHome()
    }

    fun onBackClick() {
        navController.popBackStack()
    }
}
