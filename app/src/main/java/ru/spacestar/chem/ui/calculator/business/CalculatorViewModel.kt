package ru.spacestar.chem.ui.calculator.business

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import ru.spacestar.chem.R
import ru.spacestar.chem.utils.ReactionFormatter
import ru.spacestar.chem.utils.ResourceExtensions.getString
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    @ApplicationContext context: Context,
    private val reactionRepository: ReactionRepository
) : AndroidViewModel(context as Application), ContainerHost<CalculatorState, CalculatorSideEffect> {

    private val formatter by lazy { ReactionFormatter() }

    override val container = container<CalculatorState, CalculatorSideEffect>(
        CalculatorState(),
        savedStateHandle = savedStateHandle
    ) {
        postSideEffect(CalculatorSideEffect.RequestFocus)
    }

    fun input(input: String) = intent {
        var resultInput = input
        resultInput = resultInput.replace(' ', '+')
        if (resultInput.contains('\n')) {
            resultInput = resultInput.replace("\n", "")
            postSideEffect(CalculatorSideEffect.HideKeyboard)
            search()
        }
        resultInput = formatter.toReaction(resultInput)
        reduce { state.copy(input = resultInput) }
    }

    fun search() = intent {
        val request = formatter.fromReaction(state.input)
        val reactions = reactionRepository.findReactions(request).map { reaction ->
            formatter.toReaction(reaction)
        }
        reduce {
            state.copy(result = reactions.ifEmpty { listOf(getString(R.string.calc_not_found)) }
                .joinToString(separator = "\n\n"))
        }
    }
}