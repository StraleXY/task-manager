package com.theminimalismhub.taskmanager.feature_my_day.presentation

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
class MyDayVM @Inject constructor(
    val navigationController: NavigationController,
    private val calendarPref: CalendarPref,
    private val application: Application
) : AndroidViewModel(application = application) {

    private val _state = mutableStateOf(MyDayState())
    val state: State<MyDayState> = _state

    fun load() {
        _state.value = _state.value.copy(
            tasks = CalendarUtils.getTodayTasks((application as Context), calendarPref.getSelectedEventCalendars().map { it.toString() }).sortedBy { it.timeStart }
        )
    }
}