package ru.spacestar.core_ui.view

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import ru.spacestar.core_ui.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DictionaryAppBar(
    title: String? = null,
    onBackPressed: (() -> Unit)? = null,
    backIcon: ImageVector = Icons.Filled.ArrowBack,
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = { Text(title ?: stringResource(id = R.string.app_name)) },
        colors = TopAppBarDefaults.topAppBarColors(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.onPrimary,
            MaterialTheme.colorScheme.onPrimary,
            MaterialTheme.colorScheme.onPrimary,
        ),
        navigationIcon = {
            if (onBackPressed != null) {
                IconButton(onClick = onBackPressed) {
                    Icon(
                        imageVector = backIcon,
                        contentDescription = stringResource(R.string.app_bar_back)
                    )
                }
            }
        },
        actions = actions
    )
}