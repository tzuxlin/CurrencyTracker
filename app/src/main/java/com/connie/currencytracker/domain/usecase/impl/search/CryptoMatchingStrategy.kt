package com.connie.currencytracker.domain.usecase.impl.search

import com.connie.currencytracker.domain.model.Crypto
import com.connie.currencytracker.domain.model.Currency
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Single
@Named(Currency.TYPE_CRYPTO)
class CryptoMatchingStrategy :
    CurrencyMatchingStrategy<Crypto> {

    override fun matches(currency: Crypto, query: String): Boolean {
        val regex = Regex("^$query|\\s$query", RegexOption.IGNORE_CASE)
        return regex.containsMatchIn(currency.name) ||
                currency.symbol.startsWith(query, ignoreCase = true)
    }
}