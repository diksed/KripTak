package com.diksed.kriptak.features.screen.home.components.trending_coins

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun CoinSparklineImage(sparkLine: String, percentChange24h: Double, modifier: Modifier) {
    Image(
        painter = rememberAsyncImagePainter(sparkLine),
        contentDescription = null,
        contentScale = ContentScale.Fit,
        colorFilter = getPercentChangeColorFilter(percentChange24h),
        modifier = Modifier
            .height(35.dp)
            .width(80.dp)
    )
}
