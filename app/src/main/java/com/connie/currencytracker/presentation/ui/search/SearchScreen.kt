package com.connie.currencytracker.presentation.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.connie.currencytracker.R
import com.connie.currencytracker.domain.model.State
import com.connie.currencytracker.presentation.ui.NavController
import com.connie.currencytracker.presentation.ui.component.CurrencyItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: CurrencySearchViewModel = koinViewModel<CurrencySearchViewModel>(),
) {
    val filteredListUiState by viewModel.filteredListUiState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SearchScreenTopBar(
                onBackClick = { navController.popBackStack() },
                onTextChanged = { viewModel.search(it) },
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .padding(paddingValues),
            contentAlignment = Alignment.TopCenter,
        ) {
            when (val state = filteredListUiState) {
                is State.Loading -> CircularProgressIndicator(modifier = Modifier.size(48.dp))
                is State.Error -> Text(text = state.data ?: stringResource(id = R.string.unknown_error))
                is State.Success -> {
                    if (state.data.isEmpty()) {
                        Column(
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Spacer(modifier = Modifier.size(100.dp))
                            Icon(
                                modifier = Modifier.size(48.dp),
                                painter = painterResource(id = R.drawable.ic_search_24),
                                contentDescription = stringResource(R.string.not_found)
                            )
                            Text(text = stringResource(R.string.no_results_try_bit))
                        }
                    } else {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(state.data.size) { index ->
                                CurrencyItem(state.data[index])
                            }
                        }
                    }
                }
            }
        }
    }
}