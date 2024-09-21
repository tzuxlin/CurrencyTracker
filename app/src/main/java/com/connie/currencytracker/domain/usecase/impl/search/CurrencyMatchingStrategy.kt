package com.connie.currencytracker.domain.usecase.impl.search

import com.connie.currencytracker.domain.model.Currency

interface CurrencyMatchingStrategy<T : Currency> {
    fun matches(currency: T, query: String): Boolean
}