package ru.spacestar.chem.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.spacestar.chem.ui.calculator.dao.ReactionDao
import ru.spacestar.chem.ui.calculator.entity.Reaction

@Database(entities = [Reaction::class], version = 1)
abstract class ReactionDatabase : RoomDatabase() {
    abstract fun reactionDao(): ReactionDao
}