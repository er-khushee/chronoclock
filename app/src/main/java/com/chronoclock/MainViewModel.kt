package com.chronoclock

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chronoclock.utils.Constants
import com.chronoclock.utils.formatTime
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private var countDownTimer: CountDownTimer? = null

    private val _time = MutableLiveData(
        Constants.POMODORO_TIME.formatTime()
    )
    val time: LiveData<String> = _time

    private val _progress = MutableLiveData(1f)
    val progress: LiveData<Float> = _progress

    private val _isPlaying = MutableLiveData(false)
    val isPlaying: LiveData<Boolean> = _isPlaying

    fun handleCountDownActions(action: Actions) {
        when (action) {
            Actions.PLAY -> startTimer()
            Actions.PAUSE -> pauseTimer()
            Actions.STOP -> stopTimer()
        }
    }

    fun stopTimer() {
        countDownTimer?.cancel()
        handleTimerValues(false, Constants.POMODORO_TIME.formatTime(), 1.0F)
    }

    private fun pauseTimer() {
        countDownTimer?.cancel()
        handleTimerValues(false, _time.value!!, _progress.value!!)
    }

    private fun startTimer() {
        _isPlaying.value = true
        val timeInMillis = _time.value?.format() ?: Constants.POMODORO_TIME
        val previousProgress = if (_progress.value!! > 0) progress.value!! else 1

        countDownTimer = object : CountDownTimer(
            timeInMillis,
            1000
        ) {
            override fun onTick(millisRemaining: Long) {
                val totalTime = timeInMillis.toFloat() / previousProgress.toFloat()
                val progressValue = millisRemaining.toFloat() / totalTime
                handleTimerValues(true, millisRemaining.formatTime(), progressValue)
            }

            override fun onFinish() {
                handleTimerValues(true, 0L.formatTime(), 0f)
                viewModelScope.launch {
                    delay(1000)
                    stopTimer()
                }
            }
        }.start()
    }

    private fun handleTimerValues(isPlaying: Boolean, text: String, progress: Float) {
        _isPlaying.value = isPlaying
        _time.value = text
        _progress.value = progress
    }

    private fun String.format(): Long {
        val minutes = split(":")[0].toInt()
        val seconds = split(":")[1].toInt()
        return ((minutes * 60 + seconds) * 1000).toLong()
    }

    enum class Actions {
        PLAY,
        PAUSE,
        STOP
    }
}