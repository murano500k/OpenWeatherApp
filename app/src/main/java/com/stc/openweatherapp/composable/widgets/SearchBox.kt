package com.stc.openweatherapp.composable.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction

@Composable
fun SearchBox(
    query: String,
    onQueryChanged: (String) -> Unit,
    onSearch: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = query,
        onValueChange = onQueryChanged,
        label = { Text("Search for a location") },
        modifier = Modifier
            .fillMaxWidth(),
        placeholder = { Text("Search location") },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Search Icon")
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch()
                keyboardController?.hide()
            }
        )
    )
}
