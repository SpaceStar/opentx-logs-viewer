package ru.spacestar.chem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yandex.mobile.ads.banner.AdSize
import com.yandex.mobile.ads.banner.BannerAdView
import com.yandex.mobile.ads.common.AdRequest
import dagger.hilt.android.AndroidEntryPoint
import ru.spacestar.calculator_api.CalculatorFeatureApi
import ru.spacestar.chem.navigation.Screen
import ru.spacestar.chem.ui.common.ChemAppBar
import ru.spacestar.chem.ui.info.view.Info
import ru.spacestar.core.utils.ResourceExtensions.getScreenWidth
import ru.spacestar.core_ui.theme.ChemTheme
import ru.spacestar.core_ui.utils.UiExtensions.isDestination
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var calculatorApi: CalculatorFeatureApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChemTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val back: () -> Unit = { navController.popBackStack() }
                    val isStartScreen = navController.isDestination(calculatorApi.route())
                    val title = remember { mutableStateOf<String?>(null) }
                    Scaffold(
                        topBar = {
                            ChemAppBar(
                                title = title.value,
                                onBackPressed = if (!isStartScreen) back else null,
                            ) {
                                if (isStartScreen) {
                                    IconButton(onClick = {
                                        navController.navigate(Screen.Info.destination)
                                    }) {
                                        Icon(
                                            imageVector = Icons.Outlined.HelpOutline,
                                            contentDescription = stringResource(R.string.app_bar_info)
                                        )
                                    }
                                }
                            }
                        }
                    ) { contentPadding ->
                        Column(modifier = Modifier
                            .fillMaxSize()
                            .padding(contentPadding)) {
                            NavHost(
                                navController = navController,
                                calculatorApi.route(),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            ) {
                                calculatorApi.registerGraph(this, navController, title)
                                composable(Screen.Info.destination) {
                                    title.value = stringResource(R.string.app_bar_info)
                                    Info()
                                }
                            }
                            AndroidView(
                                modifier = Modifier.fillMaxWidth(),
                                factory = { context ->
                                    BannerAdView(context).apply {
                                        setAdUnitId(BuildConfig.YAD_ID)
                                        setAdSize(
                                            AdSize.stickySize(
                                                context,
                                                context.getScreenWidth()
                                            )
                                        )
                                        loadAd(AdRequest.Builder().build())
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}