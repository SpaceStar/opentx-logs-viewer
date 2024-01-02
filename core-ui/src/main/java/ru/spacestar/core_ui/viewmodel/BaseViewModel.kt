package ru.spacestar.core_ui.viewmodel

import android.app.Application
import android.content.Context
import android.os.Parcelable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.SettingsBuilder
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import org.orbitmvi.orbit.syntax.simple.postSideEffect as postSideEffectInternal
import org.orbitmvi.orbit.viewmodel.container as containerInternal

abstract class BaseViewModel<S: Parcelable, SF>(
    appContext: Context,
    private val savedStateHandle: SavedStateHandle
) : AndroidViewModel(appContext as Application), ContainerHost<S, BaseSideEffect<SF>> {

    protected val context: Context
        get() = getApplication()

    protected fun container(
        initialState: S,
        buildSettings: SettingsBuilder.() -> Unit = {},
        onCreate: (suspend SimpleSyntax<S, BaseSideEffect<SF>>.() -> Unit)? = null
    ) = containerInternal(initialState, savedStateHandle, buildSettings, onCreate)

    protected suspend fun SimpleSyntax<S, BaseSideEffect<SF>>.postSideEffect(sideEffect: SF) {
        postSideEffect(BaseSideEffect.Other(sideEffect))
    }

    protected suspend fun SimpleSyntax<S, BaseSideEffect<SF>>.postSideEffect(sideEffect: BaseSideEffect<SF>) {
        postSideEffectInternal(sideEffect)
    }

}