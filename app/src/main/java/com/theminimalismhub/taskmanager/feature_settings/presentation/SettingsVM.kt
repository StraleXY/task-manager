package com.theminimalismhub.taskmanager.feature_settings.presentation

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.theminimalismhub.taskmanager.core.navigation.NavigationController
import com.theminimalismhub.taskmanager.feature_assignments.presentation.AssignmentsEvent
import com.theminimalismhub.taskmanager.feature_assignments.presentation.AssignmentsState
import com.theminimalismhub.taskmanager.utils.CalendarUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsVM @Inject constructor(
    val navigationController: NavigationController
) : ViewModel() {
    private val _state = mutableStateOf(SettingsState())
    val state: State<SettingsState> = _state
    private lateinit var context: Context

    fun init(context: Context) {
        this.context = context
        _state.value = _state.value.copy(
            calendars = CalendarUtils.getAllCalendars(context)
        )
    }

    fun onEvent(event: SettingsEvent) {
        when(event) {
            is SettingsEvent.SelectCalendar -> {
                Log.i("Calendar", event.id.toString())
                _state.value = _state.value.copy(
                    selectedCalendar = _state.value.calendars.first { it.id == event.id }
                )
            }
        }
    }
}