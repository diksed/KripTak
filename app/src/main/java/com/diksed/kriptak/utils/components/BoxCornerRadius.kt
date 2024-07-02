package com.diksed.kriptak.utils.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.diksed.kriptak.features.component.BoxShape

@Composable
fun getCornerRadius(boxShape: BoxShape): RoundedCornerShape {
    return when (boxShape) {
        BoxShape.TOP -> RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
        BoxShape.MIDDLE -> RoundedCornerShape(0.dp)
        BoxShape.BOTTOM -> RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
    }
}