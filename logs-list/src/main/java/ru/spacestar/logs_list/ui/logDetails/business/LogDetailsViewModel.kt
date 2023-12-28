package ru.spacestar.logs_list.ui.logDetails.business

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import ru.spacestar.core.utils.StringExtensions.decodeFromUrl
import ru.spacestar.logs_list.navigation.LogsListApiImpl
import java.io.File
import javax.inject.Inject

@HiltViewModel
internal class LogDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    @ApplicationContext context: Context
) : AndroidViewModel(context as Application), ContainerHost<LogDetailsState, Any> {

    override val container = container<LogDetailsState, Any>(LogDetailsState(), savedStateHandle = savedStateHandle) {
        val uri: String = checkNotNull(savedStateHandle[LogsListApiImpl.DETAILS_ARG_URI])
        val name = File(uri.decodeFromUrl).nameWithoutExtension
        reduce { LogDetailsState(name) }
    }
}