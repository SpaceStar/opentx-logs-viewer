package ru.spacestar.calculator_impl.navigation

import androidx.compose.runtime.MutableState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.spacestar.calculator_api.CalculatorFeatureApi
import ru.spacestar.calculator_impl.ui.view.Calculator
import javax.inject.Inject

internal class CalculatorFeatureApiImpl @Inject constructor() : CalculatorFeatureApi {
    companion object {
        private const val BASE_ROUTE = "calculator"
    }

    override fun route() = BASE_ROUTE

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController,
        title: MutableState<String?>?
    ) {
        navGraphBuilder.composable(BASE_ROUTE) {
            title?.value = null
            Calculator(viewModel = hiltViewModel())
        }
    }
}