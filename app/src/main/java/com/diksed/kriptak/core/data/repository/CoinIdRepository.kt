package com.diksed.kriptak.core.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

interface CoinIdRepository {
    suspend fun saveCoinIds(coinIds: List<String>)
    suspend fun getSavedCoinIds(): List<String>
}

class CoinIdRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : CoinIdRepository {

    private companion object {
        const val PREF_KEY_COIN_IDS = "coin_ids"
    }

    override suspend fun saveCoinIds(coinIds: List<String>) {
        val json = Gson().toJson(coinIds)
        sharedPreferences.edit {
            putString(PREF_KEY_COIN_IDS, json)
        }
    }

    override suspend fun getSavedCoinIds(): List<String> {
        val json = sharedPreferences.getString(PREF_KEY_COIN_IDS, null)
        return if (json != null) {
            Gson().fromJson(json, object : TypeToken<List<String>>() {}.type)
        } else {
            emptyList()
        }
    }
}
