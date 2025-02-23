package com.example.videoplayer.data.utils

import java.util.Locale
import kotlin.math.round
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.nanoseconds

fun Long.msToDdHhMmSs() = milliseconds.toComponents { days, hours, min, sec, ns ->
    val roundedSec = round(sec + ns.nanoseconds.inWholeMilliseconds/1000.0).toInt()
    when {
        days > 0 -> String.format(Locale.getDefault(), "%dд:%02d:%02d:%02d", days, hours, min, roundedSec)
        hours > 0 -> String.format(Locale.getDefault(), "%d:%02d:%02d", hours, min, roundedSec)
        else -> String.format(Locale.getDefault(), "%d:%02d", min, roundedSec)
    }
}
