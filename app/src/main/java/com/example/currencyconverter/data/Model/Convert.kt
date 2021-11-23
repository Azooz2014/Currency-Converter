package com.example.currencyconverter.data.Model

import com.google.gson.annotations.SerializedName

data class Convert(

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
