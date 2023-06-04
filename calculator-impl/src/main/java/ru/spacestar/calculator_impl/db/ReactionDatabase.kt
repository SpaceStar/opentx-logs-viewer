package ru.spacestar.calculator_impl.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.spacestar.calculator_impl.ui.dao.ReactionDao
import ru.spacestar.calculator_impl.ui.entity.Reaction

@Database(entities = [Reaction::class], version = 1)
internal abstract class ReactionDatabase : RoomDatabase() {
    abstract fun reactionDao(): ReactionDao
}