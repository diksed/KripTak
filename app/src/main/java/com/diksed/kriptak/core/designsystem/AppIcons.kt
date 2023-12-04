package com.diksed.kriptak.core.designsystem

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.vector.ImageVector

object AppIcons {
    val Home = Icons.Default.Home
    val HomeOutlined = Icons.Outlined.Home
}

@Stable
sealed class Icon {
    data class ImageVectorIcon(val imageVector: ImageVector) : Icon()
    data class DrawableResourceIcon(@DrawableRes val id: Int) : Icon()
}