package com.connie.currencytracker.domain.usecase.impl.search

import com.connie.currencytracker.domain.model.Currency
import com.connie.currencytracker.domain.model.Fiat
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Single
@Named(Currency.TYPE_FIAT)
class FiatMatchingStrategy :
    CurrencyMatchingStrategy<Fiat> {
    override fun matches(currency: Fiat, query: String): Boolean {
        val regex = Regex("^$query|\\s$query", RegexOption.IGNORE_CASE)
        return regex.containsMatchIn(currency.name)
    }
}