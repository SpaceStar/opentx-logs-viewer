package ru.spacestar.logs_list.ui.logsList.view

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.spacestar.core_ui.view.BaseAppBarScreen
import ru.spacestar.core_ui.viewmodel.BaseSideEffect
import ru.spacestar.logs_list.R
import ru.spacestar.logs_list.ui.logItem.LogItem
import ru.spacestar.logs_list.ui.logsList.business.LogsListSideEffect
import ru.spacestar.logs_list.ui.logsList.business.LogsListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun LogsList(
    navController: NavController,
    viewModel: LogsListViewModel
) {
    val context = LocalContext.current
    val state by viewModel.collectAsState()
    val contentLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {
            viewModel.addLog(it)
        })

    viewModel.collectSideEffect(sideEffect = {
        when (it) {
            is BaseSideEffect.Navigate -> navController.navigate(it.route)
            is BaseSideEffect.Other -> {
                when (val sf = it.sf) {
                    is LogsListSideEffect.ShowMessage -> {
                        Toast.makeText(context, sf.msgRes, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    })

    BaseAppBarScreen(
        navController = navController,
        isBackEnabled = false,
        fab = {
            PlainTooltipBox(
                tooltip = { Text(text = stringResource(id = R.string.add_log_button)) }
            ) {
                FloatingActionButton(
                    modifier = Modifier.tooltipAnchor(),
                    onClick = {
                        contentLauncher.launch("text/comma-separated-values")
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(id = R.string.add_log_button)
                    )
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(state.logs) {
                LogItem(
                    modifier = Modifier.fillMaxWidth(),
                    state = it,
                    onClick = { uri -> viewModel.selectLog(uri) },
                    onRemove = { uri ->  viewModel.deleteLog(uri) }
                )
            }
        }
    }
}