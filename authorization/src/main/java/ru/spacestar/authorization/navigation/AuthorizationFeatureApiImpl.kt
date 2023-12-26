package ru.spacestar.authorization.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.spacestar.authorization.ui.view.Authorization
import ru.spacestar.core_feature_api.AuthorizationFeatureApi

internal class AuthorizationFeatureApiImpl : AuthorizationFeatureApi {

    companion object {
        private const val BASE_ROUTE = "auth"
    }

    override fun route() = BASE_ROUTE

    override fun registerGraph(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable(BASE_ROUTE) {
            Authorization(navController = navController)
        }
    }
}