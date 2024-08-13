package com.diksed.kriptak.features.screen.crypto_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.diksed.kriptak.R
import com.diksed.kriptak.features.ui.theme.bottomAppBarItemColor
import com.diksed.kriptak.features.ui.theme.selectedButtonColor

@Composable
fun EmptyNewsMessage() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_news),
            contentDescription = null,
            tint = bottomAppBarItemColor,
            modifier = Modifier
                .size(80.dp)
                .shadow(8.dp, shape = CircleShape)
                .background(selectedButtonColor, shape = CircleShape)
                .padding(16.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(id = R.string.noNewsAboutCrypto),
            color = Color(0xFFB0B0B0),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
    }
}