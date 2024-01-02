package ru.spacestar.logs_list.ui

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import java.util.Locale
import kotlin.math.ceil
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

// TODO: not a smooth scroll on max zoom
// TODO: not good zoom offset
// TODO: clean class
@Composable
fun Timeline(
    modifier: Modifier = Modifier,
    minTime: Long? = null,
    maxTime: Long? = null,
    onTimeSelect: (Long) -> Unit = {}
) {
    if (minTime == null && maxTime != null ||
        minTime != null && maxTime == null)
        throw IllegalStateException("both minTime and maxTime should be specified or be null")

    val textMeasurer = rememberTextMeasurer()

    // 10 minutes
    val defaultOffset = remember { 10L * 60 * 100 }
    var vpSize = remember { Size.Zero }


    val zoom = rememberSaveable {
        mutableFloatStateOf(1f)
    }
    val offset = rememberSaveable {
        mutableDoubleStateOf(0.0)
    }

    fun maxTimeResolved() = maxTime ?: (Calendar.getInstance().timeInMillis + defaultOffset)
    fun minTimeResolved() = minTime ?: (Calendar.getInstance().timeInMillis)

    fun timeToPos(time: Long): Float {
        return vpSize.width * zoom.floatValue * (time - minTimeResolved()) /
                (maxTimeResolved() - minTimeResolved()) + vpSize.center.x + offset.doubleValue.toFloat()
    }

    fun posToTime(pos: Float): Long {
        return ((pos - vpSize.center.x - offset.doubleValue) *
                (maxTimeResolved() - minTimeResolved()) /
                (vpSize.width * zoom.floatValue)).toLong() + minTimeResolved()
    }

    val selectedTime = remember {
        mutableLongStateOf(minTimeResolved())
    }

    LaunchedEffect(selectedTime.longValue) {
        onTimeSelect(selectedTime.longValue)
    }

    // TODO: flings
    // TODO: zoom to center
    val transformListener = rememberTransformableState { zoomChange, panChange, _ ->
        val currentTime = posToTime(vpSize.center.x)
        zoom.floatValue *= zoomChange

        val maxOffset = if (minTime != null)
            0.0
        else
            Double.POSITIVE_INFINITY
        val minOffset = if (maxTime != null)
            -vpSize.width * zoom.floatValue.toDouble()
        else
            Double.NEGATIVE_INFINITY
        val zoomOffset = if (zoomChange != 1f)
            timeToPos(currentTime) - vpSize.center.x
        else
            0f
        offset.doubleValue = (offset.doubleValue + panChange.x - zoomOffset)
            .coerceIn(minOffset, maxOffset)
    }

    Canvas(
        modifier = modifier
            .background(Color.Gray.copy(alpha = 0.4f))
            .transformable(transformListener)
    ) {
        vpSize = size
        val width = size.width * zoom.floatValue
        if (minTime != null) {
            drawRect(
                color = Color.Cyan.copy(alpha = 0.3f),
                topLeft = Offset(timeToPos(minTime), 0f),
                size = Size(width, size.height),
            )
        }
        drawLine(
            color = Color.Red,
            start = Offset(size.center.x, 0f),
            end = Offset(size.center.x, size.height)
        )

        val startTime = posToTime(0f)
        val endTime = posToTime(size.width) + 1
        val timeInterval = (endTime - startTime).milliseconds
        val timeUnit: TimeUnit = when {
            timeInterval.inWholeDays > 1 -> TimeUnit.DAYS
            timeInterval.inWholeHours > 1 -> TimeUnit.HOURS
            timeInterval.inWholeMinutes > 1 -> TimeUnit.MINUTES
            timeInterval.inWholeSeconds > 1 -> TimeUnit.SECONDS
            else -> TimeUnit.MILLIS
        }
        val timeList = buildList {
            fun getIncDuration(interval: Long): Long {
                return if (interval > 5)
                    ceil(interval / 25f).toLong() * 5
                else
                    1
            }

            val incDuration: Duration = when (timeUnit) {
                TimeUnit.DAYS -> getIncDuration(timeInterval.inWholeDays).days
                TimeUnit.HOURS -> getIncDuration(timeInterval.inWholeHours).hours
                TimeUnit.MINUTES -> getIncDuration(timeInterval.inWholeMinutes).minutes
                TimeUnit.SECONDS -> getIncDuration(timeInterval.inWholeSeconds).seconds
                TimeUnit.MILLIS -> getIncDuration(timeInterval.inWholeMilliseconds).milliseconds
            }

            val time: Calendar = Calendar.getInstance().apply {
                timeInMillis = startTime / incDuration.inWholeMilliseconds *
                        incDuration.inWholeMilliseconds
                if (timeInMillis < startTime)
                    timeInMillis += incDuration.inWholeMilliseconds
            }

            while (time.timeInMillis < endTime) {
                add(time.timeInMillis)
                time.timeInMillis += incDuration.inWholeMilliseconds
            }
        }

        val format = when (timeUnit) {
            TimeUnit.DAYS -> SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
            TimeUnit.HOURS -> SimpleDateFormat("HH:mm", Locale.getDefault())
            TimeUnit.MINUTES -> SimpleDateFormat("HH:mm", Locale.getDefault())
            TimeUnit.SECONDS -> SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            TimeUnit.MILLIS -> SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())
        }
        var textStartThreshold = 8.dp.toPx()
        timeList.forEach {
            val x = timeToPos(it)

            val date = format.format(it)
            val textLayoutResult = textMeasurer.measure(date)
            val textStartPos = x - textLayoutResult.size.center.x
            val textEndPos = x + textLayoutResult.size.center.x
            val hasText = if (textStartPos >= textStartThreshold && textEndPos <= size.width - 8.dp.toPx()) {
                drawText(
                    textMeasurer = textMeasurer,
                    text = date,
                    topLeft = Offset(
                        textStartPos,
                        0f
                    )
                )
                textStartThreshold = textEndPos + 8.dp.toPx()
                true
            } else false

            drawLine(
                color = Color.Black,
                start = Offset(
                    x,
                    if (hasText) textLayoutResult.size.height.toFloat() else 0f
                ),
                end = Offset(x, size.height),
                strokeWidth = 1.dp.toPx()
            )
        }

        selectedTime.longValue = posToTime(size.center.x)
    }
}

private enum class TimeUnit {
    DAYS,
    HOURS,
    MINUTES,
    SECONDS,
    MILLIS
}

@Preview
@Composable
fun PreviewTimeline() {
    var text by remember {
        mutableStateOf("")
    }

    Surface(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 200.dp)
        .height(200.dp)) {
        Column {
            Text(text = text)
            // TODO: fix modifier
            Timeline(
                modifier = Modifier.fillMaxSize(),
                minTime = 1703831017000L,
                maxTime = 1703841017000L
            ) {
                text = SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS", Locale.getDefault()).format(it)
            }
        }
    }
}