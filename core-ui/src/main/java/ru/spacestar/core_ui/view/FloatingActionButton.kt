package ru.spacestar.core_ui.view

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material3.FloatingActionButton as MaterialFloatingActionButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FloatingActionButton(
    modifier: Modifier = Modifier,
    hint: String? = null,
    imageVector: ImageVector,
    onClick: () -> Unit
) {
    if (hint != null) {
        PlainTooltipBox(tooltip = {
            Text(text = hint)
        }) {
            Fab(modifier.tooltipAnchor(), hint, imageVector, onClick)
        }
    } else Fab(modifier, null, imageVector, onClick)
}

@Composable
private fun Fab(
    modifier: Modifier = Modifier,
    hint: String? = null,
    imageVector: ImageVector,
    onClick: () -> Unit
) {
    MaterialFloatingActionButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = hint
        )
    }
}