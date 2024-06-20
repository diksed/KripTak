package com.diksed.kriptak.features.component

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.*
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.navOptions
import androidx.tracing.trace
import com.diksed.kriptak.features.navigation.BottomNav
import com.diksed.kriptak.features.screen.crypto.navigation.navigateToCrypto
import com.diksed.kriptak.features.screen.favorites.navigation.navigateToFavorites
import com.diksed.kriptak.features.screen.home.navigation.navigateToHome
import com.diksed.kriptak.features.screen.news.navigation.navigateToNews
import com.diksed.kriptak.features.ui.theme.bottomAppBarColor
import com.diksed.kriptak.features.ui.theme.bottomAppBarItemColor


@Composable
fun KripTakBottomAppBar(
    navController: NavController,
    currentDestination: NavDestination?,
) {
    BottomAppBar(
        modifier = Modifier
            .clip(
                RoundedCornerShape(
                    topStart = 20.dp,
                    topEnd = 20.dp,
                )
            ),
        tonalElevation = 30.dp,
        contentPadding = PaddingValues(0.dp),
        contentColor = bottomAppBarItemColor,
        containerColor = bottomAppBarColor,

        ) {
        BottomNav.entries.forEach { screen ->
            val selected = currentDestination.isBottomNavDestinationInHierarchy(screen)
            val primaryColor = MaterialTheme.colorScheme.primary

            BottomNavigationItem(
                alwaysShowLabel = true,
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = screen.iconId),
                        contentDescription = null,
                        modifier = Modifier.size(35.dp),
                        tint = if (selected) primaryColor else bottomAppBarItemColor
                    )
                },
                label = {
                    KripTakText(
                        text = stringResource(id = screen.titleTextId),
                        fontSize = 8.sp,
                        textAlign = TextAlign.Center,
                        color = if (selected) primaryColor else bottomAppBarItemColor,
                    )
                },
                selected = selected,
                onClick = {
                    navigateToBottomNavDestination(screen, navController)
                }
            )
        }
    }
}

fun navigateToBottomNavDestination(bottomNav: BottomNav, navController: NavController) {
    trace("Navigation: ${bottomNav.name}") {
        val bottomNavOptions = navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }

        when (bottomNav) {
            BottomNav.HOME -> navController.navigateToHome(bottomNavOptions)
            BottomNav.FAVORITES -> navController.navigateToFavorites(bottomNavOptions)
            BottomNav.CRYPTO -> navController.navigateToCrypto(bottomNavOptions)
            BottomNav.NEWS -> navController.navigateToNews(bottomNavOptions)
        }
    }
}

private fun NavDestination?.isBottomNavDestinationInHierarchy(destination: BottomNav) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false
