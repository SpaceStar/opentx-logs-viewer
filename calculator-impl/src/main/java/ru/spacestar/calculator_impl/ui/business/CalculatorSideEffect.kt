package ru.spacestar.calculator_impl.ui.business

internal sealed class CalculatorSideEffect {
    object RequestFocus : CalculatorSideEffect()
    object HideKeyboard : CalculatorSideEffect()
    class Navigate(val route: String) : CalculatorSideEffect()
}
