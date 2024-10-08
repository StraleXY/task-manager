package com.theminimalismhub.taskmanager.feature_settings.presentation

import com.theminimalismhub.taskmanager.feature_assignments.presentation.AssignmentsEvent

sealed class SettingsEvent {
    data class SelectCalendar(val id: Long) : SettingsEvent()
    data class SaveEventCalendars(val ids: List<Long>) : SettingsEvent()
    data class SaveAssignmentsCalendars(val ids: List<Long>) : SettingsEvent()
}