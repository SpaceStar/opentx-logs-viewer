package ru.spacestar.authorization.ui.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import ru.spacestar.core_ui.view.BaseAppBarScreen

@Composable
internal fun Authorization(navController: NavController) {
    BaseAppBarScreen(
        navController = navController
    )
}