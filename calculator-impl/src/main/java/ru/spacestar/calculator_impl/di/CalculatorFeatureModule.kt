package ru.spacestar.calculator_impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.spacestar.calculator_api.CalculatorFeatureApi
import ru.spacestar.calculator_impl.navigation.CalculatorFeatureApiImpl

@Module
@InstallIn(SingletonComponent::class)
internal interface CalculatorFeatureModule {
    @Binds
    fun provideFeatureApi(
        api: CalculatorFeatureApiImpl
    ): CalculatorFeatureApi
}