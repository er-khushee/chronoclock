package com.chronoclock.utils

import java.util.concurrent.TimeUnit

object Constants {
    const val POMODORO_TIME: Long = 50000L
    const val TIMER_FORMAT = "%02d:%02d"
}

fun Long.formatTime(): String = String.format(
    Constants.TIMER_FORMAT,
    TimeUnit.MILLISECONDS.toMinutes(this),
    TimeUnit.MILLISECONDS.toSeconds(this) % 60
)

enum class TimerState {
    PLAY, PAUSE, STOP
}