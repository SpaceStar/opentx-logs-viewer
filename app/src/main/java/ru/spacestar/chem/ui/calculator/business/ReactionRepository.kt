package ru.spacestar.chem.ui.calculator.business

interface ReactionRepository {
    suspend fun findReactions(input: String): List<String>
}