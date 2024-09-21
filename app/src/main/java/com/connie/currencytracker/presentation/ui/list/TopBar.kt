package com.connie.currencytracker.presentation.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.connie.currencytracker.R
import com.connie.currencytracker.presentation.model.MenuItemInfo
import com.connie.currencytracker.presentation.ui.component.MoreMenu

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    listType: ListType,
    onSearchClick: () -> Unit,
    moreMenuItems: List<MenuItemInfo>,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainerHighest)
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = onSearchClick) {
            Icon(
                painter = painterResource(id = R.drawable.ic_search_24),
                contentDescription = stringResource(R.string.search),
                tint = MaterialTheme.colorScheme.onTertiaryContainer,
            )
        }
        Text(
            text = listType.name,
            color = MaterialTheme.colorScheme.onTertiaryContainer,
        )

        MoreMenu(
            items = moreMenuItems,
            color = MaterialTheme.colorScheme.onTertiaryContainer,
        )
    }
}