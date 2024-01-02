package ru.spacestar.logs_list.ui.logEntry

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer

@Composable
fun LogEntryInfo(
    modifier: Modifier = Modifier,
    logEntry: LogEntry?
) {
    val textMeasurer = rememberTextMeasurer()

    val headersWidth = remember {
        derivedStateOf {
            val width = logEntry?.details?.keys?.maxOf {
                textMeasurer.measure(
                    text = it,
                    style = TextStyle(fontWeight = FontWeight.Bold)
                ).size.width
            }
            (width ?: 0)
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
    ) {
        items(logEntry?.details?.toList().orEmpty()) {
            LogEntryItem(
                headerWidth = headersWidth.value,
                header = it.first,
                value = it.second
            )
        }
    }
}