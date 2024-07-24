package com.diksed.kriptak.features.screen.crypto_details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.diksed.kriptak.features.ui.theme.DarkModerateViolet

@Composable
fun DetailRow(
    icon: Painter,
    label: String,
    value: String,
    labelFontSize: TextUnit = 18.sp,
    valueFontSize: TextUnit = 24.sp,
    labelColor: Color = Color.Gray,
    valueColor: Color = Color.White
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .height(IntrinsicSize.Min)
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = DarkModerateViolet,
                modifier = Modifier
                    .size(35.dp)
                    .align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = label,
                color = labelColor,
                fontSize = labelFontSize,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = value,
                color = valueColor,
                fontSize = valueFontSize
            )
        }
    }
}
