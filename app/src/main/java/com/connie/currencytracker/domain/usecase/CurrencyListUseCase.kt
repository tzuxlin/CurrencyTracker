package com.connie.currencytracker.domain.usecase

import com.connie.currencytracker.domain.model.Currency
import kotlinx.coroutines.flow.Flow

interface CurrencyListUseCase {
    fun getCryptos(): Flow<List<Currency>>
    fun getFiats(): Flow<List<Currency>>
    fun getAll(): Flow<List<Currency>>
    suspend fun save(list: List<Currency>): Boolean
    suspend fun clear(): Boolean
}