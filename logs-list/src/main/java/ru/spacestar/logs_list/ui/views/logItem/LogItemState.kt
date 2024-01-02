package ru.spacestar.logs_list.ui.views.logItem

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal class LogItemState(
    val filename: String,
    val uri: Uri,
    val date: Long
) : Parcelable