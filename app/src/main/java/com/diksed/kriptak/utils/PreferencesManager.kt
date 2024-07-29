package com.diksed.kriptak.utils

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class PreferencesManager @Inject constructor(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

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
}