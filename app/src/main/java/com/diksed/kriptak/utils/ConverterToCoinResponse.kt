package com.diksed.kriptak.utils

import com.diksed.kriptak.data.model.Coin
import com.diksed.kriptak.data.model.CoinListResponse
import com.diksed.kriptak.data.model.CoinResponse
import com.diksed.kriptak.data.model.Quote
import com.diksed.kriptak.data.model.Status

fun convertToCoinResponse(coinListResponse: CoinListResponse): CoinResponse {
    return CoinResponse(
        status = Status(
            timestamp = coinListResponse.status.timestamp,
            errorCode = coinListResponse.status.errorCode,
            errorMessage = coinListResponse.status.errorMessage,
            elapsed = coinListResponse.status.elapsed,
            creditCount = coinListResponse.status.creditCount
        ),
        data = coinListResponse.data.values.map { coinData ->
            Coin(
                id = coinData.id,
                name = coinData.name,
                symbol = coinData.symbol,
                slug = coinData.slug,
                numMarketPairs = coinData.numMarketPairs,
                dateAdded = coinData.dateAdded,
                tags = coinData.tags,
                maxSupply = coinData.maxSupply,
                circulatingSupply = coinData.circulatingSupply,
                totalSupply = coinData.totalSupply,
                platform = coinData.platform,
                cmcRank = coinData.cmcRank,
                lastUpdated = coinData.lastUpdated,
                quote = Quote(
                    usd = coinData.quote["USD"] ?: throw IllegalArgumentException("USD quote not found")
                )
            )
        }
    )
}
