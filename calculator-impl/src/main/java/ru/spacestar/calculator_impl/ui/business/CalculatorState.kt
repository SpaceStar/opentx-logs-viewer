package ru.spacestar.calculator_impl.ui.business

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class CalculatorState(
    val input: String = "",
    val result: String = ""
) : Parcelable
