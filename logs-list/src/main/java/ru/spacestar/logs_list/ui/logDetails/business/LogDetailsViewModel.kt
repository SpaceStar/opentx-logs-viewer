package ru.spacestar.logs_list.ui.logDetails.business

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.net.Uri
import androidx.core.net.toFile
import androidx.lifecycle.SavedStateHandle
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import ru.spacestar.core.utils.StringExtensions.decodeFromUrl
import ru.spacestar.core_ui.viewmodel.BaseSideEffect
import ru.spacestar.core_ui.viewmodel.BaseViewModel
import ru.spacestar.logs_list.navigation.LogsListApiImpl
import ru.spacestar.logs_list.ui.views.logEntry.LogEntry
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
internal class LogDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    @ApplicationContext context: Context
) : BaseViewModel<LogDetailsState, Any>(context, savedStateHandle) {

    override val container = container(LogDetailsState(loading = true)) {
        val uri: String = checkNotNull(savedStateHandle[LogsListApiImpl.DETAILS_ARG_URI])
        val file = Uri.parse(uri.decodeFromUrl).toFile()
        val name = file.nameWithoutExtension
        reduce { state.copy(logName = name) }

        val log = prepareLog(csvReader().readAllWithHeader(file))

        reduce { state.copy(
            loading = false,
            details = log,
        ) }
    }

    fun selectEntry(time: Long) = intent {
        val items = state.details.map { it.time }
        if (items.isEmpty()) return@intent
        val secondIndex = items.indexOfFirst { it >= time }
        val firstItem = items.getOrNull(secondIndex - 1)
        val secondItem = items.getOrNull(secondIndex)
        val selectedEntry = when {
            secondItem == null -> items.size - 1
            firstItem == null -> 0
            else -> {
                val firstDiff = time - firstItem
                val secondDiff = secondItem - time
                if (firstDiff < secondDiff)
                    secondIndex - 1
                else
                    secondIndex
            }
        }
        reduce { state.copy(selectedEntry = selectedEntry) }
    }

    fun toggleMap() = intent {
        reduce { state.copy(showMap = !state.showMap) }
    }

    fun openSettings() = intent {
        postSideEffect(BaseSideEffect.Navigate(TODO("настройки карты")))
    }

    private fun prepareLog(logData: List<Map<String, String>>): List<LogEntry> {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())

        return logData.map {
            val time = formatter.parse("${it[DATE_HEADER]} ${it[TIME_HEADER]}")
            LogEntry(time.time, it)
        }
    }

    companion object {
        private const val DATE_HEADER = "Date"
        private const val TIME_HEADER = "Time"
        private const val GPS_HEADER = "GPS"
    }
}