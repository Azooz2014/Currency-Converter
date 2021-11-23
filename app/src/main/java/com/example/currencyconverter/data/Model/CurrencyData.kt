package com.example.currencyconverter.data.Model

import com.google.gson.annotations.SerializedName

data class CurrencyData(

    @SerializedName("symbols")
    val currencies: Map<String, Currency>

){
    data class Currency(

        val description: String,
        val code: String
    )
}
