package ru.spacestar.logs_list.ui.logsList.business

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.spacestar.logs_list.ui.logItem.LogItemState

@Parcelize
internal data class LogsListState(
    val logs: List<LogItemState> = emptyList()
) : Parcelable