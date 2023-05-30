package ru.spacestar.chem.ui.calculator.dao

import androidx.room.Dao
import androidx.room.Query
import ru.spacestar.chem.ui.calculator.entity.Reaction

@Dao
interface ReactionDao {
    @Query("SELECT * FROM Data WHERE name = :name COLLATE NOCASE")
    suspend fun getReactions(name: String): List<Reaction>
}