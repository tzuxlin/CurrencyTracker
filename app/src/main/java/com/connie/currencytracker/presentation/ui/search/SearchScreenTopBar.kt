package com.connie.currencytracker.presentation.ui.search

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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.connie.currencytracker.R

@Composable
fun SearchScreenTopBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onTextChanged: (String) -> Unit,
) {
    var text by remember { mutableStateOf("") }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainerHighest)
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back_24),
                contentDescription = stringResource(R.string.back),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        TextField(
            modifier = Modifier.weight(1f),
            value = text,
            onValueChange = {
                text = it
                onTextChanged(text)
            },
            placeholder = { Text(stringResource(R.string.find_a_currency)) },
            colors = TextFieldDefaults.colors().copy(
                focusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            singleLine = true,
        )

        if (text.isNotEmpty()) {
            IconButton(onClick = {
                text = ""
                onTextChanged("")
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close_24),
                    contentDescription = stringResource(R.string.clear),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}