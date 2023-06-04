package ru.spacestar.chem.navigation

sealed class Screen(val destination: String) {
    object Info : Screen("info")
}
