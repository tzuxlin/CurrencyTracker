package com.connie.currencytracker.domain.model

import com.connie.currencytracker.domain.model.Currency.Companion.TYPE_CRYPTO
import com.connie.currencytracker.domain.model.Currency.Companion.TYPE_FIAT

sealed interface Currency {
    companion object {
        const val TYPE_CRYPTO = "CRYPTO"
        const val TYPE_FIAT = "FIAT"
    }

    val type: String
    val id: String
    val name: String
    val symbol: String
}

data class Crypto(
    override val id: String,
    override val name: String,
    override val symbol: String,
) : Currency {
    override val type: String = TYPE_CRYPTO
}

data class Fiat(
    override val id: String,
    override val name: String,
    override val symbol: String,
    val code: String,
) : Currency {
    override val type: String = TYPE_FIAT
}