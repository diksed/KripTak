package com.diksed.kriptak.core.data.model

import com.google.gson.annotations.SerializedName

data class CoinResponse(
    @SerializedName("id")
    val cryptoId:String,
    @SerializedName("symbol")
    val symbol:String,
    @SerializedName("name")
    val name:String,
    @SerializedName("image")
    val image:String,
    @SerializedName("current_price")
    val current_price:String,
    @SerializedName("price_change_24h")
    val price_change_24h:String,
    @SerializedName("price_change_percentage_24h")
    val price_change_percentage_24h:String
)
