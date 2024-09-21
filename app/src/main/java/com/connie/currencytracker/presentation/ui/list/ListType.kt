package com.connie.currencytracker.presentation.ui.list

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class ListType : Parcelable {
    CRYPTO, FIAT
}