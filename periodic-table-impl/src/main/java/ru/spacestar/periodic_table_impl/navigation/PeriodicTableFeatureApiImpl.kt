package ru.spacestar.periodic_table_impl.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.spacestar.core_feature_api.PeriodicTableFeatureApi
import ru.spacestar.periodic_table_impl.ui.view.PeriodicTable
import javax.inject.Inject

internal class PeriodicTableFeatureApiImpl @Inject constructor() : PeriodicTableFeatureApi {
    companion object {
        private const val BASE_ROUTE = "periodic_table"
    }

    override fun route() = BASE_ROUTE

    override fun registerGraph(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable(BASE_ROUTE) {
            PeriodicTable(navController = navController)
        }
    }
}