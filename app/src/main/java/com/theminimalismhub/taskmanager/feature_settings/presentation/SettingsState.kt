package com.theminimalismhub.taskmanager.feature_settings.presentation

import com.theminimalismhub.taskmanager.feature_calendar.domain.model.Calendar

data class SettingsState(
    val selectedCalendar: Calendar = Calendar(name = "Select Calendar"),
    val calendars: List<Calendar> = emptyList()
)
