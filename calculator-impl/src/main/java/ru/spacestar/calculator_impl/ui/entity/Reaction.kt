package ru.spacestar.calculator_impl.ui.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("Data")
internal data class Reaction(
    @PrimaryKey val id: Int?,
    val name: String?,
    val value: String?,
    val terms: String?
) {
    val displayReactions: List<String>
        get() = prepare(value)

    private fun prepare(value: String?): List<String> {
        val terms = terms.orEmpty().split(Regex("""(?<=\d\) )|(?=(?<!^)\d+\) )"""))
        val termsMap = when {
            terms.size > 1 -> buildMap {
                for (i in terms.indices step 2) {
                    val index = terms[i].trim().removeSuffix(")").toInt()
                    val term = terms[i + 1].trim()
                    put(index, term)
                }
            }
            terms.size == 1 -> mapOf(0 to terms.first().trim())
            else -> emptyMap()
        }
        return value.orEmpty().replace("<Up>", "↑")
            .replace("<Down>", "↓")
            .replace("<Right>", "→")
            .split(Regex(" *<br> *"))
            .let { reacts ->
                if (termsMap.isEmpty())
                    reacts
                else
                    reacts.mapIndexed { index, react ->
                        val term = termsMap[index]?.let { "  [$it]" } ?: ""
                        react + term
                    }
            }
    }
}