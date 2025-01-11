package com.stc.openweatherapp.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun DaySelector(
    dayCount: Int,
    selectedDayIndex: Int,
    onDaySelected: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    // We can label each day "Day 1", "Day 2", etc.
    val selectedLabel = "Day ${selectedDayIndex + 1}"

    Box {
        // The "anchor" or "trigger" for the dropdown
        TextButton(onClick = { expanded = true }) {
            Text(text = selectedLabel)
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Select Day"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            (0 until dayCount).forEach { index ->
                val label = "Day ${index + 1}"
                DropdownMenuItem(
                    text = { Text(label) },
                    onClick = {
                        onDaySelected(index)
                        expanded = false
                    }
                )
            }
        }
    }
}