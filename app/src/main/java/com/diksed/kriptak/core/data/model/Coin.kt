package com.diksed.kriptak.core.data.model

abstract class BaseCoin(
    open val id: String,
    open val name: String,
    open val symbol: String,
    open val image: String
)
data class Coin(
    override val id: String,
    override val name: String,
    override val symbol: String,
    override val image: String,
    val rank: Int?
) : BaseCoin(
    id = id,
    name = name,
    symbol = symbol,
    image = image
)
