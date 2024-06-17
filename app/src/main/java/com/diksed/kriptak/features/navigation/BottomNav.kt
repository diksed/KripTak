package com.diksed.kriptak.features.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.diksed.kriptak.R
import com.diksed.kriptak.features.screen.crypto.navigation.cryptoNavigationRoute
import com.diksed.kriptak.features.screen.favorites.navigation.favoritesNavigationRoute
import com.diksed.kriptak.features.screen.home.navigation.homeNavigationRoute
import com.diksed.kriptak.features.screen.news.navigation.newsNavigationRoute

enum class BottomNav(
    val route: String,
    @DrawableRes val iconId: Int,
    @StringRes val titleTextId: Int
) {
    HOME(
        homeNavigationRoute,
        R.drawable.ic_home,
        R.string.homePage,
    ),
    FAVORITES(
        favoritesNavigationRoute,
        R.drawable.ic_favorites,
        R.string.favorites,
    ),
    CRYPTO(
        cryptoNavigationRoute,
        R.drawable.ic_crypto_list,
        R.string.cryptoList,
    ),
    NEWS(
        newsNavigationRoute,
        R.drawable.ic_news,
        R.string.news,
    ),
}
