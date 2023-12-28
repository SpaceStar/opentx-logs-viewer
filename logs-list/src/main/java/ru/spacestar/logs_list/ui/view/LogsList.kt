package ru.spacestar.logs_list.ui.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import ru.spacestar.core_ui.view.BaseAppBarScreen

@Composable
internal fun LogsList(
    navController: NavController
) {
    BaseAppBarScreen(
        navController = navController,
        isBackEnabled = false
    )
}