package ru.spacestar.chem.utils

import android.app.Application
import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel

object ResourceExtensions {
    fun AndroidViewModel.getString(@StringRes resId: Int) = getApplication<Application>()
        .getString(resId)

    @Suppress("DEPRECATION")
    fun Context.getScreenWidth(): Int {
        val metrics = DisplayMetrics()
        getSystemService(WindowManager::class.java).defaultDisplay.getMetrics(metrics)
        return metrics.widthPixels
    }
}