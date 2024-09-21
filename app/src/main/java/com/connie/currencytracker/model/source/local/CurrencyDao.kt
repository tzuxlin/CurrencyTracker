package com.connie.currencytracker.model.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrency(currency: CurrencyEntity)

    @Query("SELECT * FROM currency_table")
    fun getAllCurrencies(): Flow<List<CurrencyEntity>>

    @Query("SELECT * FROM currency_table WHERE type = :currencyType")
    fun getCurrenciesByType(currencyType: String): Flow<List<CurrencyEntity>>

    @Delete
    suspend fun deleteCurrency(currency: CurrencyEntity)

    @Query("DELETE FROM currency_table")
    suspend fun clearCurrencies()
}