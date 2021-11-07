package com.example.currencyconverter.data

import com.google.gson.annotations.SerializedName

data class Rate(
    @SerializedName("from")
    val currencyFrom: String,
    @SerializedName("to")
    val currencyTo: String,
    @SerializedName("amount")
    val fromAmount: Double,
    @SerializedName("result")
    val toAmount: Double,
    @SerializedName("rate")
    val rate: Double
    )
