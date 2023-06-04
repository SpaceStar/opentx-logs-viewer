package ru.spacestar.info_impl.ui.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.spacestar.info_impl.R

@Composable
internal fun Info() {
    Text(
        text = stringResource(id = R.string.info),
        modifier = Modifier.padding(16.dp)
    )
}