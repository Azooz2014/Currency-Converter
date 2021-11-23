package com.example.currencyconverter.data.Local

import androidx.room.*
import com.example.currencyconverter.data.Model.Currency

/*
*  Thanks to https://github.com/nicoqueijo/Android-Currency-Converter/blob/master/app/src/main/java/com/nicoqueijo/android/currencyconverter/kotlin/data/CurrencyDao.kt*/

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrency(currency: Currency)

    @Transaction
    suspend fun insertCurrencies(currencies: List<Currency>){
        currencies.forEach { insertCurrency(it) }
    }

    @Query("UPDATE CurrencyTable SET CurrencyRateColumn = :rate WHERE CurrencyColumn = :currencyCode")
    suspend fun updateRate(currencyCode: String, rate: Double)

    @Transaction
    suspend fun updateRates(currencies: List<Currency>){
        currencies.forEach { updateRate(it.currencyCode, it.rate) }
    }

    @Query("SELECT * FROM CurrencyTable WHERE CurrencyColumn = :currencyCode")
    suspend fun getCurrency(currencyCode: String): Currency

    @Query("SELECT * FROM CurrencyTable ORDER BY CurrencyColumn ASC")
    suspend fun getCurrencies(): List<Currency>
}