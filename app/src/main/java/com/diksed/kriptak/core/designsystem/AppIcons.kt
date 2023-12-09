package com.diksed.kriptak.core.designsystem

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Newspaper
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.vector.ImageVector

object AppIcons {
    val Home = Icons.Default.Home
    val HomeOutlined = Icons.Outlined.Home
    val Favorites = Icons.Default.Star
    val FavoritesOutlined = Icons.Outlined.Star
    val CryptoList = Icons.Default.List
    val CryptoListOutlined = Icons.Outlined.List
    val News = Icons.Default.Newspaper
    val NewsOutlined = Icons.Outlined.Newspaper
}

@Stable
sealed class Icon {
    data class ImageVectorIcon(val imageVector: ImageVector) : Icon()
    data class DrawableResourceIcon(@DrawableRes val id: Int) : Icon()
}