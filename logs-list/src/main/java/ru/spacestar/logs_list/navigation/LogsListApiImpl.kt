package ru.spacestar.logs_list.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.spacestar.core_feature_api.LogsListApi
import ru.spacestar.logs_list.ui.logDetails.view.LogDetails
import ru.spacestar.logs_list.ui.logsList.view.LogsList
import javax.inject.Inject

internal class LogsListApiImpl @Inject constructor() : LogsListApi {

    companion object {
        private const val BASE_ROUTE = "logs-list"

        const val DETAILS_ARG_URI = "uri"
        private const val DETAILS = "details"
        private const val DETAILS_ROUTE = "$BASE_ROUTE/$DETAILS/{$DETAILS_ARG_URI}"
    }

    override fun route() = BASE_ROUTE

    fun details(uri: String) = "$BASE_ROUTE/$DETAILS/$uri"

    override fun registerGraph(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable(BASE_ROUTE) {
            LogsList(navController = navController, viewModel = hiltViewModel())
        }
        navGraphBuilder.composable(
            DETAILS_ROUTE,
            arguments = listOf(navArgument(DETAILS_ARG_URI) { type = NavType.StringType })
        ) {
            LogDetails(navController = navController, viewModel = hiltViewModel())
        }
    }
}