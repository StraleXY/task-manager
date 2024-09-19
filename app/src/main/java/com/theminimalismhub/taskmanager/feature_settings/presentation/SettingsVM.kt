package com.theminimalismhub.taskmanager.feature_settings.presentation

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.theminimalismhub.taskmanager.Pages
import com.theminimalismhub.taskmanager.core.navigation.NavigationController
import com.theminimalismhub.taskmanager.feature_assignments.presentation.AssignmentsEvent
import com.theminimalismhub.taskmanager.feature_assignments.presentation.AssignmentsState
import com.theminimalismhub.taskmanager.feature_calendar.domain.use_cases.CalendarPref
import com.theminimalismhub.taskmanager.utils.CalendarUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsVM @Inject constructor(
    val navigationController: NavigationController,
    val calendarPref: CalendarPref,
    private val application: Application
) : AndroidViewModel(application = application) {
    private val _state = mutableStateOf(SettingsState())
    val state: State<SettingsState> = _state

    init {
        navigationController.registerPage(Pages.SETTINGS_KEY) {
            _state.value = _state.value.copy(
                calendars = CalendarUtils.getAllCalendars(application as Context),
                eventsCalendars = calendarPref.getSelectedEventCalendars(),
                assignmentsCalendars = calendarPref.getSelectedAssignmentsCalendars()
            )
            Log.i("SAVE", "Saved: ${_state.value.eventsCalendars}")
        }
    }

    fun onEvent(event: SettingsEvent) {
        when(event) {
            is SettingsEvent.SelectCalendar -> {
                _state.value = _state.value.copy(
                    selectedCalendar = _state.value.calendars.first { it.id == event.id }
                )
            }
            is SettingsEvent.SaveEventCalendars -> {
                calendarPref.setSelectedEventCalendars(event.ids)
                _state.value = _state.value.copy(eventsCalendars = event.ids)
            }
            is SettingsEvent.SaveAssignmentsCalendars -> {
                calendarPref.setSelectedAssignmentsCalendars(event.ids)
                _state.value = _state.value.copy(assignmentsCalendars = event.ids)
            }
        }
    }
}