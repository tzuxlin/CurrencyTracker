package com.connie.currencytracker.presentation.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.connie.currencytracker.domain.model.Currency
import com.connie.currencytracker.domain.model.State
import com.connie.currencytracker.domain.usecase.CurrencyListUseCase
import com.connie.currencytracker.domain.usecase.SearchCurrencyUseCase
import com.connie.currencytracker.presentation.model.CurrencyInfo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import kotlin.time.Duration.Companion.milliseconds

@ExperimentalCoroutinesApi
@FlowPreview
@KoinViewModel
class CurrencySearchViewModel(
    private val currencyListUseCase: CurrencyListUseCase,
    private val searchCurrencyUseCase: SearchCurrencyUseCase,
) : ViewModel() {

    private val _filteredListUiState =
        MutableStateFlow<State<List<Currency>>>(State.Success(emptyList()))
    val filteredListUiState: StateFlow<State<List<CurrencyInfo>>> = _filteredListUiState.map {
        it.map { list -> list.map { currency -> CurrencyInfo.from(currency) } }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(2000),
        initialValue = State.Loading,
    )

    private val query = MutableSharedFlow<String>()

    init {
        viewModelScope.launch {
            query
                .debounce(500.milliseconds)
                .flatMapLatest { query ->
                    if (query.isBlank()) {
                        flowOf(emptyList())
                    } else {
                        currencyListUseCase.getAll().flatMapConcat { list ->
                            searchCurrencyUseCase.filter(input = query, list = list)
                        }
                    }
                }.catch {
                    _filteredListUiState.value = State.Error(it)
                }.collect {
                    _filteredListUiState.value = State.Success(it)
                }
        }
    }

    fun search(query: String) {
        viewModelScope.launch {
            this@CurrencySearchViewModel.query.emit(query)
        }
    }

}