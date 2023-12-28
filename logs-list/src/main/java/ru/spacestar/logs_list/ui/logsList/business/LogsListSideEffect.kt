package ru.spacestar.logs_list.ui.logsList.business

internal sealed interface LogsListSideEffect {
    class ShowMessage(val msgRes: Int) : LogsListSideEffect
    class Navigate(val route: String) : LogsListSideEffect
}