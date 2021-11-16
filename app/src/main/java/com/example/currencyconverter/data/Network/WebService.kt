package com.example.currencyconverter.data.Network

import com.example.currencyconverter.data.model.CurrencyData
import com.example.currencyconverter.data.model.Convert
import com.example.currencyconverter.data.model.ExchangeRate
import retrofit2.Response
import retrofit2.http.*

interface WebService {

    @Headers("x-rapidapi-host: exchange-rate9.p.rapidapi.com")
    @GET("convert")
    suspend fun convert(@Query("from") currencyFrom: String,
                        @Query("to") currencyTo: String,
                        @Query("amount") amountToConvert: Double,
                        @Header("x-rapidapi-key") apiKey: String): Response<Convert>

    @Headers("x-rapidapi-host: exchange-rate9.p.rapidapi.com")
    @GET("symbols")
    suspend fun getCurrencies(@Header("x-rapidapi-key") apiKey: String): Response<CurrencyData>

    @Headers("x-rapidapi-host: exchange-rate9.p.rapidapi.com")
    @GET("latest?base=SAR")
    suspend fun getLatestRates(@Header("x-rapidapi-key") apiKey: String): Response<ExchangeRate>
}