package com.connie.currencytracker.presentation.model

data class MenuItemInfo(
    val text: String,
    val isEnabled: Boolean = true,
    val onClick: () -> Unit,
)