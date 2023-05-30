package ru.spacestar.chem.ui.calculator.business

import ru.spacestar.chem.ui.calculator.dao.ReactionDao
import javax.inject.Inject

class ReactionRepositoryImpl @Inject constructor(
    private val reactionDao: ReactionDao
) : ReactionRepository {
    override suspend fun findReactions(input: String): List<String> {
        return reactionDao.getReactions(input).flatMap { it.displayReactions }
    }
}