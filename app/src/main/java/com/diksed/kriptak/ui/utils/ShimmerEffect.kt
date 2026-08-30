package com.diksed.kriptak.ui.utils

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.diksed.kriptak.ui.theme.boxColor

@Composable
fun AnimatedShimmer() {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f),
    )

    val transition = rememberInfiniteTransition(label = "")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )

    LazyColumn(content = {
        items(3) {
            ShimmerGridItem(brush = brush)
        }
    }, verticalArrangement = Arrangement.spacedBy(5.dp))
}

@Composable
fun ShimmerGridItem(brush: Brush) {
    Card(
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(containerColor = boxColor, contentColor = boxColor),
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(80.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .background(brush)
            )

            Spacer(
                modifier = Modifier
                    .height(25.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .fillMaxWidth(fraction = 0.7f)
                    .background(brush)
            )
        }
    }
}