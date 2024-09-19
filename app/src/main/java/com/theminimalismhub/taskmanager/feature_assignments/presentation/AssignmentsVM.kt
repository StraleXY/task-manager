package com.theminimalismhub.taskmanager.feature_assignments.presentation

import android.app.Application
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.theminimalismhub.taskmanager.core.navigation.NavigationController
import com.theminimalismhub.taskmanager.feature_calendar.domain.use_cases.CalendarPref
import com.theminimalismhub.taskmanager.utils.CalendarUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AssignmentsVM @Inject constructor(
    val navigationController: NavigationController,
    private val calendarPref: CalendarPref,
    private val application: Application
) : AndroidViewModel(application = application) {
    private val _state = mutableStateOf(AssignmentsState())
    val state: State<AssignmentsState> = _state

    fun load() {
        _state.value = _state.value.copy(
            tasks = CalendarUtils.getAssignments((application as Context), calendarPref.getSelectedAssignmentsCalendars().map { it.toString() }, allDay = listOf(true, false)).sortedBy { it.timeStart }
        )
    }
}