package ru.spacestar.periodic_table_impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.spacestar.periodic_table_api.PeriodicTableFeatureApi
import ru.spacestar.periodic_table_impl.navigation.PeriodicTableFeatureApiImpl

@Module
@InstallIn(SingletonComponent::class)
internal interface PeriodicTableFeatureModule {
    @Binds
    fun provideFeatureApi(
        api: PeriodicTableFeatureApiImpl
    ): PeriodicTableFeatureApi
}