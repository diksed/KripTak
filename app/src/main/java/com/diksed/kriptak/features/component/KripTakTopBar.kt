package com.diksed.kriptak.features.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.diksed.kriptak.R

@Composable
fun KripTakTopBar(modifier: Modifier = Modifier, spacerHeight : Int = 10) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.kriptak_logo),
                contentDescription = "Logo",
                modifier = Modifier.scale(0.7f)
            )
            Spacer(modifier =  Modifier.height(spacerHeight.dp))
        }
    }
}