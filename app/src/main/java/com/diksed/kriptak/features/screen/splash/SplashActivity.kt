package com.diksed.kriptak.features.screen.splash

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.diksed.kriptak.features.screen.main.MainActivity
import com.diksed.kriptak.utils.Utility.launchActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


@SuppressLint("CustomSplashScreen")
class  SplashActivity : ComponentActivity() {
    companion object {
        const val SPLASH_FADE_DURATION_MILLIS = 1000L
    }

    private val viewModel:  SplashViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        var uiState:  SplashUiState by mutableStateOf( SplashUiState.Loading)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState
                    .onEach {
                        uiState = it
                    }
                    .collect()
            }
        }

        splashScreen.setKeepOnScreenCondition {
            when (uiState) {
                SplashUiState.Loading -> true
                is  SplashUiState.Success -> false
            }
        }

        // If the splash screen is still visible, set the content view
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S_V2) {
            splashScreen.setOnExitAnimationListener { splashScreenViewProvider ->
                // Get logo and start a fade out animation
                splashScreenViewProvider.iconView
                    .animate()
                    .rotation(360f)
                    .setDuration(SPLASH_FADE_DURATION_MILLIS)
                    .alpha(0f)
                    .withEndAction {
                        // After the fade out, remove the
                        // splash and set content view
                        splashScreenViewProvider.remove()
                        startMainActivity()
                    }.start()
            }
        } else {
            startMainActivity()
        }
    }
    private fun startMainActivity() {
        launchActivity(
            packageName = packageName,
            className = MainActivity::class.java.name
        )
    }
}
