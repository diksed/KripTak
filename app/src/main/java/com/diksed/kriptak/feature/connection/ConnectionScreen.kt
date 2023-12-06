package com.diksed.kriptak.feature.connection

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.diksed.kriptak.R
import com.diksed.kriptak.ui.components.MainAppScaffold
import com.diksed.kriptak.ui.theme.noConnectionCircleColor

@Composable
internal fun ConnectionScreenRoute(
    modifier: Modifier = Modifier,
) {
    ConnectionScreen(
        modifier = modifier,
    )
}

@Composable
fun ConnectionScreen(modifier: Modifier = Modifier) {
    MainAppScaffold(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.kriptak_logo),
                contentDescription = "Kriptak Logo",
                modifier = Modifier
                    .fillMaxWidth(0.7f)
            )
            Spacer(modifier = Modifier.fillMaxSize(0.1f))
            Box(
                modifier = Modifier
                    .background(noConnectionCircleColor, shape = CircleShape)
                    .size(200.dp),
                contentAlignment = Alignment.Center
            ) {
                val composition by rememberLottieComposition(
                    spec = LottieCompositionSpec.RawRes(R.raw.no_internet_anim)
                )
                LottieAnimation(
                    composition,
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier
                        .fillMaxWidth(0.7f),
                )
            }
            Spacer(modifier = Modifier.fillMaxSize(0.05f))
            Text(
                text = stringResource(id = R.string.no_connection),
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.fillMaxSize(0.05f))
            Text(
                text = stringResource(id = R.string.check_connection),
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center,
            )
        }
    }
}
