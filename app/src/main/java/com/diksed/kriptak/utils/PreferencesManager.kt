package com.diksed.kriptak.utils

import android.content.Context

class PreferencesManager(context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences("crypto_prefs", Context.MODE_PRIVATE)

    fun addFavorite(coinId: String) {
        val favorites = getFavorites().toMutableSet()
        favorites.add(coinId)
        sharedPreferences.edit().putStringSet("favorites", favorites).apply()
    }

    fun removeFavorite(coinId: String) {
        val favorites = getFavorites().toMutableSet()
        favorites.remove(coinId)
        sharedPreferences.edit().putStringSet("favorites", favorites).apply()
    }

    fun getFavorites(): Set<String> {
        return sharedPreferences.getStringSet("favorites", emptySet()) ?: emptySet()
    }

    fun isFavorite(coinId: String): Boolean {
        return getFavorites().contains(coinId)
    }
}
