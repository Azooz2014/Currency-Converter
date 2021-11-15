package com.example.currencyconverter.data.Local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.currencyconverter.data.model.Currency

/*
*  Thanks to https://github.com/nicoqueijo/Android-Currency-Converter/blob/master/app/src/main/java/com/nicoqueijo/android/currencyconverter/kotlin/data/CurrencyDatabase.kt*/

@Database(entities = [Currency::class], version = 1)
abstract class CurrencyDatabase: RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao

    companion object {
        private const val DATABASE_NAME = "currency.db"

        @Volatile
        private var instance: CurrencyDatabase? = null

        fun getInstance(context: Context): CurrencyDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): CurrencyDatabase {
            return Room.databaseBuilder(context, CurrencyDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}