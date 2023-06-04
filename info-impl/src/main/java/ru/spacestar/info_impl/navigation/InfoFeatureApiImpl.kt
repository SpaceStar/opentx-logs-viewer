package ru.spacestar.info_impl.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.spacestar.info_api.InfoFeatureApi
import ru.spacestar.info_impl.R
import ru.spacestar.info_impl.ui.view.Info
import javax.inject.Inject

internal class InfoFeatureApiImpl @Inject constructor() : InfoFeatureApi {
    companion object {
        private const val BASE_ROUTE = "info"
    }

    override fun route() = BASE_ROUTE

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController,
        title: MutableState<String?>?
    ) {
        navGraphBuilder.composable(BASE_ROUTE) {
            title?.value = stringResource(id = R.string.info_title)
            Info()
        }
    }
}