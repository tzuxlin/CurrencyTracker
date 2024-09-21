package com.connie.currencytracker.domain

import com.connie.currencytracker.domain.model.Crypto
import com.connie.currencytracker.domain.usecase.impl.search.CryptoMatchingStrategy
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class CryptoMatchingStrategyTest {

    private val cryptoMatchingStrategy = CryptoMatchingStrategy()

    @Test
    fun `test crypto matches name starts with query`() {
        val crypto = Crypto(id = "BTC", name = "Bitcoin", symbol = "BTC")
        val query = "bit"

        val result = cryptoMatchingStrategy.matches(crypto, query)
        assertTrue(result)
    }

    @Test
    fun `test crypto matches name with space before query`() {
        val crypto = Crypto(id = "ETC", name = "Ethereum Classic", symbol = "ETC")
        val query = "classic"

        val result = cryptoMatchingStrategy.matches(crypto, query)
        assertTrue(result)
    }

    @Test
    fun `test crypto matches symbol starts with query`() {
        val crypto = Crypto(id = "ETH", name = "Ethereum", symbol = "ETH")
        val query = "et"

        val result = cryptoMatchingStrategy.matches(crypto, query)
        assertTrue(result)
    }

    @Test
    fun `test crypto does not matches symbol without space before query`() {
        val crypto = Crypto(id = "ETH", name = "Ethereum", symbol = "ETH")
        val query = "th"

        val result = cryptoMatchingStrategy.matches(crypto, query)
        assertFalse(result)
    }

    @Test
    fun `test crypto does not match`() {
        val crypto = Crypto(id = "ETH", name = "Ethereum", symbol = "ETH")
        val query = "btc"

        val result = cryptoMatchingStrategy.matches(crypto, query)
        assertFalse(result)
    }
}