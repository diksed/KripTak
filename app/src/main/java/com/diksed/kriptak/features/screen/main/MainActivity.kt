package com.diksed.kriptak.features.screen.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.diksed.kriptak.KripTakApp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.diksed.kriptak.R
import com.diksed.kriptak.features.component.KripTakNoInternet
import com.diksed.kriptak.features.navigation.NavGraph
import com.diksed.kriptak.features.screen.crypto.navigation.cryptoNavigationRoute
import com.diksed.kriptak.features.screen.favorites.navigation.favoritesNavigationRoute
import com.diksed.kriptak.features.screen.home.navigation.homeNavigationRoute
import com.diksed.kriptak.features.ui.theme.KripTakTheme
import com.diksed.kriptak.utils.LocalKripTakApp

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_NOTIFICATION_PERMISSION = 1
    }

    @Inject
    lateinit var application: KripTakApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkNotificationPermission()
        val startDestination = startDestinationFromShortcut()
        setContent {
            val mainViewModel: MainViewModel = hiltViewModel()
            val isConnected by mainViewModel.isConnected.collectAsState()

            if (!isConnected) {
                KripTakNoInternet()
            } else {
                CompositionLocalProvider(LocalKripTakApp provides application) {
                    KripTakTheme(darkTheme = application.isDark.value) {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            NavGraph(startDestination = startDestination)
                        }
                    }
                }
            }
        }
    }

    // Launcher long-press shortcuts (res/xml/shortcuts.xml) target this activity
    // directly with a "shortcut_destination" extra so they jump straight to the
    // right screen instead of always opening on Home.
    private fun startDestinationFromShortcut(): String {
        return when (intent?.getStringExtra("shortcut_destination")) {
            "favorites" -> favoritesNavigationRoute
            "crypto_list" -> cryptoNavigationRoute
            else -> homeNavigationRoute
        }
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    REQUEST_NOTIFICATION_PERMISSION
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_NOTIFICATION_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(
                    this,
                    R.string.notificationPermissionGranted,
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(this, R.string.notificationPermissionDenied, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}
