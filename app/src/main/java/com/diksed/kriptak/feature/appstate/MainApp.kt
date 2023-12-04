package com.diksed.kriptak.feature.appstate

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.diksed.kriptak.R
import com.diksed.kriptak.core.designsystem.Icon
import com.diksed.kriptak.core.util.NetworkMonitor
import com.diksed.kriptak.feature.navigation.MainNavHost
import com.diksed.kriptak.feature.navigation.TopLevelDestination
import com.diksed.kriptak.ui.components.MainAppScaffold
import com.diksed.kriptak.ui.theme.bgColor

@OptIn(
    ExperimentalComposeUiApi::class,
)
@Composable
fun MainApp(
    networkMonitor: NetworkMonitor,
    modifier: Modifier = Modifier,
    appState: MainAppState = rememberMainAppState(
        networkMonitor = networkMonitor,
    ),
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val isOffline by appState.isOffline.collectAsStateWithLifecycle()

    val notConnectedMessage = stringResource(R.string.not_network_connection)
    LaunchedEffect(isOffline) {
        if (isOffline) {
            snackbarHostState.showSnackbar(
                message = notConnectedMessage,
                duration = SnackbarDuration.Indefinite,
            )
        }
    }

    MainAppScaffold(
        modifier = modifier.semantics {
            testTagsAsResourceId = true
        },
        bottomBar = {
            AnimatedVisibility(
                visible = appState.shouldShowBottomBar,
            ) {
                AppNavBar(
                    destinations = AppDestinations(appState.topLevelDestinations),
                    onNavigateToDestination = appState::navigateToTopLevelDestination,
                    currentDestination = appState.currentDestination,
                )
            }
        },
        backgroundColor = bgColor,
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) {
        MainNavHost(
            navController = appState.navController,
        )
    }
}

@Composable
internal fun AppIcon(
    icon: Icon,
) {
    when (icon) {
        is Icon.ImageVectorIcon -> Icon(
            imageVector = icon.imageVector,
            contentDescription = null,
        )

        is Icon.DrawableResourceIcon -> Icon(
            painter = painterResource(id = icon.id),
            contentDescription = null,
        )
    }
}

@Stable
data class AppDestinations(
    val destinations: List<TopLevelDestination>,
) : List<TopLevelDestination> by destinations

@Composable
internal fun AppNavBar(
    destinations: AppDestinations,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
) {
    NavigationBar {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                label = {
                    Text(text = stringResource(id = destination.titleTextId))
                },
                icon = {
                    val icon = if (selected) {
                        destination.selectedIcon
                    } else {
                        destination.unselectedIcon
                    }
                    AppIcon(icon = icon)
                },
            )
        }
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.route, true) ?: false
    } ?: false
