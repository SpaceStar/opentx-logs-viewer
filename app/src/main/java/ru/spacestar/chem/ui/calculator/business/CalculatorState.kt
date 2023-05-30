package ru.spacestar.chem.ui.calculator.business

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CalculatorState(
    val input: String = "",
    val result: String = ""
) : Parcelable
