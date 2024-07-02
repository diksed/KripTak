package com.diksed.kriptak.features.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

@Composable
fun KripTakCurrentBoxTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title.uppercase(Locale.getDefault()),
        color = Color.White,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        modifier = modifier.padding(bottom = 7.dp)
    )
}
