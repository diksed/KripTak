package com.diksed.kriptak.features.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = PaleViolet,
    onPrimary = VeryDarkViolet,
    secondary = LightGrayishViolet90,
    onSecondary = VeryDarkGrayishViolet40,
    error = VerySoftRed,
    onError = VeryDarkRed,
    background = VeryDarkBlue,
    onBackground = LightGrayishMagenta,
    surface = VeryDarkBlue,
    onSurface = LightGrayishMagenta,
)

private val LightColorScheme = lightColorScheme(
    primary = DarkModerateViolet,
    onPrimary = White,
    secondary = VeryDarkGrayishViolet,
    onSecondary = LightGrayishViolet,
    error = StrongRed,
    onError = White,
    background = PaleGray,
    onBackground = VeryDarkBlue,
    surface = White,
    onSurface = LightGrayishMagenta,
)

@Composable
fun KripTakTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colors,
        shapes = Shapes,
        content = content
    )
}