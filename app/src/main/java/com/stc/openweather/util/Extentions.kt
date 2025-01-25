package com.stc.openweather.util

import android.content.Context
import android.text.format.DateFormat
import com.stc.openweather.R
import java.util.Date

fun String.capitalizeFirstLetter(): String {
    return this.replaceFirstChar {
        if (it.isLowerCase()) it.uppercase() else it.toString()
    }
}

fun Double.limitToOneDecimal(): String {
    val rounded = kotlin.math.round(this * 10) / 10
    return if (rounded % 1 == 0.0) rounded.toInt().toString() else rounded.toString()
}

fun Int.windDegreesToDirection(context: Context): String {
    val angle = (this % 360 + 360) % 360
    val index = ((angle + 22.5) / 45).toInt() % 8

    return when (index) {
        0 -> context.getString(R.string.direction_north)
        1 -> context.getString(R.string.direction_north_east)
        2 -> context.getString(R.string.direction_east)
        3 -> context.getString(R.string.direction_south_east)
        4 -> context.getString(R.string.direction_south)
        5 -> context.getString(R.string.direction_south_west)
        6 -> context.getString(R.string.direction_west)
        7 -> context.getString(R.string.direction_north_west)
        else -> context.getString(R.string.direction_north)
    }
}

fun getUviCategory(context: Context, uvi: Double): String {
    return when {
        uvi < 3 -> context.getString(R.string.uvi_low)
        uvi < 6 -> context.getString(R.string.uvi_moderate)
        uvi < 8 -> context.getString(R.string.uvi_high)
        uvi < 11 -> context.getString(R.string.uvi_very_high)
        else -> context.getString(R.string.uvi_extreme)
    }
}

fun getUviAdvice(context: Context, uvi: Double): String {
    return when (getUviCategory(context, uvi)) {
        context.getString(R.string.uvi_low) -> context.getString(R.string.advice_low)
        context.getString(R.string.uvi_moderate) -> context.getString(R.string.advice_moderate)
        context.getString(R.string.uvi_high) -> context.getString(R.string.advice_high)
        context.getString(R.string.uvi_very_high) -> context.getString(R.string.advice_very_high)
        context.getString(R.string.uvi_extreme) -> context.getString(R.string.advice_extreme)
        else -> context.getString(R.string.advice_default)
    }
}

fun getWindDescription(context: Context, windSpeed: Double): String {
    return when {
        windSpeed < 1.5 -> context.getString(R.string.wind_calm)
        windSpeed < 3.3 -> context.getString(R.string.wind_light)
        windSpeed < 5.5 -> context.getString(R.string.wind_gentle)
        windSpeed < 7.9 -> context.getString(R.string.wind_moderate)
        windSpeed < 10.8 -> context.getString(R.string.wind_fresh)
        windSpeed < 13.9 -> context.getString(R.string.wind_strong)
        windSpeed < 17.2 -> context.getString(R.string.wind_very_strong)
        else -> context.getString(R.string.wind_stormy)
    }
}

fun getPressureDescription(context: Context, pressure: Int): String {
    return when {
        pressure < 1000 -> context.getString(R.string.pressure_low)
        pressure < 1013 -> context.getString(R.string.pressure_slightly_low)
        pressure < 1022 -> context.getString(R.string.pressure_normal)
        pressure < 1030 -> context.getString(R.string.pressure_high)
        else -> context.getString(R.string.pressure_very_high)
    }
}

fun Context.formatLocalTime(time: Long): String {
    val formattedTime = DateFormat.getTimeFormat(this).format(Date(time))
    return if (formattedTime.contains("AM") || formattedTime.contains("PM")) {
        formattedTime.replace(":00", "") // Remove ":00" if AM/PM is present
    } else {
        formattedTime // Keep the original format for 24-hour time
    }
}


fun Int.toBarString(): String {
    val pressureBar = this / 1000.0 // Convert hPa to bar
    return String.format("%.2f", pressureBar) // Format to 2 decimal places
}