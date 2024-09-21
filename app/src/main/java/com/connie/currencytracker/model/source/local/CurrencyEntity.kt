package com.connie.currencytracker.model.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_table")
data class CurrencyEntity(
    @PrimaryKey val id: String,
    val type: String,
    val name: String,
    val symbol: String,
    val code: String?,
)