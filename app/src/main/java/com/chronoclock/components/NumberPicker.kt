package com.chronoclock.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun NumberPicker(
    state: MutableState<Int>,
    modifier: Modifier = Modifier,
    range: IntRange? = null,
    textStyle: TextStyle = LocalTextStyle.current,
) {

    val coroutineScope = rememberCoroutineScope()
    val numbersColumnHeight = 48.dp
    val halvedNumbersColumnHeight = numbersColumnHeight / 2
    val halvedNumbersColumnHeightPx =
        with(LocalDensity.current) { halvedNumbersColumnHeight.toPx() }

    fun animatedStateValue(offset: Float): Int =
        state.value - (offset / halvedNumbersColumnHeightPx).toInt()

    val animatedOffset = remember { Animatable(0f) }.apply {
        if (range != null) {
            val offsetRange = remember(state.value, range) {
                val value = state.value
                val first = -(range.last - value) * halvedNumbersColumnHeightPx
                val last = -(range.first - value) * halvedNumbersColumnHeightPx
                first..last
            }
            updateBounds(offsetRange.start, offsetRange.endInclusive)
        }
    }
    val coercedAnimatedOffset = animatedOffset.value % halvedNumbersColumnHeightPx
    val animatedStateValue = animatedStateValue(animatedOffset.value)

    Column(
        modifier = modifier
            .wrapContentSize()
            .draggable(
                state = DraggableState { },
                orientation = Orientation.Vertical,
                onDragStarted = { deltaY ->
                    coroutineScope.launch {
                        animatedOffset.snapTo(animatedOffset.value + deltaY.y)
                    }
                },
                onDragStopped = { velocity ->
                    /*coroutineScope.launch {
                        val flingConfig = FlingConfig(
                            decayAnimation = FloatExponentialDecaySpec(
                                frictionMultiplier = 20f
                            ),
                            adjustTarget = { target ->
                                val coercedTarget = target % halvedNumbersColumnHeightPx
                                val coercedAnchors = listOf(
                                    -halvedNumbersColumnHeightPx,
                                    0f,
                                    halvedNumbersColumnHeightPx
                                )
                                val coercedPoint =
                                    coercedAnchors.minByOrNull { abs(it - coercedTarget) }!!
                                val base =
                                    halvedNumbersColumnHeightPx * (target / halvedNumbersColumnHeightPx).toInt()
                                val adjusted = coercedPoint + base
                                TargetAnimation(adjusted, SpringSpec())
                            }
                        )
                        val endValue = animatedOffset.velocity(
                            initialVelocity = velocity,
                            flingConfig = flingConfig,
                        ).endState.value

                        state.value = animatedStateValue(endValue)
                        animatedOffset.snapTo(0f)
                    }*/
                }
            )
    ) {
        val spacing = 4.dp

        val arrowColor = MaterialTheme.colors.onSecondary.copy(alpha = ContentAlpha.disabled)

        Spacer(modifier = Modifier.height(spacing))

        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .offset(y = coercedAnimatedOffset.roundToInt().dp)
        ) {
            val baseLabelModifier = Modifier.align(Alignment.Center)
            ProvideTextStyle(textStyle) {
                Label(
                    text = (animatedStateValue - 1).toString(),
                    modifier = baseLabelModifier
                        .offset(y = -halvedNumbersColumnHeight)
                        .alpha(coercedAnimatedOffset / halvedNumbersColumnHeightPx)
                )
                Label(
                    text = animatedStateValue.toString(),
                    modifier = baseLabelModifier
                        .alpha(1 - abs(coercedAnimatedOffset) / halvedNumbersColumnHeightPx)
                )
                Label(
                    text = (animatedStateValue + 1).toString(),
                    modifier = baseLabelModifier
                        .offset(y = halvedNumbersColumnHeight)
                        .alpha(-coercedAnimatedOffset / halvedNumbersColumnHeightPx)
                )
            }
        }

        Spacer(modifier = Modifier.height(spacing))
    }
}

@Composable
private fun Label(text: String, modifier: Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.h3,
        modifier = modifier.pointerInput(Unit) {
            detectTapGestures(onLongPress = {
                // FIXME: Empty to disable text selection
            })
        }
    )
}

@Preview
@Composable
fun PreviewNumberPicker() {
    Box(modifier = Modifier.fillMaxSize()) {
        NumberPicker(
            state = mutableStateOf(9),
            range = 0..10,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}