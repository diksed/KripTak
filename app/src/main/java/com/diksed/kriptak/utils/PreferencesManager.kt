package com.diksed.kriptak.utils

import android.content.Context
import android.content.SharedPreferences
import com.diksed.kriptak.data.model.PriceAlert
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class PreferencesManager @Inject constructor(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    private val _favoritesFlow = MutableStateFlow<List<String>>(emptyList())
    val favoritesFlow: StateFlow<List<String>> = _favoritesFlow.asStateFlow()

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        val favorites = preferences.getStringSet("favorites", emptySet())?.toList() ?: emptyList()
        _favoritesFlow.value = favorites
    }

    fun getFavorites(): List<String> {
        return _favoritesFlow.value
    }

    fun isFavorite(symbol: String): Boolean {
        return _favoritesFlow.value.contains(symbol)
    }

    fun addFavorite(symbol: String) {
        val favorites = preferences.getStringSet("favorites", emptySet())?.toMutableSet() ?: mutableSetOf()
        favorites.add(symbol)
        preferences.edit().putStringSet("favorites", favorites).apply()
        _favoritesFlow.value = favorites.toList()
    }

    fun removeFavorite(symbol: String) {
        val favorites = preferences.getStringSet("favorites", emptySet())?.toMutableSet() ?: mutableSetOf()
        favorites.remove(symbol)
        preferences.edit().putStringSet("favorites", favorites).apply()
        _favoritesFlow.value = favorites.toList()
    }

    fun getPriceAlerts(): List<PriceAlert> {
        val json = preferences.getString(PRICE_ALERTS_KEY, null) ?: return emptyList()
        return try {
            val type = object : TypeToken<List<PriceAlert>>() {}.type
            gson.fromJson(json, type)
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun getPriceAlert(coinId: Int): PriceAlert? {
        return getPriceAlerts().find { it.coinId == coinId }
    }

    /** One alert per coin - setting a new one for a coin replaces its previous alert. */
    fun setPriceAlert(alert: PriceAlert) {
        val alerts = getPriceAlerts().filterNot { it.coinId == alert.coinId } + alert
        savePriceAlerts(alerts)
    }

    fun removePriceAlert(coinId: Int) {
        savePriceAlerts(getPriceAlerts().filterNot { it.coinId == coinId })
    }

    private fun savePriceAlerts(alerts: List<PriceAlert>) {
        preferences.edit().putString(PRICE_ALERTS_KEY, gson.toJson(alerts)).apply()
    }

    /**
     * Whether we've already asked the user for the notification permission at
     * least once. Used so MainActivity only prompts on the very first launch
     * instead of re-requesting (and re-toasting "denied") on every app open.
     */
    fun hasAskedNotificationPermission(): Boolean {
        return preferences.getBoolean(NOTIFICATION_PERMISSION_ASKED_KEY, false)
    }

    fun setAskedNotificationPermission() {
        preferences.edit().putBoolean(NOTIFICATION_PERMISSION_ASKED_KEY, true).apply()
    }

    companion object {
        private const val PRICE_ALERTS_KEY = "price_alerts"
        private const val NOTIFICATION_PERMISSION_ASKED_KEY = "notification_permission_asked"
    }
}