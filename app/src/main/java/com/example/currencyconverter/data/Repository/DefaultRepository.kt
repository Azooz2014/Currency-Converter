package com.example.currencyconverter.data.Repository

import android.content.Context
import com.example.currencyconverter.BuildConfig
import com.example.currencyconverter.data.Local.CurrencyDao
import com.example.currencyconverter.data.Local.CurrencyDatabase
import com.example.currencyconverter.data.Model.Currency
import com.example.currencyconverter.data.Network.RetrofitService
import com.example.currencyconverter.data.Network.WebService
import com.example.currencyconverter.util.Utils.isNetworkAvailable

class DefaultRepository(private val context: Context) : Repository {

    private var dao: CurrencyDao
    private var webService: WebService
    private var isFirstLaunched = true

    init {
        val db = CurrencyDatabase.getInstance(context)
        dao = db.currencyDao()
        webService = RetrofitService.exchangeRateApi
    }

    override suspend fun insertCurrency(currency: Currency) = dao.insertCurrency(currency)

    override suspend fun insertCurrencies(currencies: List<Currency>) =
        dao.insertCurrencies(currencies)

    override suspend fun updateRate(currencyCode: String, rate: Double) {
        TODO("Not yet implemented")
    }

    override suspend fun updateRates(currencies: List<Currency>) = dao.updateRates(currencies)

    override suspend fun getCurrency(currencyCode: String): Currency = dao.getCurrency(currencyCode)

    override suspend fun getCurrencies(): List<Currency> = dao.getCurrencies()

    override suspend fun fetchData(
        forceUpdate: Boolean,
        isDataOld: Boolean
    ) {

        //TODO: Optimize this code...
        if (context.isNetworkAvailable() && isFirstLaunched) {

            val currencies = webService.getLatestRates(BuildConfig.API_KEY).body()?.rates?.map {
                val currencyDescription =
                    webService.getCurrencies(BuildConfig.API_KEY).body()?.currencies?.getValue(
                        it.key
                    )?.description ?: ""
                Currency(currencyCode = it.key, description = currencyDescription, it.value)
            }
            if (currencies != null) {
                insertCurrencies(currencies)
                isFirstLaunched = false
            }
        } else if (context.isNetworkAvailable() && (forceUpdate || isDataOld)) {
            val currencies = webService.getLatestRates(BuildConfig.API_KEY).body()?.rates?.map {
                val currencyDescription =
                    webService.getCurrencies(BuildConfig.API_KEY).body()?.currencies?.getValue(
                        it.key
                    )?.description ?: ""
                Currency(currencyCode = it.key, description = currencyDescription, it.value)
            }
            if (currencies != null) {
                updateRates(currencies)
            }
        } else return
    }
}