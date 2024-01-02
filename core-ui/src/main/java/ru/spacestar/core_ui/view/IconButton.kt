package ru.spacestar.core_ui.view

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material3.IconButton as MaterialIconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IconButton(
    modifier: Modifier = Modifier,
    hint: String? = null,
    imageVector: ImageVector,
    onClick: () -> Unit
) {
    if (hint != null) {
        PlainTooltipBox(tooltip = {
            Text(text = hint)
        }) {
            Button(modifier.tooltipAnchor(), hint, imageVector, onClick)
        }
    } else Button(modifier, null, imageVector, onClick)
}

@Composable
private fun Button(
    modifier: Modifier = Modifier,
    hint: String? = null,
    imageVector: ImageVector,
    onClick: () -> Unit
) {
    MaterialIconButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = hint
        )
    }
}