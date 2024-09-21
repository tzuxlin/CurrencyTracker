package com.connie.currencytracker.model

import com.connie.currencytracker.MainCoroutineRule
import com.connie.currencytracker.domain.model.Crypto
import com.connie.currencytracker.domain.model.Currency
import com.connie.currencytracker.domain.model.Fiat
import com.connie.currencytracker.model.repository.CurrencyRepositoryImpl
import com.connie.currencytracker.model.source.local.CurrencyDao
import com.connie.currencytracker.model.source.local.CurrencyEntity
import com.connie.currencytracker.model.source.remote.CurrencyService
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CurrencyRepositoryImplTest {

    private lateinit var currencyRepository: CurrencyRepositoryImpl

    private val currencyDao: CurrencyDao = mockk()
    private val currencyApiService: CurrencyService = mockk()

    private val localCryptos =
        listOf(CurrencyEntity("BTC", Currency.TYPE_CRYPTO, "Bitcoin", "BTC", null))
    private val localFiats =
        listOf(CurrencyEntity("SGD", Currency.TYPE_FIAT, "Singapore Dollar", "$", "SGD"))
    private val remoteCryptos = listOf(Crypto("BTC", "Bitcoin", "BTC"))
    private val remoteFiats = listOf(Fiat("SGD", "Singapore Dollar", "$", "USD"))


    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        currencyRepository =
            CurrencyRepositoryImpl(currencyDao, currencyApiService, Dispatchers.Unconfined)

    }

    @Test
    fun `getCrypto should return data from local database when available`() = runTest {
        coEvery { currencyDao.getCurrenciesByType(Currency.TYPE_CRYPTO) } returns flowOf(
            localCryptos
        )

        val result = currencyRepository.getCryptos().first()

        assertEquals(localCryptos[0].id, result[0].id)
        coVerify(exactly = 0) { currencyApiService.getCryptoList() }
    }

    @Test
    fun `getFiats should return data from local database when available`() = runTest {
        coEvery { currencyDao.getCurrenciesByType(Currency.TYPE_FIAT) } returns flowOf(localFiats)

        val result = currencyRepository.getFiats().first()

        assertEquals(localFiats[0].id, result[0].id)
        coVerify(exactly = 0) { currencyApiService.getCryptoList() }
        coVerify(exactly = 0) { currencyApiService.getFiatList() }
    }

    @Test
    fun `getAll should return data from local database when available`() = runTest {
        coEvery { currencyDao.getCurrenciesByType(Currency.TYPE_CRYPTO) } returns flowOf(
            localCryptos
        )
        coEvery { currencyDao.getCurrenciesByType(Currency.TYPE_FIAT) } returns flowOf(localFiats)

        val result = currencyRepository.getAll().first()

        assertEquals((localCryptos + localFiats).size, result.size)
        coVerify(exactly = 0) { currencyApiService.getCryptoList() }
        coVerify(exactly = 0) { currencyApiService.getFiatList() }
    }

    @Test
    fun `getCrypto should fetch from network when local database is empty`() = runTest {
        coEvery { currencyDao.getCurrenciesByType(Currency.TYPE_CRYPTO) } returns flowOf(emptyList())
        coEvery { currencyApiService.getCryptoList() } returns flowOf(remoteCryptos)
        coJustRun { currencyDao.insertCurrency(any()) }

        val result = currencyRepository.getCryptos().first()

        assertEquals(remoteCryptos[0].id, result[0].id)
        coVerify { currencyDao.getCurrenciesByType(Currency.TYPE_CRYPTO) }
        coVerify { currencyDao.insertCurrency(any()) }
        coVerify { currencyApiService.getCryptoList() }
    }

    @Test
    fun `getFiats should fetch from network when local database is empty`() = runTest {
        coEvery { currencyDao.getCurrenciesByType(Currency.TYPE_FIAT) } returns flowOf(emptyList())
        coEvery { currencyApiService.getFiatList() } returns flowOf(remoteFiats)
        coJustRun { currencyDao.insertCurrency(any()) }

        val result = currencyRepository.getFiats().first()

        assertEquals(remoteFiats[0].id, result[0].id)
        coVerify { currencyDao.getCurrenciesByType(Currency.TYPE_FIAT) }
        coVerify { currencyDao.insertCurrency(any()) }
        coVerify { currencyApiService.getFiatList() }
    }

    @Test
    fun `getAllCurrencies should fetch from network when local database is empty`() = runTest {
        coEvery { currencyDao.getCurrenciesByType(Currency.TYPE_CRYPTO) } returns flowOf(emptyList())
        coEvery { currencyDao.getCurrenciesByType(Currency.TYPE_FIAT) } returns flowOf(emptyList())
        coEvery { currencyApiService.getCryptoList() } returns flowOf(remoteCryptos)
        coEvery { currencyApiService.getFiatList() } returns flowOf(remoteFiats)
        coJustRun { currencyDao.insertCurrency(any()) }

        val result = currencyRepository.getAll().first()

        assertEquals(2, result.size)
        coVerify { currencyDao.getCurrenciesByType(Currency.TYPE_CRYPTO) }
        coVerify { currencyDao.getCurrenciesByType(Currency.TYPE_FIAT) }
        coVerify(exactly = 2) { currencyDao.insertCurrency(any()) }
        coVerify { currencyApiService.getCryptoList() }
        coVerify { currencyApiService.getFiatList() }
    }

}