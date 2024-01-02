package ru.spacestar.logs_list.ui.mapSettings.business

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.spacestar.core_ui.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MapSettingsViewModel @Inject constructor(
    @ApplicationContext context: Context,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<MapSettingsState, Any>(context, savedStateHandle) {

    override val container = container(MapSettingsState())
}