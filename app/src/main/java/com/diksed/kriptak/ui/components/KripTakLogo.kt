package com.diksed.kriptak.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.diksed.kriptak.R

@Composable
fun KripTakLogo(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.fillMaxWidth(0.5f).wrapContentHeight(),
        painter = painterResource(id = R.drawable.kriptak_logo),
        contentDescription = stringResource(
            id = R.string.app_name
        ),
    )
}