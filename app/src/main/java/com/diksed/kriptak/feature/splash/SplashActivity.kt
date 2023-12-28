package com.diksed.kriptak.feature.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.diksed.kriptak.R
import com.diksed.kriptak.feature.main.MainActivity
import com.diksed.kriptak.ui.components.CustomLottieAnimation
import com.diksed.kriptak.ui.theme.KripTakTheme
import com.diksed.kriptak.ui.theme.bgColor
import kotlinx.coroutines.delay

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KripTakTheme {
                SplashScreen()
            }
        }
    }

    @Composable
    private fun SplashScreen() {
        LaunchedEffect(key1 = true) {
            delay(2000)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        }

        CustomLottieAnimation(
            animation = R.raw.kriptak_splash,
            modifier = Modifier
                .fillMaxSize()
                .background(bgColor)
        )
    }
}
