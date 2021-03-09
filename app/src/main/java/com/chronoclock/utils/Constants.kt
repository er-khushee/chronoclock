package com.chronoclock.utils

import java.util.concurrent.TimeUnit

object Constants {
    const val INITIAL_TIME: Long = 0L
    const val TIMER_FORMAT = "%02d:%02d"
    const val TIME_FORMAT = "%02d"
}

fun Long.formatTime(): String = String.format(
    Constants.TIMER_FORMAT,
    TimeUnit.MILLISECONDS.toMinutes(this),
    TimeUnit.MILLISECONDS.toSeconds(this) % 60
)

fun Int.format(): String = String.format(Constants.TIME_FORMAT, this)
