package com.diksed.kriptak.ui.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.diksed.kriptak.R

@Composable
fun SvgImage(image: String, modifier: Modifier = Modifier) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .decoderFactory(SvgDecoder.Factory())
            .data(image)
            .size(coil.size.Size.ORIGINAL)
            .build()
    )

    when (painter.state) {
        is AsyncImagePainter.State.Success, is AsyncImagePainter.State.Empty -> {
            Image(
                painter = painter,
                contentDescription = null,
                modifier = modifier.scale(1.1f),
            )
        }

        is AsyncImagePainter.State.Loading -> {
            CircularProgressIndicator()
        }

        else -> {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.drawable.no_data_chart),
                    contentDescription = null,
                )
                Text(
                    text = stringResource(id = R.string.no_graph),
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 12.sp)
                )
            }
        }
    }
}
