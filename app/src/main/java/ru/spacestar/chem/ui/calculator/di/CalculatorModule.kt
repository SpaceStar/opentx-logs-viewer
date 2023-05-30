package ru.spacestar.chem.ui.calculator.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.spacestar.chem.ui.calculator.business.ReactionRepository
import ru.spacestar.chem.ui.calculator.business.ReactionRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
interface CalculatorModule {
    @Binds
    fun provideReactionRepository(
        repository: ReactionRepositoryImpl
    ): ReactionRepository
}