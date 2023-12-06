package com.diksed.kriptak.feature.connection.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.diksed.kriptak.feature.connection.ConnectionScreenRoute

const val ConnectionNavigationRoute = "connection_route"

fun NavController.navigateToConnection(navOptions: NavOptions? = null) {
    this.navigate(ConnectionNavigationRoute, navOptions)
}

fun NavGraphBuilder.connectionScreen(
) {
    composable(route = ConnectionNavigationRoute) {
        ConnectionScreenRoute()
    }
}