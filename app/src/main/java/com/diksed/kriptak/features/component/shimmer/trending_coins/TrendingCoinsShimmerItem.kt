package com.diksed.kriptak.features.component.shimmer.trending_coins

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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

@Composable
fun TrendingCoinsShimmerItem(boxShape: BoxShape) {
    val cornerRadius = getCornerRadius(boxShape)

    Box(
        modifier = Modifier
            .background(boxColor, shape = cornerRadius)
            .padding(8.dp)
            .fillMaxWidth()
            .height(70.dp)
            .shimmer()
    ) {
        Row(Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .size(65.dp)
                    .clip(CircleShape)
            ) {
                Box(
                    modifier = Modifier
                        .background(Color.Gray, shape = RoundedCornerShape(8.dp))
                        .fillMaxSize()
                        .shimmer()
                )
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .align(Alignment.CenterVertically)
                    .weight(1.1f)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .background(Color.Gray)
                        .shimmer()
                )
                Spacer(modifier = Modifier.height(5.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .background(Color.Gray)
                        .shimmer()
                )
            }
            Box(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth(0.2f)
                    .height(40.dp)
                    .background(Color.Gray)
                    .weight(1.4f)
                    .shimmer()
            )
            Box(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 8.dp)
                    .fillMaxWidth(0.2f)
                    .height(15.dp)
                    .background(Color.Gray)
                    .weight(0.8f)
                    .shimmer()
            )
            Box(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 8.dp)
                    .fillMaxWidth(0.2f)
                    .height(15.dp)
                    .background(Color.Gray)
                    .weight(1.1f)
                    .shimmer()
            )
        }
    }
}
