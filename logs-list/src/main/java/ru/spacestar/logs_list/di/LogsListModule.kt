package ru.spacestar.logs_list.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.spacestar.core_feature_api.LogsListApi
import ru.spacestar.logs_list.navigation.LogsListApiImpl

@Module
@InstallIn(SingletonComponent::class)
internal interface LogsListModule {
    @Binds
    fun provideLogsListApi(
        api: LogsListApiImpl
    ): LogsListApi
}