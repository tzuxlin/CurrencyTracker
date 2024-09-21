package com.connie.currencytracker.presentation.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.connie.currencytracker.R
import com.connie.currencytracker.presentation.model.MenuItemInfo

@Composable
fun MoreMenu(items: List<MenuItemInfo>, color: Color) {
    val isMenuExpanded = remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { isMenuExpanded.value = !isMenuExpanded.value }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_more_24),
                contentDescription = stringResource(R.string.more),
                tint = color,
            )
        }

        DropdownMenu(
            expanded = isMenuExpanded.value,
            onDismissRequest = { isMenuExpanded.value = false }
        ) {
            for (item in items) {
                DropdownMenuItem(
                    text = { Text(item.text) },
                    onClick = {
                        item.onClick()
                        isMenuExpanded.value = !isMenuExpanded.value
                    },
                    enabled = item.isEnabled,
                )
            }
        }
    }
}