package com.diksed.kriptak.feature.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.diksed.kriptak.core.util.NetworkMonitor
import com.diksed.kriptak.ui.theme.KripTakTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var networkMonitor : NetworkMonitor
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            KripTakTheme {
                MainScreen()
            }
        }
    }
}

@Composable
private fun MainScreen(){
    Text(text = "Main Screen")
}