package com.connie.currencytracker.domain.usecase

import com.connie.currencytracker.domain.model.Currency
import kotlinx.coroutines.flow.Flow

interface SearchCurrencyUseCase {
    fun filter(input: String, list: List<Currency>): Flow<List<Currency>>
}