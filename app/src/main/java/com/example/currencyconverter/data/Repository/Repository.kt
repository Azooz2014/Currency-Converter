package com.example.currencyconverter.data.Repository

import com.example.currencyconverter.data.Model.Currency
import com.example.currencyconverter.data.Model.CurrencyData
import com.example.currencyconverter.data.Model.ExchangeRate
import retrofit2.Response

interface Repository {

    suspend fun insertCurrencies(currencies: List<Currency>)
    suspend fun updateRates(currencies: List<Currency>)
    suspend fun getCurrency(currencyCode: String): Currency
    suspend fun getCurrencies(): List<Currency>
    suspend fun fetchData(
        forceUpdate: Boolean, isDataOld: Boolean
    )
}