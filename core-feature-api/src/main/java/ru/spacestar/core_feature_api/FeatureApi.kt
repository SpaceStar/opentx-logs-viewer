package ru.spacestar.core_feature_api

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

interface FeatureApi {
    fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController,
    )
}