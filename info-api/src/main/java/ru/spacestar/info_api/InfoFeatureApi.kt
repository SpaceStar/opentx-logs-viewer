package ru.spacestar.info_api

import ru.spacestar.core_feature_api.FeatureApi

interface InfoFeatureApi : FeatureApi {
    fun route(): String
}