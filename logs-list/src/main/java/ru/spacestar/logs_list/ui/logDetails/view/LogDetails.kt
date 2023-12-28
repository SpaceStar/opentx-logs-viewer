package ru.spacestar.logs_list.ui.logDetails.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import org.orbitmvi.orbit.compose.collectAsState
import ru.spacestar.core_ui.view.BaseAppBarScreen
import ru.spacestar.logs_list.ui.logDetails.business.LogDetailsViewModel

@Composable
internal fun LogDetails(
    navController: NavController,
    viewModel: LogDetailsViewModel
) {
    val state by viewModel.collectAsState()

    BaseAppBarScreen(
        navController = navController,
        title = state.logName
    )
}