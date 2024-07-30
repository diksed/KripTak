package com.diksed.kriptak.features.screen.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.diksed.kriptak.KripTakApp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.diksed.kriptak.features.component.KripTakNoInternet
import com.diksed.kriptak.features.navigation.NavGraph
import com.diksed.kriptak.features.ui.theme.KripTakTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var application: KripTakApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val mainViewModel: MainViewModel = hiltViewModel()
            val isConnected by mainViewModel.isConnected.collectAsState()

            if (!isConnected) {
                KripTakNoInternet()
            } else {
                KripTakTheme(darkTheme = application.isDark.value) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        NavGraph()
                    }
                }
            }
        }
    }
}
