package com.connie.currencytracker.presentation.ui.list

import android.content.Context
import com.connie.currencytracker.R
import org.koin.core.annotation.Single

@Single
class CurrencyListMessageFormatter {
    fun format(context: Context, message: CurrencyListViewModel.Message): String {
        return when (message) {
            CurrencyListViewModel.Message.ClearFailure ->
                context.getString(R.string.clear_failure)

            CurrencyListViewModel.Message.ClearSuccess ->
                context.getString(R.string.clear_success)

            CurrencyListViewModel.Message.SaveFailure ->
                context.getString(R.string.save_failure)

            CurrencyListViewModel.Message.SaveSuccess ->
                context.getString(R.string.save_success)

            is CurrencyListViewModel.Message.SimulateNavigation ->
                context.getString(R.string.navigate_detail, message.name)
        }
    }
}