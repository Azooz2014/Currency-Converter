package com.example.currencyconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.currencyconverter.data.Network.ApiService
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
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

        val api = retrofit.create(ApiService::class.java)

        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                val currenciesRes = api.getCurrencies(BuildConfig.API_KEY)
                val data = currenciesRes.body()

                val currFrom = data?.currencies?.values?.find { it.code == "EUR" }!!.code
                val currTo = data?.currencies?.values?.find { it.code == "SAR" }!!.code

                val rateRes = api.convert(currFrom, currTo, 5.0, BuildConfig.API_KEY)
                val rateData = rateRes.body()

                Log.i("MainActivity API res", rateData.toString())
            }
        }
    }
}