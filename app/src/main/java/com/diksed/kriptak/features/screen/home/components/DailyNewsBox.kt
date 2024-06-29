package com.diksed.kriptak.features.screen.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.diksed.kriptak.data.model.Article
import com.diksed.kriptak.features.ui.theme.boxColor
import com.diksed.kriptak.utils.formatDateTime

@Composable
fun DailyNewsBox(dailyNews: Article) {
    Box(
        modifier = Modifier
            .background(boxColor, shape = RoundedCornerShape(8.dp))
            .padding(8.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(Modifier.fillMaxSize()) {
            Image(
                painter = rememberAsyncImagePainter(dailyNews.urlToImage),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .align(Alignment.CenterVertically)
                    .weight(1f)
            ) {
                Text(
                    text = dailyNews.title,
                    color = Color.White,
                    maxLines = 2,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
            println(dailyNews.publishedAt)
            Text(
                text = formatDateTime(dailyNews.publishedAt),
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.Bottom)
                    .padding(end = 8.dp, bottom = 8.dp)
            )
        }
    }
}
