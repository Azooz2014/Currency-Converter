package com.example.currencyconverter.data

import com.google.gson.annotations.SerializedName

data class Rate(

    val query: Query,
    @SerializedName("result")
    val toAmount: Double,
    val info: Info

) {
    data class Query(
        @SerializedName("from")
        val currencyFrom: String,
        @SerializedName("to")
        val currencyTo: String,
        @SerializedName("amount")
        val fromAmount: Double
    )

    data class Info(
        val rate: Double
    )
}
