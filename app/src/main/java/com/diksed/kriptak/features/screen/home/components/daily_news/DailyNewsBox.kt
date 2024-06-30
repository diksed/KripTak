package com.diksed.kriptak.features.screen.home.components.daily_news

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.diksed.kriptak.R
import com.diksed.kriptak.data.model.Article
import com.diksed.kriptak.features.component.BoxShape

@Composable
fun DailyNewsBox(dailyNews: List<Article>) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(id = R.string.currentNews).uppercase(),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp, modifier = Modifier.padding(bottom = 7.dp)
        )
        LazyColumn {
            itemsIndexed(dailyNews) { index, article ->
                val boxShape = when (index) {
                    0 -> BoxShape.TOP
                    dailyNews.size - 1 -> BoxShape.BOTTOM
                    else -> BoxShape.MIDDLE
                }
                DailyNewsItem(dailyNews = article, boxShape = boxShape)
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
    }
}