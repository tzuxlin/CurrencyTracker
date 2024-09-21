package com.connie.currencytracker.model.source.remote

import com.connie.currencytracker.domain.model.Currency
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class MockCurrencyService : CurrencyService {
    override fun getCryptoList(): Flow<List<Currency>> = flow {
        delay(1000)
        emit(cryptoCurrencyList)
    }

    override fun getFiatList(): Flow<List<Currency>> = flow {
        delay(1000)
        emit(fiatCurrencyList)
    }
}