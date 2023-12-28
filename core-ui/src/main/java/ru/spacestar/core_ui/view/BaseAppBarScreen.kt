package ru.spacestar.core_ui.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController

@Composable
fun BaseAppBarScreen(
    navController: NavController,
    title: String? = null,
    onBackPressed: (() -> Unit) = { navController.popBackStack() },
    isBackEnabled: Boolean = true,
    backIcon: ImageVector = Icons.Filled.ArrowBack,
    appBarMenu: @Composable RowScope.() -> Unit = {},
    content: @Composable (PaddingValues) -> Unit = {}
) {
    Scaffold(
        topBar = {
            LogsViewerAppBar(
                title = title,
                backIcon = backIcon,
                onBackPressed = if (isBackEnabled) onBackPressed else null,
                actions = appBarMenu
            )
        },
        content = content
    )
}