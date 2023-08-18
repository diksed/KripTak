package com.sedatkavak.kriptak.screens.connection_screen

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object ConnectionUtils {
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities =
            if (network != null) connectivityManager.getNetworkCapabilities(network) else null

        return networkCapabilities?.run {
            hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } ?: false
    }
}
