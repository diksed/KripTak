package com.diksed.kriptak.features.screen.home.components.trending_coins

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter


@Composable
fun CoinImage(imageUrl: String, modifier: Modifier) {
    Image(
        painter = rememberAsyncImagePainter(imageUrl),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(65.dp)
            .clip(CircleShape)
    )
}
