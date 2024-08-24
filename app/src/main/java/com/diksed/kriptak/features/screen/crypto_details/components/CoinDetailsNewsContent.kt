package com.diksed.kriptak.features.screen.crypto_details.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.diksed.kriptak.data.model.Article
import com.diksed.kriptak.features.component.BoxShape
import com.diksed.kriptak.features.component.shimmer.current_news.CurrentNewsShimmerEffect
import com.diksed.kriptak.features.screen.home.components.current_news.CurrentNewsItem

@Composable
fun CoinDetailsNewsContent(cryptoNews: List<Article>, isCryptoLoading: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 12.dp)
    ) {
        when {
            isCryptoLoading -> {
                CurrentNewsShimmerEffect(newsCount = 5)
            }

            cryptoNews.isEmpty() -> {
                EmptyNewsMessage()
            }

            else -> {
                LazyColumn {
                    items(cryptoNews.size) { index ->
                        val article = cryptoNews[index]
                        val boxShape = when (index) {
                            0 -> BoxShape.TOP
                            cryptoNews.size - 1 -> BoxShape.BOTTOM
                            else -> BoxShape.MIDDLE
                        }
                        CurrentNewsItem(currentNews = article, boxShape = boxShape)
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                }
            }
        }

    }
}
