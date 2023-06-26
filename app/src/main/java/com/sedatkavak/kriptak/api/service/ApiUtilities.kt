package com.sedatkavak.kriptak.api.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiUtilities {
    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(" https://api.coinmarketcap.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}