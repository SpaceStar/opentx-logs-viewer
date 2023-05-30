package ru.spacestar.chem.utils

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel

object ResourceExtensions {
    fun AndroidViewModel.getString(@StringRes resId: Int) = getApplication<Application>()
        .getString(resId)
}