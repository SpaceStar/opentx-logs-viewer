package ru.spacestar.logs_list.ui.logItem

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.InsertDriveFile
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.spacestar.logs_list.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun LogItem(
    modifier: Modifier = Modifier,
    state: LogItemState,
    onClick: (Uri) -> Unit,
    onRemove: (Uri) -> Unit
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurfaceVariant),
        onClick = { onClick(state.uri) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp, end = 8.dp, bottom = 16.dp),
                imageVector = Icons.Default.InsertDriveFile,
                contentDescription = null
            )
            Text(modifier = Modifier
                .weight(1f)
                .padding(vertical = 16.dp), text = state.filename)
            // fixme: ripple size on remove button
            PlainTooltipBox(tooltip = { Text(text = stringResource(id = R.string.delete_button)) }) {
                Surface(
                    modifier = Modifier.tooltipAnchor(),
                    shape = CircleShape,
                    onClick = { onRemove(state.uri) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        tint = Color.Red.copy(alpha = 0.7f),
                        contentDescription = stringResource(id = R.string.add_log_button)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Surface {
        LogItem(
            modifier = Modifier
                .fillMaxWidth(),
            LogItemState("test", Uri.EMPTY, 0),
            onClick = {}, onRemove = {})
    }
}