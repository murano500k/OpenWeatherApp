package com.stc.openweather.composable.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.stc.openweather.R
import com.stc.openweather.util.getPressureDescription
import com.stc.openweather.util.toBarString

@Composable
fun PressureCard(pressure: Int, modifier: Modifier) {
    val pressureDescription = getPressureDescription(LocalContext.current, pressure)

    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
        ) {
            // Title text
            Text(
                text = stringResource(R.string.pressure),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${pressure.toBarString()} Bar",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = pressureDescription,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

        }
    }
}