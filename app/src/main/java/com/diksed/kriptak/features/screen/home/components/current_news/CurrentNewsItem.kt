package com.diksed.kriptak.features.screen.home.components.current_news

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.diksed.kriptak.R
import com.diksed.kriptak.data.model.Article
import com.diksed.kriptak.features.component.BoxShape
import com.diksed.kriptak.features.component.DateFormatter
import com.diksed.kriptak.features.component.KripTakText
import com.diksed.kriptak.features.ui.theme.boxColor
import com.diksed.kriptak.utils.components.getCornerRadius
import com.diksed.kriptak.utils.formatters.formatDate
import com.diksed.kriptak.utils.formatters.formatTime
import com.diksed.kriptak.utils.isImageUrlValid
import kotlinx.coroutines.launch

@SuppressLint("NewApi")
@Composable
fun CurrentNewsItem(
    currentNews: Article,
    boxShape: BoxShape,
    placeholderImageResId: Int = R.drawable.logo
) {
    val context = LocalContext.current
    val imageUrl by remember { mutableStateOf(currentNews.urlToImage) }
    var isValidUrl by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(currentNews.urlToImage) {
        scope.launch {
            isValidUrl = isImageUrlValid(currentNews.urlToImage)
        }
    }

    val cornerRadius = getCornerRadius(boxShape)

    Box(
        modifier = Modifier
            .background(boxColor, shape = cornerRadius)
            .padding(8.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(currentNews.url))
                context.startActivity(intent)
            }
    ) {
        Row(Modifier.fillMaxSize()) {
            CurrentNewsImage(
                imageUrl = imageUrl,
                isValidUrl = isValidUrl,
                placeholderImageResId = placeholderImageResId
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .align(Alignment.CenterVertically)
                    .weight(1f)
            ) {
                KripTakText(
                    text = currentNews.title,
                    maxLines = 2,
                    fontSize = 11.sp,
                    modifier = Modifier.padding(bottom = 2.dp)
                )

                Row {
                    DateFormatter(
                        painterId = R.drawable.ic_calendar,
                        dateTime = formatDate(currentNews.publishedAt)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    DateFormatter(
                        painterId = R.drawable.ic_clock,
                        dateTime = formatTime(currentNews.publishedAt)
                    )
                }
            }
        }
    }
}
