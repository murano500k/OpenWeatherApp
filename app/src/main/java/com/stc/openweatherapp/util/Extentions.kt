package com.stc.openweatherapp.util

fun String.capitalizeFirstLetter(): String {
    return this.replaceFirstChar {
        if (it.isLowerCase()) it.uppercase() else it.toString()
    }
}

fun Double.limitToOneDecimal(): String {
    val rounded = kotlin.math.round(this * 10) / 10
    return if (rounded % 1 == 0.0) rounded.toInt().toString() else rounded.toString()
}

fun Int.windDegreesToDirection(): String {
    // Normalize angle to 0..359
    val angle = (this % 360 + 360) % 360

    // Each direction covers 45°
    // Shift by 22.5° so the boundary is in the middle of segments
    val index = ((angle + 22.5) / 45).toInt() % 8

    return when (index) {
        0 -> "North"
        1 -> "North-East"
        2 -> "East"
        3 -> "South-East"
        4 -> "South"
        5 -> "South-West"
        6 -> "West"
        7 -> "North-West"
        else -> "North"
    }
}

/**
 * Maps the UV index to a risk category label (e.g., "Moderate", "High").
 */
fun getUviCategory(uvi: Double): String {
    return when {
        uvi < 3 -> "Low"
        uvi < 6 -> "Moderate"
        uvi < 8 -> "High"
        uvi < 11 -> "Very High"
        else -> "Extreme"
    }
}

/**
 * Provides a short safety tip or message based on the UV category.
 */
fun getUviAdvice(uvi: Double): String {
    return when (getUviCategory(uvi)) {
        "Low" -> "Minimal protection required."
        "Moderate" -> "Seek shade during midday hours, use SPF 15+."
        "High" -> "Cover up, use SPF 30+, and limit midday sun."
        "Very High" -> "Extra protection needed! Hat, sunglasses, and SPF 30+."
        "Extreme" -> "Take all precautions. Avoid midday sun if possible."
        else -> "Stay safe in the sun!"
    }
}

fun getWindDescription(windSpeed: Double): String {
    return when {
        windSpeed < 1.5 -> "Calm"
        windSpeed < 3.3 -> "Light"
        windSpeed < 5.5 -> "Gentle"
        windSpeed < 7.9 -> "Moderate"
        windSpeed < 10.8 -> "Fresh"
        windSpeed < 13.9 -> "Strong"
        windSpeed < 17.2 -> "Very Strong"
        else -> "Stormy"
    }
}

fun getPressureDescription(pressure: Int): String {
    return when {
        pressure < 1000 -> "Low"
        pressure < 1013 -> "Slightly Low"
        pressure < 1022 -> "Normal"
        pressure < 1030 -> "High"
        else -> "Very High"
    }
}