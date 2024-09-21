package com.connie.currencytracker.presentation.model

import android.os.Parcelable
import com.connie.currencytracker.domain.model.Crypto
import com.connie.currencytracker.domain.model.Currency
import com.connie.currencytracker.domain.model.Fiat
import kotlinx.parcelize.Parcelize


@Parcelize
sealed class CurrencyInfo : Parcelable {
    abstract val iconLabel: String
    abstract val name: String
    abstract val symbol: String?

    companion object {
        fun from(currency: Currency): CurrencyInfo {
            return when (currency) {
                is Crypto -> CryptoInfo(
                    iconLabel = currency.name.first().toString(),
                    name = currency.name,
                    symbol = currency.symbol,
                )

                is Fiat -> FiatInfo(
                    iconLabel = currency.name.first().toString(),
                    name = currency.name,
                )
            }
        }
    }

    data class CryptoInfo(
        override val iconLabel: String,
        override val name: String,
        override val symbol: String?,
    ) : CurrencyInfo()

    data class FiatInfo(
        override val iconLabel: String,
        override val name: String,
    ) : CurrencyInfo() {
        override val symbol: String? = null
    }
}
