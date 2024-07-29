package com.diksed.kriptak.features.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.diksed.kriptak.R
import com.diksed.kriptak.features.ui.theme.White

@Composable
fun KripTakFavoriteButton(
    isFavorite: Boolean,
    onClick: () -> Unit
) {
    val iconId = if (isFavorite) R.drawable.ic_favorited else R.drawable.ic_unfavorited

    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = if (isFavorite) Yellow else White,
            modifier = Modifier.size(30.dp)
        )
    }
}
