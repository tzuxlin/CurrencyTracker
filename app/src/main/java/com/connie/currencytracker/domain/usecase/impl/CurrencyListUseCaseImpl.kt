package com.connie.currencytracker.domain.usecase.impl

import com.connie.currencytracker.domain.model.Currency
import com.connie.currencytracker.domain.repository.CurrencyRepository
import com.connie.currencytracker.domain.usecase.CurrencyListUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Single

@Single
class CurrencyListUseCaseImpl(
    private val currencyRepository: CurrencyRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
) : CurrencyListUseCase {

    override fun getCryptos(): Flow<List<Currency>> {
        return currencyRepository.getCryptos()
            .map { list -> list.sortedBy { it.name } }
            .flowOn(defaultDispatcher)
    }

    override fun getFiats(): Flow<List<Currency>> {
        return currencyRepository.getFiats()
            .map { list -> list.sortedBy { it.name } }
            .flowOn(defaultDispatcher)
    }

    override fun getAll(): Flow<List<Currency>> {
        return currencyRepository.getAll()
            .map { list -> list.sortedBy { it.name } }
            .flowOn(defaultDispatcher)
    }

    override suspend fun save(list: List<Currency>): Boolean {
        return runCatching { currencyRepository.save(list) }.isSuccess
    }

    override suspend fun clear(): Boolean {
        return runCatching { currencyRepository.clear() }.isSuccess
    }
}