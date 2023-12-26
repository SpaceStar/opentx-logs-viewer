package ru.spacestar.calculator_impl.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.spacestar.calculator_impl.ui.view.Calculator
import ru.spacestar.core_feature_api.CalculatorFeatureApi
import javax.inject.Inject

internal class CalculatorFeatureApiImpl @Inject constructor() : CalculatorFeatureApi {
    companion object {
        private const val BASE_ROUTE = "calculator"
    }

    override fun route() = BASE_ROUTE

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController
    ) {
        navGraphBuilder.composable(BASE_ROUTE) {
            Calculator(navController, hiltViewModel())
        }
    }
}