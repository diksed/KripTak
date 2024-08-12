package com.diksed.kriptak.features.screen.favorites.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.diksed.kriptak.R
import com.diksed.kriptak.features.ui.theme.bottomAppBarItemColor
import com.diksed.kriptak.features.ui.theme.boxColor

@Composable
fun AddToFavoritesButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = boxColor,
            contentColor = bottomAppBarItemColor
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(48.dp)
            .shadow(4.dp, shape = RoundedCornerShape(10.dp))
    ) {
        Text(
            text = stringResource(id = R.string.addFavorite),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}
