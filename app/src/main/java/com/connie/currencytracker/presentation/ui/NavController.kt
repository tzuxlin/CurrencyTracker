package com.connie.currencytracker.presentation.ui

interface NavController {
    enum class Destination(val path: String) {
        LIST("NAV_DESTINATION_LIST"), SEARCH("NAV_DESTINATION_SEARCH"),
    }

    fun popBackStack()
    fun navigate(destination: Destination)
}