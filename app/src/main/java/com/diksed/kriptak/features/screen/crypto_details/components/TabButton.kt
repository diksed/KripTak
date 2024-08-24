package com.diksed.kriptak.features.screen.crypto_details.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.diksed.kriptak.features.component.KripTakText
import com.diksed.kriptak.features.ui.theme.scaffoldBackgroundColor
import com.diksed.kriptak.utils.noRippleClickable

@Composable
fun TabButton(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) scaffoldBackgroundColor else Color.Transparent, label = "",
    )
    val paddingVertical by animateDpAsState(
        targetValue = if (isSelected) 16.dp else 16.dp, label = "",
    )

    Box(
        modifier = modifier
            .background(backgroundColor, shape = RoundedCornerShape(10.dp))
            .noRippleClickable { onClick() }
            .padding(vertical = paddingVertical),
        contentAlignment = Alignment.Center,
    ) {
        KripTakText(
            text = text,
            color = if (!isSelected) Color.White else Color.LightGray,
            fontSize = 16.sp
        )
    }
}