package app.simple.inurelauncher.ui.components

import android.graphics.Paint
import android.graphics.Rect
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import java.time.LocalTime
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun AnalogClockComposable(
        modifier: Modifier = Modifier,
        minSize: Dp = 64.dp,
        time: LocalTime = LocalTime.now(),
        isClockRunning: Boolean = true
) {
    var seconds by remember { mutableIntStateOf(time.second) }
    var minutes by remember { mutableIntStateOf(time.minute) }
    var hours by remember { mutableIntStateOf(time.hour) }

    val secondsColor = MaterialTheme.colorScheme.primary
    val needleColor = MaterialTheme.colorScheme.onSurfaceVariant
    val clockBackgroundColor = MaterialTheme.colorScheme.surface
    val clockLineColor = Color.Gray.toArgb()

    var hourAngle by remember {
        mutableDoubleStateOf(0.0)
    }

    LaunchedEffect(key1 = minutes) {
        //Just putting this in LaunchedEffect so that we are going only to calculate
        //the angle when the minutes change
        hourAngle = (minutes / 60.0 * 30.0) - 90.0 + (hours * 30)
    }

    LaunchedEffect(isClockRunning) {
        while (isClockRunning) {
            seconds += 1
            if (seconds > 60) {
                seconds = 1
                minutes++
            }
            if (minutes > 60) {
                minutes = 1
                hours++
            }
            delay(1000)
        }
    }

    BoxWithConstraints() {

        //This is the estate we are going to work with
        val width = if (minWidth < 1.dp) minSize else minWidth
        val height = if (minHeight < 1.dp) minSize else minHeight

        Canvas(
                modifier = modifier
                    .size(width, height)
        ) {

            // lets draw the circle now
            // but before we do that lets calculate the radius,
            // which is going to be 40% of the radius so we can achieve responsiveness
            val radius = size.width * .4f
            // Clock background
            drawCircle(
                    color = clockBackgroundColor,
                    style = Fill,
                    radius = radius,
                    center = size.center,
            )

            //The degree difference between the each 'minute' line
            val angleDegreeDifference = (360f / 60f)

            //drawing all lines
            (1..60).forEach {
                val angleRadDifference =
                    (((angleDegreeDifference * it) - 90f) * (PI / 180f)).toFloat()
                val lineLength = if (it % 5 == 0) radius * .85f else radius * .93f
                val lineColour = if (it % 5 == 0) Color.Black else Color.Gray
                val startOffsetLine = Offset(
                        x = lineLength * cos(angleRadDifference) + size.center.x,
                        y = lineLength * sin(angleRadDifference) + size.center.y
                )
                val endOffsetLine = Offset(
                        x = (radius - ((radius * .05f) / 2)) * cos(angleRadDifference) + size.center.x,
                        y = (radius - ((radius * .05f) / 2)) * sin(angleRadDifference) + size.center.y
                )
                drawLine(
                        color = lineColour,
                        start = startOffsetLine,
                        end = endOffsetLine
                )
                if (it % 5 == 0) {
                    // here we are using the native canvas (native canvas is the traditional
                    // one we use dto work with the views), so that we can draw text on the canvas
                    drawContext.canvas.nativeCanvas.apply {
                        val positionX = (radius * .75f) * cos(angleRadDifference) + size.center.x
                        val positionY = (radius * .75f) * sin(angleRadDifference) + size.center.y
                        val text = (it / 5).toString()
                        val paint = Paint()
                        paint.textSize = radius * .15f
                        paint.color = clockLineColor
                        val textRect = Rect()
                        paint.getTextBounds(text, 0, text.length, textRect)

                        drawText(
                                text,
                                positionX - (textRect.width() / 2),
                                positionY + (textRect.width() / 2),
                                paint
                        )
                    }
                }
            }

            //now draw the center of the screen :O
            drawCircle(
                    color = Color.Black,
                    radius = radius * .02f, //only 2% of the main radius
                    center = size.center
            )

            //hour hand
            drawLine(
                    color = needleColor,
                    start = size.center,
                    end = Offset(
                            //don't forget, the hourAngle is calculated in the one of the LaunchedEffects
                            x = (radius * .50f) * cos((hourAngle * (PI / 180)).toFloat()) + size.center.x,
                            y = (radius * .50f) * sin((hourAngle * (PI / 180)).toFloat()) + size.center.y
                    ),
                    strokeWidth = radius * .06f,
                    cap = StrokeCap.Round
            )

            //          minutes hand - just dividing the seconds with 60 and multiplying it by 6 degrees (which is the difference between the lines)
            // subtracting 90 as the 0degrees is actually at 3 o'clock
            val minutesAngle = (seconds / 60.0 * 6.0) - 90.0 + (minutes * 6.0)
            drawLine(
                    color = needleColor,
                    start = size.center,
                    end = Offset(
                            x = (radius * .65f) * cos((minutesAngle * (PI / 180)).toFloat()) + size.center.x,
                            y = (radius * .65f) * sin((minutesAngle * (PI / 180)).toFloat()) + size.center.y
                    ),
                    strokeWidth = radius * .03f,
                    cap = StrokeCap.Round
            )

            // seconds hand
            drawLine(
                    color = secondsColor,
                    start = Offset(
                            x = size.center.x - (radius * .1f) * cos(seconds.secondsToRad()),
                            y = size.center.y - (radius * .1f) * sin(seconds.secondsToRad())
                    ),
                    end = Offset(
                            x = (radius * .70F) * cos(seconds.secondsToRad()) + size.center.x,
                            y = (radius * .70F) * sin(seconds.secondsToRad()) + size.center.y
                    ),
                    strokeWidth = 3.dp.toPx(),
                    cap = StrokeCap.Round
            )

            // Draw a circle at the start of the seconds hand
            drawCircle(
                    color = secondsColor,
                    radius = radius * .05f, //only 2% of the main radius
            )

            // Draw a circle at the end of the seconds hand
            drawCircle(
                    color = secondsColor,
                    radius = radius * .03f, //only 2% of the main radius
                    center = Offset(
                            x = (radius * .80F) * cos(seconds.secondsToRad()) + size.center.x,
                            y = (radius * .80F) * sin(seconds.secondsToRad()) + size.center.y
                    )
            )

            //paused text
            if (!isClockRunning) {
                drawContext.canvas.nativeCanvas.apply {
                    val text = "PAUSED"
                    val paint = Paint()
                    paint.textSize = radius * .15f
                    paint.color = android.graphics.Color.MAGENTA

                    val textRect = Rect()
                    paint.getTextBounds(text, 0, text.length, textRect)

                    drawText(
                            text,
                            size.center.x - (textRect.width() / 2),
                            size.center.y + (textRect.width() / 2),
                            paint
                    )
                }
            }

        }
    }
}

/***
 * @return radians
 */
fun Int.secondsToRad(): Float {
    val angle = (360f / 60f * this) - 90f
    return (angle * (PI / 180f)).toFloat()
}
