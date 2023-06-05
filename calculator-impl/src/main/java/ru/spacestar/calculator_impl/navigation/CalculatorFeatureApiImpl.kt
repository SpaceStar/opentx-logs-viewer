package ru.spacestar.calculator_impl.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import ru.spacestar.calculator_api.CalculatorFeatureApi
import ru.spacestar.calculator_impl.ui.view.Calculator
import javax.inject.Inject

internal class CalculatorFeatureApiImpl @Inject constructor() : CalculatorFeatureApi {
    companion object {
        private const val BASE_ROUTE = "calculator"
    }

    override fun route() = BASE_ROUTE

    @OptIn(ExperimentalAnimationApi::class)
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController
    ) {
        navGraphBuilder.composable(BASE_ROUTE) {
            Calculator(navController, hiltViewModel())
        }
    }
}