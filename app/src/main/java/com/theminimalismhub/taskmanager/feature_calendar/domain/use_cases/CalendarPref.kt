package com.theminimalismhub.taskmanager.feature_calendar.domain.use_cases

interface CalendarPref {
    fun getSelectedCalendarId() : Long
    fun saveSelectedCalendarId(id: Long)

    fun getSelectedEventCalendars() : List<Long>
    fun setSelectedEventCalendars(calendarIds: List<Long>)

    fun getSelectedAssignmentsCalendars() : List<Long>
    fun setSelectedAssignmentsCalendars(calendarIds: List<Long>)

}