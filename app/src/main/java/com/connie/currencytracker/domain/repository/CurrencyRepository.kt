package com.connie.currencytracker.domain.repository

import com.connie.currencytracker.domain.model.Currency
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    fun getCryptos(): Flow<List<Currency>>
    fun getFiats(): Flow<List<Currency>>
    fun getAll(): Flow<List<Currency>>
    suspend fun save(list: List<Currency>)
    suspend fun clear()
}