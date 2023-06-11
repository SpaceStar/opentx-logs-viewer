package ru.spacestar.info_impl.ui.view

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.spacestar.core_ui.view.BaseAppBarScreen
import ru.spacestar.core_ui.view.HtmlText
import ru.spacestar.info_impl.R

@Composable
internal fun Info(navController: NavController) {
    BaseAppBarScreen(
        navController = navController,
        title = stringResource(id = ru.spacestar.info_api.R.string.info_title)
    ) { paddingValues ->
        HtmlText(
            html = stringResource(id = R.string.info),
            modifier = Modifier.padding(paddingValues).padding(16.dp)
        )
    }
}