package ru.spacestar.chem.utils

class ReactionFormatter {
    private val toReactionPattern = Regex("""(?<=[)a-zA-Z\u2080-\u2089])\d+""")
    private val fromReactionPattern = Regex("""[\u2080-\u2089]+""")

    fun toReaction(input: String) = toReactionPattern.replace(input) {
        it.value.map { c -> c - '0'.code + BOTTOM_0 }.joinToString(separator = "")
    }

    fun fromReaction(input: String) = fromReactionPattern.replace(input) {
        it.value.map { c -> c + '0'.code - BOTTOM_0 }.joinToString(separator = "")
    }

    companion object {
        private const val BOTTOM_0 = 0x2080
    }
}