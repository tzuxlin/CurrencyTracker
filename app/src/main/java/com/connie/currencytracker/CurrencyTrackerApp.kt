package com.connie.currencytracker

import android.app.Application
import com.connie.currencytracker.di.AppModule
import com.connie.currencytracker.model.source.local.CurrencyDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

class CurrencyTrackerApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@CurrencyTrackerApp)
            modules(AppModule().module)
        }

        CurrencyDatabase.getDatabase(this)
    }
}