package ru.spacestar.logs_list.ui.logEntry

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LogEntryItem(
    modifier: Modifier = Modifier,
    headerWidth: Int = 0,
    header: String,
    value: String
) {
    Row(
        modifier = modifier
            .height(IntrinsicSize.Max)
    ) {
        val headerWidthDp = with(LocalDensity.current) {
            headerWidth.toDp()
        }
        Text(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                .padding(8.dp)
                .width(headerWidthDp),
            fontWeight = FontWeight.Bold,
            text = header
        )
        Box(modifier = Modifier
            .fillMaxHeight()
            .weight(1f)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurface
            )
            .padding(8.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = value
            )
        }
    }
}

@Preview
@Composable
fun Preview() {
    Surface {
        val tm = rememberTextMeasurer()
        LogEntryItem(
            headerWidth = tm.measure(
                "title",
                style = TextStyle(fontWeight = FontWeight.Bold)
            ).size.width,
            header = "title",
            value = "value"
        )
    }
}