package ru.spacestar.calculator_impl.ui.business

internal interface ReactionRepository {
    suspend fun findReactions(input: String): List<String>
}