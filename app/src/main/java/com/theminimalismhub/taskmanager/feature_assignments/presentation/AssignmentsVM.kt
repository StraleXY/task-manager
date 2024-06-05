package com.theminimalismhub.taskmanager.feature_assignments.presentation

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.theminimalismhub.taskmanager.core.navigation.NavigationController
import com.theminimalismhub.taskmanager.utils.CalendarUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AssignmentsVM @Inject constructor(
    val navigationController: NavigationController
) : ViewModel() {
    private val _state = mutableStateOf(AssignmentsState())
    val state: State<AssignmentsState> = _state
    private lateinit var context: Context

    fun init(context: Context) {
        this.context = context
        _state.value = _state.value.copy(
            tasks = CalendarUtils.getCalendarEvents(context, daysToShow = 365)
        )
    }

    fun onEvent(event: AssignmentsEvent) {

    }
}