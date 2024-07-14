package com.diksed.kriptak.features.screen.home.components.current_news

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.diksed.kriptak.R
import com.diksed.kriptak.data.model.Article
import com.diksed.kriptak.features.component.BoxShape
import com.diksed.kriptak.features.component.KripTakCurrentBoxTitle
import com.diksed.kriptak.features.component.KripTakCurrentBoxTextButton

@Composable
fun CurrentNewsBox(
    currentNews: List<Article>,
    isDailyNews: Boolean = false,
    navigateToNews: () -> Unit = {},
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (isDailyNews) KripTakCurrentBoxTitle(title = stringResource(id = R.string.currentNews))
        currentNews.forEachIndexed { index, article ->
            val boxShape = when (index) {
                0 -> BoxShape.TOP
                currentNews.size - 1 -> BoxShape.BOTTOM
                else -> BoxShape.MIDDLE
            }
            CurrentNewsItem(currentNews = article, boxShape = boxShape)
            Spacer(modifier = Modifier.height(5.dp))
        }
        if (isDailyNews) {
            KripTakCurrentBoxTextButton(
                text = stringResource(id = R.string.allNews),
                navigateTo = navigateToNews
            )
        }
    }
}
