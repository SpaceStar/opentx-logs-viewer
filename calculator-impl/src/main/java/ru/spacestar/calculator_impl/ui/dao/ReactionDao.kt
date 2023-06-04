package ru.spacestar.calculator_impl.ui.dao

import androidx.room.Dao
import androidx.room.Query
import ru.spacestar.calculator_impl.ui.entity.Reaction

@Dao
internal interface ReactionDao {
    @Query("SELECT * FROM Data WHERE name = :name COLLATE NOCASE")
    suspend fun getReactions(name: String): List<Reaction>
}