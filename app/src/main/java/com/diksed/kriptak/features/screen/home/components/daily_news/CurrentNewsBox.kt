package com.diksed.kriptak.features.screen.home.components.daily_news

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.diksed.kriptak.R
import com.diksed.kriptak.data.model.Article
import com.diksed.kriptak.features.component.BoxShape
import com.diksed.kriptak.features.component.KripTakCurrentBoxTitle

@SuppressLint("NewApi")
@Composable
fun CurrentNewsBox(currentNews: List<Article>) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        KripTakCurrentBoxTitle(title = stringResource(id = R.string.currentNews))
        LazyColumn {
            itemsIndexed(currentNews) { index, article ->
                val boxShape = when (index) {
                    0 -> BoxShape.TOP
                    currentNews.size - 1 -> BoxShape.BOTTOM
                    else -> BoxShape.MIDDLE
                }
                CurrentNewsItem(currentNews = article, boxShape = boxShape)
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
    }
}