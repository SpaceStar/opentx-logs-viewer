package ru.spacestar.calculator_impl.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.spacestar.calculator_impl.db.ReactionDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun provideReactionDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, ReactionDatabase::class.java, "reaction")
        .createFromAsset("database/data.sqlite")
        .build()

    @Provides
    fun provideReactionDao(
        database: ReactionDatabase
    ) = database.reactionDao()
}