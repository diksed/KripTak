package com.diksed.kriptak.features.screen.crypto.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.diksed.kriptak.features.screen.crypto.CryptoScreen
import com.google.accompanist.navigation.animation.composable

const val cryptoNavigationRoute = "crypto_route"

fun NavController.navigateToCrypto(
    navOptions: NavOptions? = null
) {
    this.navigate(cryptoNavigationRoute, navOptions)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.cryptoScreen() {
    composable(
        cryptoNavigationRoute,
        content = {
            CryptoScreen(
                viewModel = hiltViewModel()
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
