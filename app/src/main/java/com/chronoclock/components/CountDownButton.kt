package com.chronoclock.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.chronoclock.MainViewModel


@ExperimentalAnimationApi
@Composable
fun CountDownButton(
    modifier: Modifier = Modifier,
    isPlaying: Boolean,
    optionSelected: (MainViewModel.Actions) -> Unit
) {
    AnimatedVisibility(
        visible = isPlaying.not(),
        enter = fadeIn(
            initialAlpha = 0.5f,
            animationSpec = tween(durationMillis = 500, easing = LinearEasing)
        ),
        exit = fadeOut()
    ) {
        IconButton(
            onClick = { optionSelected(MainViewModel.Actions.PLAY) },
            modifier = modifier
                .shadow(8.dp, CircleShape)
                .background(
                    Brush.verticalGradient(
                        listOf(MaterialTheme.colors.primary, MaterialTheme.colors.primaryVariant)
                    ), shape = CircleShape
                )
        ) {
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = null,
                tint = Color.White
            )
        }
    }

    AnimatedVisibility(
        visible = isPlaying,
        enter = fadeIn(initialAlpha = 0.5f),
        exit = fadeOut()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .weight(0.5f)
                    .wrapContentSize(Alignment.BottomStart)
            ) {
                ActionButton(
                    modifier = Modifier,
                    action = MainViewModel.Actions.STOP,
                    optionSelected = optionSelected
                )
            }

            Box(
                modifier = Modifier
                    .weight(0.5f)
                    .wrapContentSize(Alignment.BottomEnd)
            ) {
                ActionButton(
                    modifier = Modifier,
                    action = MainViewModel.Actions.PAUSE,
                    optionSelected = optionSelected
                )
            }
        }

    }
}

@Composable
fun ActionButton(
    modifier: Modifier,
    action: MainViewModel.Actions,
    optionSelected: (MainViewModel.Actions) -> Unit
) {
    IconButton(
        onClick = { optionSelected(action) },
        modifier = modifier
            .shadow(8.dp, CircleShape)
            .background(MaterialTheme.colors.primary)
    ) {
        Icon(
            imageVector = if (action == MainViewModel.Actions.STOP) Icons.Filled.Stop
            else Icons.Filled.Pause,
            contentDescription = null,
            tint = Color.White
        )
    }
}
