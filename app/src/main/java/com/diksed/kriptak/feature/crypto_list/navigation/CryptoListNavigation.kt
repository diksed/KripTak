package com.diksed.kriptak.feature.crypto_list.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.diksed.kriptak.feature.crypto_list.CryptoListScreenRoute

const val CryptoListNavigationRoute = "crypto_list_route"

fun NavController.navigateToCryptoList(navOptions: NavOptions? = null) {
    this.navigate(CryptoListNavigationRoute, navOptions)
}

fun NavGraphBuilder.cryptoListScreen(
) {
    composable(route = CryptoListNavigationRoute) {
        CryptoListScreenRoute()
    }
}
