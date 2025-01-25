package com.stc.openweatherapp.composable.daily

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.stc.openweatherapp.R
import com.stc.openweatherapp.model.DailyWeather
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun DaySelectorItem(
    index: Int,
    daily: DailyWeather,
    isSelected: Boolean,
    onDaySelected: (Int) -> Unit
) {
    val dayFormatter = SimpleDateFormat("EEE", Locale.getDefault())
    val dayLabel = if (index == 0) {
        stringResource(R.string.today)
    } else {
        dayFormatter.format(Date(daily.dt * 1000))
    }

    val dayTemp = daily.temp.day.roundToInt()
    val nightTemp = daily.temp.night.roundToInt()

    val iconUrl = daily.weather.firstOrNull()?.icon?.let { iconId ->
        "https://openweathermap.org/img/wn/$iconId@2x.png"
    }

    Card(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .clip(CardDefaults.shape)
            .clickable { onDaySelected(index) },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.tertiaryContainer
            else MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .width(60.dp)
        ) {
            // Day label
            Text(
                text = dayLabel,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = if (isSelected) MaterialTheme.colorScheme.onTertiaryContainer
                else MaterialTheme.colorScheme.onPrimaryContainer
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Weather icon
            iconUrl?.let {
                AsyncImage(
                    model = it,
                    contentDescription = "Weather Icon",
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Day and Night temperatures
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$dayTemp°/",
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer
                    else MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "$nightTemp°",
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isSelected) MaterialTheme.colorScheme.onSecondaryContainer
                    else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}