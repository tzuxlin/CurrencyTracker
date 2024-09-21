package com.connie.currencytracker.presentation.ext

import android.os.Build
import android.os.Build.VERSION
import android.os.Bundle
import android.os.Parcelable

inline fun <reified T : Parcelable> Bundle.getParcelableCompat(key: String): T? =
    when {
        VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getParcelable(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelable(key) as? T
    }