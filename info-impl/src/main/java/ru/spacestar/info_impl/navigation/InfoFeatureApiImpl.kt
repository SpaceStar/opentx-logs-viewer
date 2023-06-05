package ru.spacestar.info_impl.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import ru.spacestar.info_api.InfoFeatureApi
import ru.spacestar.info_impl.ui.view.Info
import javax.inject.Inject

internal class InfoFeatureApiImpl @Inject constructor() : InfoFeatureApi {
    companion object {
        private const val BASE_ROUTE = "info"
    }

    override fun route() = BASE_ROUTE

    @OptIn(ExperimentalAnimationApi::class)
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController
    ) {
        navGraphBuilder.composable(BASE_ROUTE) {
            Info(navController)
        }
    }
}