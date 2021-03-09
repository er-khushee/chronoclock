package com.chronoclock.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chronoclock.animation.updateTransitionData

@Composable
fun CountDownIndicator(
    modifier: Modifier = Modifier,
    progress: Float,
    time: String,
    size: Int,
    stroke: Int
) {
    val transition = updateTransitionData(progress)

    Column(modifier = modifier) {
        Box {

            CircularProgressIndicatorBackGround(
                modifier = Modifier
                    .height(size.dp)
                    .width(size.dp),
                MaterialTheme.colors.primary,
                stroke
            )

            CircularProgressIndicator(
                progress = transition.progress,
                modifier = Modifier
                    .height(size.dp)
                    .width(size.dp),
                color = Color.White,
                strokeWidth = stroke.dp,
            )

            Column(modifier = Modifier.align(Alignment.Center)) {
                Text(
                    text = time,
                    style = MaterialTheme.typography.h4,
                    color = contentColorFor(backgroundColor = transition.color),
                    modifier = Modifier
                        .width(size.dp.div(1.2f))
                        .height(size.dp.div(1.2f))
                        .background(
                            transition.color, CircleShape
                        )
                        .wrapContentSize(Alignment.Center),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
fun CircularProgressIndicatorBackGround(
    modifier: Modifier = Modifier,
    color: Color,
    stroke: Int
) {
    val style = with(LocalDensity.current) { Stroke(stroke.dp.toPx()) }
    Canvas(modifier) {
        val innerRadius = (size.minDimension - style.width) / 2
        drawArc(
            color = color,
            startAngle = 0f,
            sweepAngle = 360f,
            topLeft = Offset(
                (size / 2.0f).width - innerRadius,
                (size / 2.0f).height - innerRadius
            ),
            size = Size(innerRadius * 2, innerRadius * 2),
            useCenter = false,
            style = style
        )
    }
}

@Preview
@Composable
fun TimerPreview() {
    CountDownIndicator(progress = 0.5f, time = "03:00", size = 180, stroke = 8)
}