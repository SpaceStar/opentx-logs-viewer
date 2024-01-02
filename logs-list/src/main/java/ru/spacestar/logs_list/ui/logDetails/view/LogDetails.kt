package ru.spacestar.logs_list.ui.logDetails.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import org.orbitmvi.orbit.compose.collectAsState
import ru.spacestar.core_ui.view.BaseAppBarScreen
import ru.spacestar.logs_list.ui.logDetails.business.LogDetailsViewModel
import ru.spacestar.logs_list.ui.logEntry.LogEntryInfo
import ru.spacestar.logs_list.ui.timeline.Timeline

@Composable
internal fun LogDetails(
    navController: NavController,
    viewModel: LogDetailsViewModel
) {
    val state by viewModel.collectAsState()

    BaseAppBarScreen(
        navController = navController,
        title = state.logName
    ) { paddingValues ->
        if (state.loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                LogEntryInfo(
                    modifier = Modifier
                        .weight(0.75f),
                    logEntry = state.details.getOrNull(state.selectedEntry))
                Timeline(
                    modifier = Modifier
                        .weight(0.25f),
                    minTime = state.details.firstOrNull()?.time,
                    maxTime = state.details.lastOrNull()?.time,
                    onTimeSelect = {
                        viewModel.selectEntry(it)
                    }
                )
            }
        }
    }
}