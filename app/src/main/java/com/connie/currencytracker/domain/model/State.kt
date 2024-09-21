package com.connie.currencytracker.domain.model

sealed class State<out T> {

    abstract val data: T?

    data object Loading : State<Nothing>() {
        override val data = null
    }

    data class Success<T>(override val data: T) : State<T>()
    data class Error(val throwable: Throwable) : State<Nothing>() {
        override val data = null
    }

    fun <R> map(transform: (T) -> R) = when (this) {
        is Success -> Success(transform(data))
        is Loading -> Loading
        is Error -> Error(throwable)
    }
}