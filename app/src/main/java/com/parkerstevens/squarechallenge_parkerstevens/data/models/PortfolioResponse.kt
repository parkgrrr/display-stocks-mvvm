package com.parkerstevens.squarechallenge_parkerstevens.data.models

import com.google.gson.annotations.SerializedName

data class PortfolioResponse(
    val stocks: List<Stock>,
)

data class Stock(
    val ticker: String,
    val name: String,
    val currency: String,
    @SerializedName("current_price_cents")
    val cents: Long,
    val quantity: Int?,
    @SerializedName("current_price_timestamp")
    val timestamp: Long,
)