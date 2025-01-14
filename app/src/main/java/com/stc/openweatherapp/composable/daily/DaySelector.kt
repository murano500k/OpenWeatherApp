package com.stc.openweatherapp.composable.daily

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun DaySelector(
    dailyWeather: List<DailyWeather>,
    selectedDayIndex: Int,
    onDaySelected: (Int) -> Unit
) {
    val dayFormatter = SimpleDateFormat("EEE", Locale.getDefault())
    val greyBackground = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f)


    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        itemsIndexed(dailyWeather) { index, daily ->
            val isSelected = index == selectedDayIndex

            // Day label (e.g., Today, Thursday)
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

            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                        else greyBackground
                    ) // Apply grey background
                    .border(
                        width = if (isSelected) 2.dp else 1.dp,
                        color = if (isSelected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable { onDaySelected(index) }
                    .padding(4.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Day label
                    Text(
                        text = dayLabel,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        color = if (isSelected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurface
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
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "$nightTemp°",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }
        }
    }
}