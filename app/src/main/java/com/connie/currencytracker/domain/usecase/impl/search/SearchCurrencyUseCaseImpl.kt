package com.connie.currencytracker.domain.usecase.impl.search

import com.connie.currencytracker.domain.model.Currency
import com.connie.currencytracker.domain.usecase.SearchCurrencyUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.annotation.Factory
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.qualifier.named

@Factory
class SearchCurrencyUseCaseImpl(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
) : SearchCurrencyUseCase, KoinComponent {

    override fun filter(input: String, list: List<Currency>): Flow<List<Currency>> = flow {
        val filteredList = list.filter { currency ->
            get<CurrencyMatchingStrategy<Currency>>(named(currency.type)).matches(currency, input)
        }
        emit(filteredList)
    }.flowOn(defaultDispatcher)
}