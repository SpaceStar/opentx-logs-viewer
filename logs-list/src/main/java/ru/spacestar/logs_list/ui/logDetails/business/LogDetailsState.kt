package ru.spacestar.logs_list.ui.logDetails.business

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.spacestar.logs_list.ui.logEntry.LogEntry

@Parcelize
internal data class LogDetailsState(
    val loading: Boolean = false,
    val logName: String = "",
    val selectedEntry: Int = 0,
    val details: List<LogEntry> = emptyList(),
) : Parcelable