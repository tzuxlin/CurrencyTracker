# CurrencyTracker
CurrencyTracker is an Android application that helps users track different currencies including both Crypto and Fiat currencies. This project demonstrates the usage of `Jetpack Compose`, `Kotlin Coroutines`, and `Room`, as well as `MVVM` architecture and `Clean Architecture` principles.

## Features
#### Currency Display
- View a list of both Crypto and Fiat currencies.
- Switch between the two lists using the navigation bar.
- Items are clickable.
#### Save or Clear to Database
- Save or clear currency data using the menu options.
#### Swipe-to-Refresh
- Refresh the currency data by swiping down.
- A mock network fetch simulates a 1-second delay, while data from the database is returned instantly. You can also clear the database and refresh the data to observe behavior.
#### Search and Filter
- Use the search bar to filter currencies by name or symbol.