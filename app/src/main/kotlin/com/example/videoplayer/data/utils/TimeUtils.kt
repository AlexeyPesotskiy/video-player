package com.example.videoplayer.data.utils

import java.util.Locale

fun String.formatDuration(): String {
    val regex = Regex("""P(?:(\d+)D)?(?:T(?:(\d+)H)?(?:(\d+)M)?(?:(\d+)S)?)?""")
    val matchResult = regex.matchEntire(this) ?: return this

    val days = matchResult.groupValues[1].toIntOrNull() ?: 0
    val hours = matchResult.groupValues[2].toIntOrNull() ?: 0
    val minutes = matchResult.groupValues[3].toIntOrNull() ?: 0
    val seconds = matchResult.groupValues[4].toIntOrNull() ?: 0

    val locale = Locale.getDefault()
    return when {
        days > 0 -> String.format(locale, "%dд%02d:%02d:%02d", days, hours, minutes, seconds)
        hours > 0 -> String.format(locale, "%02d:%02d:%02d", hours, minutes, seconds)
        minutes > 0 -> String.format(locale, "%02d:%02d", minutes, seconds)
        else -> String.format(locale, "%02d", seconds)
    }
}