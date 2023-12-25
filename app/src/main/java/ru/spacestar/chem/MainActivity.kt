package ru.spacestar.chem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.AnimationConstants
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.spacestar.calculator_api.CalculatorFeatureApi
import ru.spacestar.core_ui.theme.ChemTheme
import ru.spacestar.core_ui.view.AdBanner
import ru.spacestar.info_api.InfoFeatureApi
import ru.spacestar.periodic_table_api.PeriodicTableFeatureApi
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var calculatorApi: CalculatorFeatureApi
    @Inject
    lateinit var infoApi: InfoFeatureApi
    @Inject
    lateinit var periodicTableApi: PeriodicTableFeatureApi

    private val startDestination: String
        get() = calculatorApi.route()
//        get() = periodicTableApi.route()

    private fun NavGraphBuilder.registerNavGraphs(navController: NavController) {
        calculatorApi.registerGraph(this, navController)
        infoApi.registerGraph(this, navController)
        periodicTableApi.registerGraph(this, navController)
    }

    @OptIn(ExperimentalAnimationApi::class)
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
                        val navController = rememberAnimatedNavController()
                        AnimatedNavHost(
                            navController = navController,
                            startDestination = startDestination,
                            enterTransition = {
                                slideIntoContainer(AnimatedContentScope.SlideDirection.Start) },
                            exitTransition = {
                                fadeOut(animationSpec = tween(
                                    durationMillis = 1,
                                    delayMillis = AnimationConstants.DefaultDurationMillis
                                )) },
                            popEnterTransition = { EnterTransition.None },
                            popExitTransition = {
                                slideOutOfContainer(AnimatedContentScope.SlideDirection.End) },
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