package ru.spacestar.chem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.spacestar.calculator_api.CalculatorFeatureApi
import ru.spacestar.core_ui.theme.ChemTheme
import ru.spacestar.core_ui.view.AdBanner
import ru.spacestar.info_api.InfoFeatureApi
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var calculatorApi: CalculatorFeatureApi
    @Inject
    lateinit var infoApi: InfoFeatureApi

    private fun NavGraphBuilder.registerNavGraphs(navController: NavController) {
        calculatorApi.registerGraph(this, navController)
        infoApi.registerGraph(this, navController)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChemTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        val navController = rememberNavController()
                        NavHost(
                            navController = navController,
                            startDestination = calculatorApi.route(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            registerNavGraphs(navController)
                        }
                        AdBanner(
                            modifier = Modifier.fillMaxWidth(),
                            unitId = BuildConfig.YAD_ID
                        )
                    }
                }
            }
        }
    }
}