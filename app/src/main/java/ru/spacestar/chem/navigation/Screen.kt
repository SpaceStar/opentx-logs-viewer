package ru.spacestar.chem.navigation

sealed class Screen(val destination: String) {
    object Calculator : Screen("calculator")
}
