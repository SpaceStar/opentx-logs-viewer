package ru.spacestar.info_impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.spacestar.info_api.InfoFeatureApi
import ru.spacestar.info_impl.navigation.InfoFeatureApiImpl

@Module
@InstallIn(SingletonComponent::class)
internal interface InfoFeatureModule {
    @Binds
    fun provideFeatureApi(
        api: InfoFeatureApiImpl
    ): InfoFeatureApi
}