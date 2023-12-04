package com.diksed.kriptak.feature.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.diksed.kriptak.core.util.NetworkMonitor
import com.diksed.kriptak.feature.appstate.MainApp
import com.diksed.kriptak.ui.theme.KripTakTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var networkMonitor: NetworkMonitor
    private val viewModel: MainActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        var uiState: MainActivityUiState by mutableStateOf(MainActivityUiState.Loading)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState
                    .collect {
                    }
            }
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent()
    }

    private fun setContent() {
        setContent {
            KripTakTheme {
                MainApp(networkMonitor = networkMonitor)
            }
        }
    }
}
