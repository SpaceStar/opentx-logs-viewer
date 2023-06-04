package ru.spacestar.calculator_api

import ru.spacestar.core_feature_api.FeatureApi

interface CalculatorFeatureApi : FeatureApi {
    fun route(): String
}