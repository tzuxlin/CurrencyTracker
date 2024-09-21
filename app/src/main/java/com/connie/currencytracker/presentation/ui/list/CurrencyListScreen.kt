package com.connie.currencytracker.presentation.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.connie.currencytracker.R
import com.connie.currencytracker.domain.model.State
import com.connie.currencytracker.presentation.ui.component.CurrencyItem
import com.connie.currencytracker.presentation.model.MenuItemInfo
import com.connie.currencytracker.presentation.ui.NavController
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CurrencyListScreen(
    navigationController: NavController,
    listType: ListType,
    viewModel: CurrencyListViewModel =
        koinViewModel<CurrencyListViewModel>(parameters = { parametersOf(listType) }),
    messageFormatter: CurrencyListMessageFormatter = koinInject(),
) {
    val context = LocalContext.current

    val snackbarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsState()
    val refreshState = rememberPullRefreshState(uiState.refreshing, onRefresh = { viewModel.refresh() })
    val currencyListUiState by viewModel.currencyInfoListUiState.collectAsState()
    val message by viewModel.message.collectAsState(initial = null)

    LaunchedEffect(message) {
        message?.let {
            val text = messageFormatter.format(context, it)
            snackbarHostState.showSnackbar(text)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                listType = listType,
                onSearchClick = { navigationController.navigate(NavController.Destination.SEARCH) },
                moreMenuItems = listOf(
                    MenuItemInfo(
                        stringResource(R.string.save_current_list_to_database),
                        isEnabled = uiState.isSaveEnabled
                    ) { viewModel.save() },
                    MenuItemInfo(stringResource(R.string.clear_database)) { viewModel.clear() },
                ),
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .padding(top = paddingValues.calculateTopPadding()),
            contentAlignment = Alignment.Center,
        ) {
            when (val state = currencyListUiState) {
                is State.Loading -> CircularProgressIndicator(modifier = Modifier.size(48.dp))
                is State.Error -> Text(
                    text = state.throwable.message ?: stringResource(R.string.unknown_error)
                )

                is State.Success -> {
                    if (state.data.isEmpty()) {
                        Text(text = stringResource(R.string.no_currencies_available))
                    } else {
                        Box(Modifier.pullRefresh(refreshState)) {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(state.data.size) { index ->
                                    CurrencyItem(state.data[index]) {
                                        viewModel.click(state.data[index])
                                    }
                                }
                            }
                            PullRefreshIndicator(
                                refreshing = uiState.refreshing,
                                state = refreshState,
                                Modifier.align(Alignment.TopCenter),
                            )
                        }
                    }
                }
            }
        }
    }
}