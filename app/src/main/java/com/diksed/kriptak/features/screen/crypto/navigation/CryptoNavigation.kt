package com.diksed.kriptak.features.screen.crypto.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.diksed.kriptak.data.model.Coin
import com.diksed.kriptak.features.screen.crypto.CryptoScreen

const val cryptoNavigationRoute = "crypto_route"

fun NavController.navigateToCrypto(
    navOptions: NavOptions? = null
) {
    this.navigate(cryptoNavigationRoute, navOptions)
}

fun NavGraphBuilder.cryptoScreen(navigateToDetail: (Coin) -> Unit) {
    composable(
        cryptoNavigationRoute,
        content = {
            CryptoScreen(
                viewModel = hiltViewModel(),
                navigateToCryptoDetails = {
                    navigateToDetail.invoke(it)
                }
            )
        },
    )
}
