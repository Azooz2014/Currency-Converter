package com.example.currencyconverter.data.Network

import com.example.currencyconverter.data.CurrencyData
import com.example.currencyconverter.data.Rate
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ApiService {

    @Headers("x-rapidapi-host: exchange-rate9.p.rapidapi.com", "x-rapidapi-key: {apiKey}")
    @GET("convert?from={from}&to={to}")
    suspend fun getRate(@Path("from") currencyFrom: String,
                                @Path("to") currencyTo: String,
                                @Path("apiKey") apiKey: String): Response<List<Rate>>

    @Headers("x-rapidapi-host: exchange-rate9.p.rapidapi.com", "x-rapidapi-key: 895218008fmsh26007e9e125e142p16047fjsnf40bc63a1012")
    @GET("https://exchange-rate9.p.rapidapi.com/symbols")
    suspend fun getCurrencies(): Response<CurrencyData>
}