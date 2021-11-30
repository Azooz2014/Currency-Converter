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

    override suspend fun insertCurrencies(currencies: List<Currency>) =
        dao.insertCurrencies(currencies)

    override suspend fun updateRates(currencies: List<Currency>) = dao.updateRates(currencies)

    override suspend fun getCurrency(currencyCode: String): Currency = dao.getCurrency(currencyCode)

    override suspend fun getCurrencies(): List<Currency> = dao.getCurrencies()

    override suspend fun fetchData(
        forceUpdate: Boolean,
        isDataOld: Boolean
    ) {

        if (context.isNetworkAvailable()) {
            if (isFirstLaunched) {
                currencies()?.let { insertCurrencies(it) }
                isFirstLaunched = false
                return
            }
            if (isDataOld || forceUpdate) {
                currencies()?.let { updateRates(it) }
                return
            } else {
                getCurrencies()
                return
            }
        } else {
            //TODO: Find away to pass error massage to presenter
            return
        }
    }

    private suspend fun currencies(): List<Currency>? {
        val currencies = webService.getLatestRates(BuildConfig.API_KEY).body()?.rates?.map {
            val currencyDescription =
                webService.getCurrencies(BuildConfig.API_KEY).body()?.currencies?.getValue(
                    it.key
                )?.description ?: ""

            Currency(currencyCode = it.key, description = currencyDescription, it.value)
        }
        return currencies
    }
}