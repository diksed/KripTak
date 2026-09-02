package com.diksed.kriptak.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.palette.graphics.Palette
import coil3.imageLoader
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import coil3.request.allowHardware
import coil3.toBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// Small process-wide cache: the same coin icon URL repeats across screens
// (Home, Favorites, Crypto List) and re-scrolling the same list shouldn't
// re-fetch and re-run Palette on every recomposition.
private val dominantColorCache = mutableMapOf<String, Color>()

/**
 * Loads [imageUrl] (independently of whatever's rendering it on screen) and
 * extracts its most prominent color via the Palette API, for use as a
 * per-coin accent (e.g. CryptoListItem's left bar). Returns [fallback] until
 * that finishes, or if it fails - this is a decorative touch, never worth
 * blocking or erroring the row over.
 */
@Composable
fun rememberDominantColor(imageUrl: String, fallback: Color): Color {
    val context = LocalContext.current
    var color by remember(imageUrl) { mutableStateOf(dominantColorCache[imageUrl] ?: fallback) }

    LaunchedEffect(imageUrl) {
        dominantColorCache[imageUrl]?.let {
            color = it
            return@LaunchedEffect
        }
        val extracted = withContext(Dispatchers.IO) {
            try {
                val request = ImageRequest.Builder(context)
                    .data(imageUrl)
                    .allowHardware(false)
                    .build()
                val result = context.imageLoader.execute(request)
                if (result is SuccessResult) {
                    val bitmap = result.image.toBitmap()
                    val palette = Palette.from(bitmap).generate()
                    val swatch = palette.vibrantSwatch
                        ?: palette.dominantSwatch
                        ?: palette.mutedSwatch
                    swatch?.let { Color(it.rgb) }
                } else {
                    null
                }
            } catch (e: Exception) {
                null
            }
        }
        if (extracted != null) {
            dominantColorCache[imageUrl] = extracted
            color = extracted
        }
    }

    return color
}
