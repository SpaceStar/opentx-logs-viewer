package ru.spacestar.logs_list.ui.mapSettings.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import org.orbitmvi.orbit.compose.collectAsState
import ru.spacestar.core_ui.view.BaseAppBarScreen
import ru.spacestar.logs_list.R
import ru.spacestar.logs_list.ui.mapSettings.business.MapSettingsViewModel

@Composable
fun MapSettings(
    navController: NavController,
    viewModel: MapSettingsViewModel
) {
    val state by viewModel.collectAsState()

    BaseAppBarScreen(
        navController = navController,
        title = stringResource(id = R.string.map_settings)
    )
}