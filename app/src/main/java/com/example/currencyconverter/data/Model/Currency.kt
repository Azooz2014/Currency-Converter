package com.example.currencyconverter.data.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CurrencyTable")
data class Currency(

    @PrimaryKey
    @ColumnInfo(name = "CurrencyColumn")
    val currencyCode: String,
    @ColumnInfo(name = "CurrencyDescriptionColumn")
    val description: String,
    @ColumnInfo(name = "CurrencyRateColumn")
    val rate: Double
)
