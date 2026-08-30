package com.diksed.kriptak.features.component.shimmer.trending_coins

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.diksed.kriptak.features.component.BoxShape
import com.diksed.kriptak.features.ui.theme.boxColor
import com.diksed.kriptak.utils.components.getCornerRadius
import com.valentinilk.shimmer.shimmer

/**
 * Mirrors TrendingCoinsItem's layout (same fixed sizes/spacers/weights) so the
 * loading state doesn't visibly reflow once real data comes in.
 */
@Composable
fun TrendingCoinsShimmerItem(boxShape: BoxShape) {
    val cornerRadius = getCornerRadius(boxShape)

    Box(
        modifier = Modifier
            .background(boxColor, shape = cornerRadius)
            .padding(horizontal = 4.dp, vertical = 8.dp)
            .fillMaxWidth()
            .height(65.dp)
    ) {
        Row(Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(65.dp)
                    .clip(CircleShape)
                    .background(Color.Gray, shape = CircleShape)
                    .shimmer()
            )
            Spacer(modifier = Modifier.width(4.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(14.dp)
                        .background(Color.Gray, shape = RoundedCornerShape(4.dp))
                        .shimmer()
                )
                Spacer(modifier = Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .height(10.dp)
                        .background(Color.Gray, shape = RoundedCornerShape(4.dp))
                        .shimmer()
                )
            }
            Spacer(modifier = Modifier.width(5.dp))
            Box(
                modifier = Modifier
                    .weight(0.9f)
                    .height(30.dp)
                    .background(Color.Gray, shape = RoundedCornerShape(4.dp))
                    .shimmer()
            )
            Spacer(modifier = Modifier.width(2.dp))
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.weight(2f)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(13.dp)
                        .background(Color.Gray, shape = RoundedCornerShape(4.dp))
                        .shimmer()
                )
                Spacer(modifier = Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(13.dp)
                        .background(Color.Gray, shape = RoundedCornerShape(4.dp))
                        .shimmer()
                )
            }
        }
    }
}
