package ru.spacestar.periodic_table_impl.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import ru.spacestar.core_ui.view.BaseAppBarScreen

@Composable
internal fun PeriodicTable(
    navController: NavController
) {
    BaseAppBarScreen(
        navController = navController,
        title = stringResource(id = ru.spacestar.periodic_table_api.R.string.periodic_table_title)
    ) { paddingValues ->

    }
}