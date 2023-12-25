package ru.spacestar.periodic_table_api

import ru.spacestar.core_feature_api.FeatureApi

interface PeriodicTableFeatureApi : FeatureApi {
    fun route(): String
}