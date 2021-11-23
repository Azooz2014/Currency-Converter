package com.example.currencyconverter

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.currencyconverter.data.Local.CurrencyDatabase
import com.example.currencyconverter.data.Network.WebService
import com.example.currencyconverter.data.Model.Currency
import com.example.currencyconverter.data.Repository.DefaultRepository
import com.example.currencyconverter.util.Utils
import com.example.currencyconverter.util.Utils.isNetworkAvailable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://exchange-rate9.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //For Testing Only, Remove when finalizing.
        val api = retrofit.create(WebService::class.java)
        val db = CurrencyDatabase.getInstance(this)
        val dao = db.currencyDao()
        var currencies: List<Currency> = emptyList()
        val repo = DefaultRepository(this)


        /*GlobalScope.launch {
            withContext(Dispatchers.IO) {
                val currenciesRes = api.getCurrencies(BuildConfig.API_KEY)
                val data = currenciesRes.body()

                val currFrom = data?.currencies?.values?.find { it.code == "EUR" }!!.code
                val currTo = data?.currencies?.values?.find { it.code == "SAR" }!!.code

                val rateRes = api.getLatestRates(BuildConfig.API_KEY)
                val rateData = rateRes.body()

                Log.i("MainActivity API res",
                    rateData?.rates?.forEach{ println("Key: ${it.key} value: ${it.value}")}.toString()
                )
            }
        }*/

        /*GlobalScope.launch {
            withContext(Dispatchers.IO) {

                val rateRes = api.getLatestRates(BuildConfig.API_KEY)
                val rateData = rateRes.body()
                val currenciesRes = api.getCurrencies(BuildConfig.API_KEY)
                val currencyDesc = currenciesRes.body()

                currencies = rateData?.rates?.map {

                    Currency(currencyCode = it.key, description = currencyDesc?.currencies?.getValue(it.key)?.description!!, rate = it.value)
                }!!
                dao.insertCurrencies(currencies)

                dao.updateRate("SAR", 50.90)

                dao.updateRates(currencies)

                Log.i("MainActivity API res", dao.getCurrency("SAR").toString())
            }
        }*/

        Log.i("MainActivity", "Network is available:  ${this.isNetworkAvailable()}")

        /*Testing repository*/

        GlobalScope.launch {
            repo.fetchData(false, true)
//            dao.updateRate("SAR", 80.0)
        }
    }
}