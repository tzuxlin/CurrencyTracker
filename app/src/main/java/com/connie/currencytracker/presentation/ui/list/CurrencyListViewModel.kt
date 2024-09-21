package com.connie.currencytracker.presentation.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.connie.currencytracker.domain.model.Currency
import com.connie.currencytracker.domain.model.State
import com.connie.currencytracker.domain.usecase.CurrencyListUseCase
import com.connie.currencytracker.presentation.model.CurrencyInfo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.InjectedParam


@KoinViewModel
class CurrencyListViewModel(
    @InjectedParam private val listType: ListType,
    private val currencyListUseCase: CurrencyListUseCase,
) : ViewModel() {

    sealed class Message {
        data object SaveSuccess : Message()
        data object SaveFailure : Message()
        data object ClearSuccess : Message()
        data object ClearFailure : Message()

        data class SimulateNavigation(val name: String) : Message()
    }

    data class CurrencyListUiState(
        val isSaveEnabled: Boolean = true,
        val refreshing: Boolean = false,
    )

    private val _currencyList: MutableStateFlow<State<List<Currency>>> =
        MutableStateFlow(State.Loading)

    val currencyInfoListUiState: StateFlow<State<List<CurrencyInfo>>> = _currencyList.map {
        it.map { list -> list.map { currency -> CurrencyInfo.from(currency) } }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(2000),
        State.Loading
    )

    private val _uiState: MutableStateFlow<CurrencyListUiState> =
        MutableStateFlow(CurrencyListUiState())
    val uiState: StateFlow<CurrencyListUiState> = _uiState.asStateFlow()

    private val _message = MutableSharedFlow<Message>()
    val message: SharedFlow<Message> = _message

    init {
        fetchData()
    }

    fun click(item: CurrencyInfo) {
        viewModelScope.launch {
            _message.emit(Message.SimulateNavigation(item.name))
        }
    }

    fun refresh() {
        fetchData(isRefresh = true)
    }

    fun save() {
        _currencyList.value.data?.let { list ->
            viewModelScope.launch {
                _uiState.value = _uiState.value.copy(isSaveEnabled = false)
                if (currencyListUseCase.save(list)) {
                    _message.emit(Message.SaveSuccess)
                } else {
                    _message.emit(Message.SaveFailure)
                }
                _uiState.value = _uiState.value.copy(isSaveEnabled = true)
            }
        }
    }

    fun clear() {
        viewModelScope.launch {
            if (currencyListUseCase.clear()) {
                _message.emit(Message.ClearSuccess)
            } else {
                _message.emit(Message.ClearFailure)
            }
        }
    }

    private fun fetchData(isRefresh: Boolean = false) {
        viewModelScope.launch {
            when (listType) {
                ListType.CRYPTO -> currencyListUseCase.getCryptos()
                ListType.FIAT -> currencyListUseCase.getFiats()
            }.catch {
                _currencyList.value = State.Error(it)
            }.onStart {
                if (isRefresh) {
                    _uiState.value = _uiState.value.copy(refreshing = true)
                } else {
                    _currencyList.value = State.Loading
                }
            }.collect {
                if (isRefresh) {
                    _uiState.value = _uiState.value.copy(refreshing = false)
                }
                _currencyList.value = State.Success(it)
            }
        }
    }
}