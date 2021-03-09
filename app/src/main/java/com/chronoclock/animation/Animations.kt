package com.chronoclock.animation

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import kotlin.random.Random

// Holds the animation values.
class TransitionData(
    color: State<Color>,
    size: State<Float>
) {
    val color by color
    val progress by size
}

// Create a Transition and return its animation values.
@Composable
fun updateTransitionData(progress: Float): TransitionData {
    val transition = updateTransition(progress)
    fun <T> animSpec() = tween<T>(
        durationMillis = 1000,
        easing = LinearEasing
    )

    val color = transition.animateColor(transitionSpec = { animSpec() }) { pro ->
        val random = Random((pro * 1000).toInt())
        Color(random.nextInt(256), random.nextInt(256), random.nextInt(256), 255)
    }
    val animatedProgress = transition.animateFloat(transitionSpec = { animSpec() }) { pro ->
        pro
    }
    return remember(transition) { TransitionData(color, animatedProgress) }
}
