package com.diksed.kriptak.feature.navigation

import com.diksed.kriptak.R
import com.diksed.kriptak.core.designsystem.AppIcons
import com.diksed.kriptak.core.designsystem.Icon
import com.diksed.kriptak.feature.home.navigation.HomeNavigationRoute

enum class TopLevelDestination(
    val route: String,
    val selectedIcon: Icon,
    val unselectedIcon: Icon,
    val titleTextId: Int,
) {
    HOME(
        route = HomeNavigationRoute,
        selectedIcon = Icon.DrawableResourceIcon(R.drawable.selected_home),
        unselectedIcon = Icon.ImageVectorIcon(AppIcons.HomeOutlined),
        titleTextId = R.string.nav_home_title,
    ),

}