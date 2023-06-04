package ru.spacestar.calculator_impl.ui.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.spacestar.calculator_impl.ui.business.ReactionRepository
import ru.spacestar.calculator_impl.ui.business.ReactionRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
internal interface CalculatorModule {
    @Binds
    fun provideReactionRepository(
        repository: ReactionRepositoryImpl
    ): ReactionRepository
}