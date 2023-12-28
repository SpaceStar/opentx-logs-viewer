package ru.spacestar.logs_list.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.spacestar.core_feature_api.LogsListApi
import ru.spacestar.logs_list.ui.view.LogsList
import javax.inject.Inject

internal class LogsListApiImpl @Inject constructor() : LogsListApi {

    companion object {
        private const val BASE_ROUTE = "logs-list"
    }

    override fun route() = BASE_ROUTE

    override fun registerGraph(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable(BASE_ROUTE) {
            LogsList(navController = navController)
        }
    }
}