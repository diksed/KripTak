package com.diksed.kriptak.features.screen.home.components.daily_news

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.diksed.kriptak.R
import com.diksed.kriptak.data.model.Article
import com.diksed.kriptak.features.component.BoxShape
import com.diksed.kriptak.features.ui.theme.boxColor
import com.diksed.kriptak.utils.formatDateTime
import com.diksed.kriptak.utils.isImageUrlValid
import kotlinx.coroutines.launch


@Composable
fun DailyNewsItem(
    dailyNews: Article,
    boxShape: BoxShape,
    placeholderImageResId: Int = R.drawable.logo
) {
    val context = LocalContext.current
    val imageUrl by remember { mutableStateOf(dailyNews.urlToImage) }
    var isValidUrl by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(dailyNews.urlToImage) {
        scope.launch {
            isValidUrl = isImageUrlValid(dailyNews.urlToImage)
        }
    }

    val cornerRadius = when (boxShape) {
        BoxShape.TOP -> RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
        BoxShape.MIDDLE -> RoundedCornerShape(0.dp)
        BoxShape.BOTTOM -> RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
    }

    Box(
        modifier = Modifier
            .background(boxColor, shape = cornerRadius)
            .padding(8.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(dailyNews.url))
                context.startActivity(intent)
            }
    ) {
        Row(Modifier.fillMaxSize()) {
            DailyNewsImage(
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
                Text(
                    text = dailyNews.title,
                    color = Color.White,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 11.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            Text(
                text = formatDateTime(dailyNews.publishedAt),
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.Bottom)
                    .padding(end = 8.dp, bottom = 8.dp)
            )
        }
    }
}
