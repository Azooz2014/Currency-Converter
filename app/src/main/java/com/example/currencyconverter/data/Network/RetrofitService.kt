package com.example.currencyconverter.data.Network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {

    private const val BASE_URL = "https://exchange-rate9.p.rapidapi.com/"

    private fun retrofitService(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val exchangeRateApi: WebService by lazy {
        retrofitService().create(WebService::class.java)
    }
}