package com.diksed.kriptak.feature.navigation

import com.diksed.kriptak.R
import com.diksed.kriptak.core.designsystem.AppIcons
import com.diksed.kriptak.core.designsystem.Icon
import com.diksed.kriptak.feature.crypto_list.navigation.CryptoListNavigationRoute
import com.diksed.kriptak.feature.favorites.navigation.FavoritesNavigationRoute
import com.diksed.kriptak.feature.home.navigation.HomeNavigationRoute
import com.diksed.kriptak.feature.news.navigation.NewsNavigationRoute

enum class TopLevelDestination(
    val route: String,
    val selectedIcon: Icon,
    val unselectedIcon: Icon,
    val titleTextId: Int,
) {
    HOME(
        route = HomeNavigationRoute,
        selectedIcon = Icon.ImageVectorIcon(AppIcons.Home),
        unselectedIcon = Icon.ImageVectorIcon(AppIcons.HomeOutlined),
        titleTextId = R.string.nav_home_title,
    ),
    FAVORITES(
        route = FavoritesNavigationRoute,
        selectedIcon = Icon.ImageVectorIcon(AppIcons.Favorites),
        unselectedIcon = Icon.ImageVectorIcon(AppIcons.FavoritesOutlined),
        titleTextId = R.string.nav_favorites_title,
    ),
    CRYPTO_LIST(
        route = CryptoListNavigationRoute,
        selectedIcon = Icon.ImageVectorIcon(AppIcons.CryptoList),
        unselectedIcon = Icon.ImageVectorIcon(AppIcons.CryptoListOutlined),
        titleTextId = R.string.nav_crypto_list_title,
    ),
    NEWS(
        route = NewsNavigationRoute,
        selectedIcon = Icon.ImageVectorIcon(AppIcons.News),
        unselectedIcon = Icon.ImageVectorIcon(AppIcons.NewsOutlined),
        titleTextId = R.string.nav_news_title,
    ),
}
