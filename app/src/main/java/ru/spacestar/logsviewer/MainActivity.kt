package ru.spacestar.logsviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.EnterTransition
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.spacestar.core_feature_api.CalculatorFeatureApi
import ru.spacestar.core_feature_api.InfoFeatureApi
import ru.spacestar.core_feature_api.LogsListApi
import ru.spacestar.core_feature_api.PeriodicTableFeatureApi
import ru.spacestar.core_ui.theme.LogsViewerTheme
import ru.spacestar.core_ui.view.AdBanner
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var calculatorApi: CalculatorFeatureApi
    @Inject
    lateinit var infoApi: InfoFeatureApi
    @Inject
    lateinit var periodicTableApi: PeriodicTableFeatureApi
    @Inject
    lateinit var logsListApi: LogsListApi

    private val startDestination: String
//        get() = calculatorApi.route()
        get() = logsListApi.route()

    private fun NavGraphBuilder.registerNavGraphs(navController: NavController) {
        calculatorApi.registerGraph(this, navController)
        infoApi.registerGraph(this, navController)
        periodicTableApi.registerGraph(this, navController)
        logsListApi.registerGraph(this, navController)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LogsViewerTheme {
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
                            startDestination = startDestination,
                            enterTransition = {
                                slideIntoContainer(SlideDirection.Start) },
                            exitTransition = {
                                fadeOut(animationSpec = tween(
                                    durationMillis = 1,
                                    delayMillis = AnimationConstants.DefaultDurationMillis
                                )) },
                            popEnterTransition = { EnterTransition.None },
                            popExitTransition = {
                                slideOutOfContainer(SlideDirection.End) },
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