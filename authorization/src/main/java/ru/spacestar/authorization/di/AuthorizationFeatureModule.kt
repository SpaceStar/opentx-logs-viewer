package ru.spacestar.authorization.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.spacestar.authorization.navigation.AuthorizationFeatureApiImpl
import ru.spacestar.core_feature_api.AuthorizationFeatureApi

@Module
@InstallIn(SingletonComponent::class)
internal interface AuthorizationFeatureModule {
    @Binds
    fun provideFeatureApi(
        api: AuthorizationFeatureApiImpl
    ): AuthorizationFeatureApi
}