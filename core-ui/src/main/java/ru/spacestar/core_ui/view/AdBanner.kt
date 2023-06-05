package ru.spacestar.core_ui.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.yandex.mobile.ads.banner.AdSize
import com.yandex.mobile.ads.banner.BannerAdView
import com.yandex.mobile.ads.common.AdRequest
import ru.spacestar.core.utils.ResourceExtensions.getScreenWidth

@Composable
fun AdBanner(
    modifier: Modifier = Modifier,
    unitId: String
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            BannerAdView(context).apply {
                setAdUnitId(unitId)
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