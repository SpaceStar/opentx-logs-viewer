package ru.spacestar.info_impl.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.spacestar.core_feature_api.InfoFeatureApi
import ru.spacestar.info_impl.ui.view.Info
import javax.inject.Inject

internal class InfoFeatureApiImpl @Inject constructor() : InfoFeatureApi {
    companion object {
        private const val BASE_ROUTE = "info"
    }

    override fun route() = BASE_ROUTE

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController
    ) {
        navGraphBuilder.composable(BASE_ROUTE) {
            Info(navController)
        }
    }
}