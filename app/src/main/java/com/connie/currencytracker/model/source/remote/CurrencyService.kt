package com.connie.currencytracker.model.source.remote

import com.connie.currencytracker.domain.model.Currency
import kotlinx.coroutines.flow.Flow

interface CurrencyService {
    fun getCryptoList(): Flow<List<Currency>>

    fun getFiatList(): Flow<List<Currency>>
}