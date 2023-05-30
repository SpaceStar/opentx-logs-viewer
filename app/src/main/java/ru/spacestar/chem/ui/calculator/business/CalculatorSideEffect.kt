package ru.spacestar.chem.ui.calculator.business

sealed class CalculatorSideEffect {
    object RequestFocus : CalculatorSideEffect()
    object HideKeyboard : CalculatorSideEffect()
}
