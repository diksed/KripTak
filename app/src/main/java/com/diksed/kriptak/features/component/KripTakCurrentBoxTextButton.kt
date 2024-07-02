package com.diksed.kriptak.features.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.diksed.kriptak.R

@Composable
fun KripTakCurrentBoxTextButton(
    text: String,
    modifier: Modifier = Modifier,
    navigateTo: () -> Unit = {}
) {
    Row(
        modifier = Modifier.clickable {
            navigateTo()
        },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 12.sp,
            modifier = modifier,
        )
        Spacer(modifier = modifier.width(5.dp))
        Icon(
            painter = painterResource(id = R.drawable.ic_right_arrow),
            contentDescription = null,
            tint = Color.White,
            modifier = modifier.size(15.dp)
        )

    }
}
