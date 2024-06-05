package com.theminimalismhub.taskmanager.feature_calendar.domain.use_cases

import androidx.compose.ui.graphics.Color

interface CalendarPref {
    fun getSelectedCalendarId() : Long
    fun saveSelectedCalendarId(id: Long)
}