package com.diksed.kriptak.features.screen.crypto_details.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.diksed.kriptak.features.screen.crypto_details.CryptoDetailsScreen

const val cryptoDetailsNavigationRoute = "crypto_details_route"

fun NavController.navigateToCryptoDetails(cryptoDetail: String, navOptions: NavOptions? = null) {
    this.navigate(cryptoDetailsNavigationRoute.plus("?cryptoDetail=$cryptoDetail"), navOptions)
}

fun NavGraphBuilder.cryptoDetailsScreen() {
    composable(
        cryptoDetailsNavigationRoute.plus("?cryptoDetail={cryptoDetail}"),
        content = {
            CryptoDetailsScreen(
                viewModel = hiltViewModel()
            )
        }
    )
}
