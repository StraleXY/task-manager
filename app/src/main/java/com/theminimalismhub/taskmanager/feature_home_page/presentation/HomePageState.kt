package com.theminimalismhub.taskmanager.feature_home_page.presentation

import com.theminimalismhub.taskmanager.feature_calendar.domain.model.Calendar
import com.theminimalismhub.taskmanager.feature_task.domain.model.Task

data class HomePageState(
    val selectedCalendar: Calendar = Calendar(name = "Select Calendar"),
    val calendars: List<Calendar> = emptyList(),
    val tasks: List<Task> = emptyList()
)
