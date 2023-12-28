package ru.spacestar.logs_list.ui.logDetails.business

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal class LogDetailsState(
    val logName: String = ""
) : Parcelable