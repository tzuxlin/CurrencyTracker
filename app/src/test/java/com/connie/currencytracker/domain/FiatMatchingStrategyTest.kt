package com.connie.currencytracker.domain

import com.connie.currencytracker.domain.model.Fiat
import com.connie.currencytracker.domain.usecase.impl.search.FiatMatchingStrategy
import org.junit.Assert.*
import org.junit.Test

class FiatMatchingStrategyTest {

    private val fiatMatchingStrategy = FiatMatchingStrategy()

    @Test
    fun `test fiat matches name starts with query`() {
        val fiat = Fiat(id = "SGD", name = "Singapore Dollar", symbol = "$", code = "SGD")
        val query = "sing"

        val result = fiatMatchingStrategy.matches(fiat, query)
        assertTrue(result)
    }

    @Test
    fun `test fiat matches name with space before query`() {
        val fiat = Fiat(id = "SGD", name = "Singapore Dollar", symbol = "$", code = "SGD")
        val query = "dollar"

        val result = fiatMatchingStrategy.matches(fiat, query)
        assertTrue(result)
    }

    @Test
    fun `test fiat does not match without space before query`() {
        val fiat = Fiat(id = "SGD", name = "Singapore Dollar", symbol = "$", code = "SGD")
        val query = "ing"

        val result = fiatMatchingStrategy.matches(fiat, query)
        assertFalse(result)
    }

    @Test
    fun `test fiat does not match with symbol`() {
        val fiat = Fiat(id = "SGD", name = "Singapore Dollar", symbol = "$", code = "SGD")
        val query = "$"

        val result = fiatMatchingStrategy.matches(fiat, query)
        assertFalse(result)
    }

    @Test
    fun `test fiat does not match with code`() {
        val fiat = Fiat(id = "SGD", name = "Singapore Dollar", symbol = "$", code = "SGD")
        val query = "SGD"

        val result = fiatMatchingStrategy.matches(fiat, query)
        assertFalse(result)
    }
}