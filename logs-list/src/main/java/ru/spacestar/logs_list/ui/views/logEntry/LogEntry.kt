package ru.spacestar.logs_list.ui.views.logEntry

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class LogEntry(
    val time: Long,
    val details: Map<String, String>
): Parcelable