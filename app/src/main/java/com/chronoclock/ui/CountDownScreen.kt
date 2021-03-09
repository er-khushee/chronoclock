package com.chronoclock.ui

import CountDownInput
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chronoclock.MainViewModel
import com.chronoclock.R
import com.chronoclock.animation.updateTransitionData
import com.chronoclock.components.CountDownButton
import com.chronoclock.components.CountDownIndicator
import com.chronoclock.utils.Constants
import com.chronoclock.utils.formatTime

@ExperimentalAnimationApi
@Composable
fun CountDownView(viewModel: MainViewModel = viewModel()) {
    val time by viewModel.time.observeAsState(Constants.INITIAL_TIME.formatTime())
    val progress by viewModel.progress.observeAsState(0f)
    val isPlaying by viewModel.isPlaying.observeAsState(false)

    CountDownView(time = time,
        progress = progress,
        isPlaying = isPlaying,
        updateTime = { viewModel.updateTime(it) },
        countDownAction = { viewModel.handleCountDownActions(it) })
}

@ExperimentalAnimationApi
@Composable
fun CountDownView(
    time: String,
    progress: Float,
    isPlaying: Boolean,
    updateTime: (T: String) -> Unit,
    countDownAction: (T: MainViewModel.Actions) -> Unit
) {
    val transition = updateTransitionData(progress = progress)
    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(id = R.drawable.ic_bubbles),
        contentDescription = null,
        alpha = 0.1f,
        contentScale = ContentScale.Fit,
        colorFilter = ColorFilter.tint(transition.color)
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            modifier = Modifier.padding(40.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.subtitle2,
            color = MaterialTheme.colors.primary,
            text = stringResource(R.string.count_down_screen_intro_text)
        )

        AnimatedVisibility(visible = isPlaying.not()) {
            CountDownInput(modifier = Modifier.size(36.dp),
                time = time,
                onTimeUpdated = { updateTime(it) })
        }

        AnimatedVisibility(visible = isPlaying) {
            CountDownIndicator(
                Modifier.padding(20.dp),
                progress = progress,
                time = time,
                size = 180,
                stroke = 6
            )
        }

        Box(
            modifier = Modifier
                .padding(40.dp)
        ) {
            CountDownButton(
                modifier = Modifier
                    .size(70.dp),
                isPlaying = isPlaying
            ) {
                countDownAction(it)
            }
        }
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun CountDownScreen() {
    CountDownView()
}