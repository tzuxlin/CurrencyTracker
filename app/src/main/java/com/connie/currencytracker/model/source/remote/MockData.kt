package com.connie.currencytracker.model.source.remote

import com.connie.currencytracker.domain.model.Crypto
import com.connie.currencytracker.domain.model.Currency
import com.connie.currencytracker.domain.model.Fiat

val cryptoCurrencyList = listOf<Currency>(
    Crypto(id = "BTC", name = "Bitcoin", symbol = "BTC"),
    Crypto(id = "ETH", name = "Ethereum", symbol = "ETH"),
    Crypto(id = "XRP", name = "XRP", symbol = "XRP"),
    Crypto(id = "BCH", name = "Bitcoin Cash", symbol = "BCH"),
    Crypto(id = "LTC", name = "Litecoin", symbol = "LTC"),
    Crypto(id = "EOS", name = "EOS", symbol = "EOS"),
    Crypto(id = "BNB", name = "Binance Coin", symbol = "BNB"),
    Crypto(id = "LINK", name = "Chainlink", symbol = "LINK"),
    Crypto(id = "NEO", name = "NEO", symbol = "NEO"),
    Crypto(id = "ETC", name = "Ethereum Classic", symbol = "ETC"),
    Crypto(id = "ONT", name = "Ontology", symbol = "ONT"),
    Crypto(id = "CRO", name = "Crypto.com Chain", symbol = "CRO"),
    Crypto(id = "CUC", name = "Cucumber", symbol = "CUC"),
    Crypto(id = "USDC", name = "USD Coin", symbol = "USDC"),
)

val fiatCurrencyList = listOf<Currency>(
    Fiat(id = "SGD", name = "Singapore Dollar", symbol = "$", code = "SGD"),
    Fiat(id = "EUR", name = "Euro", symbol = "€", code = "EUR"),
    Fiat(id = "GBP", name = "British Pound", symbol = "£", code = "GBP"),
    Fiat(id = "HKD", name = "Hong Kong Dollar", symbol = "$", code = "HKD"),
    Fiat(id = "JPY", name = "Japanese Yen", symbol = "¥", code = "JPY"),
    Fiat(id = "AUD", name = "Australian Dollar", symbol = "$", code = "AUD"),
    Fiat(id = "USD", name = "United States Dollar", symbol = "$", code = "USD"),
)