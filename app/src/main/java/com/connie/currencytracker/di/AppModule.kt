package com.connie.currencytracker.di

import android.app.Application
import com.connie.currencytracker.model.source.local.CurrencyDao
import com.connie.currencytracker.model.source.local.CurrencyDatabase
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("com.connie.currencytracker")
class AppModule {
    @Single
    fun provideDatabase(application: Application): CurrencyDatabase {
        return CurrencyDatabase.getDatabase(application)
    }

    @Single
    fun provideDao(database: CurrencyDatabase): CurrencyDao {
        return database.currencyDao()
    }
}