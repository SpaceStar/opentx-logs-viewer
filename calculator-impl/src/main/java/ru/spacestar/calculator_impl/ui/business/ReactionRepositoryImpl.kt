package ru.spacestar.calculator_impl.ui.business

import ru.spacestar.calculator_impl.ui.dao.ReactionDao
import javax.inject.Inject

internal class ReactionRepositoryImpl @Inject constructor(
    private val reactionDao: ReactionDao
) : ReactionRepository {
    override suspend fun findReactions(input: String): List<String> {
        return reactionDao.getReactions(input).flatMap { it.displayReactions }
    }
}