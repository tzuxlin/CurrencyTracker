package com.connie.currencytracker.model.repository

import com.connie.currencytracker.domain.model.Crypto
import com.connie.currencytracker.domain.model.Currency
import com.connie.currencytracker.domain.model.Fiat
import com.connie.currencytracker.domain.repository.CurrencyRepository
import com.connie.currencytracker.model.source.local.CurrencyDao
import com.connie.currencytracker.model.source.local.CurrencyEntity
import com.connie.currencytracker.model.source.remote.CurrencyService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory

@Factory
class CurrencyRepositoryImpl(
    private val currencyDao: CurrencyDao,
    private val currencyService: CurrencyService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : CurrencyRepository {

    override fun getCryptos(): Flow<List<Currency>> = flow {
        val localData = runCatching {
            currencyDao.getCurrenciesByType(Currency.TYPE_CRYPTO).firstOrNull()?.map {
                Crypto(
                    id = it.id,
                    name = it.name,
                    symbol = it.symbol,
                )
            }
        }.getOrNull()

        if (localData?.isNotEmpty() == true) {
            emit(localData)
        } else {
            emitAll(currencyService.getCryptoList().onEach { save(it) })
        }
    }.flowOn(ioDispatcher)

    override fun getFiats(): Flow<List<Currency>> = flow {
        val localData = runCatching {
            currencyDao.getCurrenciesByType(Currency.TYPE_FIAT).firstOrNull()?.map {
                Fiat(
                    id = it.id,
                    name = it.name,
                    symbol = it.symbol,
                    code = it.code!!
                )
            }
        }.getOrNull()

        if (localData?.isNotEmpty() == true) {
            emit(localData)
        } else {
            emitAll(currencyService.getFiatList().onEach { save(it) })
        }
    }.flowOn(ioDispatcher)

    override fun getAll(): Flow<List<Currency>> =
        combine(getCryptos(), getFiats()) { cryptos, fiats -> cryptos + fiats }

    override suspend fun save(list: List<Currency>) {
        withContext(ioDispatcher) {
            list.forEach { currencyDao.insertCurrency(mapToEntity(it)) }
        }
    }

    override suspend fun clear() {
        withContext(ioDispatcher) {
            currencyDao.clearCurrencies()
        }
    }

    private fun mapToEntity(currency: Currency): CurrencyEntity {
        return when (currency) {
            is Crypto -> CurrencyEntity(
                id = currency.id,
                name = currency.name,
                symbol = currency.symbol,
                type = currency.type,
                code = null,
            )

            is Fiat -> CurrencyEntity(
                id = currency.id,
                name = currency.name,
                symbol = currency.symbol,
                type = currency.type,
                code = currency.code,
            )
        }
    }
}