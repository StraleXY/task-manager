package com.theminimalismhub.taskmanager.core.local_storage

import android.content.Context
import com.theminimalismhub.taskmanager.feature_calendar.domain.use_cases.CalendarPref

class LocalPref constructor(
    private val context: Context
) : CalendarPref {

    private val PREFERENCES_NAME: String = "tasker_prefs"
    private object KEYS {
        const val EVENT_CALENDARS: String = "event_calendars"
        const val ASSIGNMENTS_CALENDARS: String = "assignments_calendars"
    }

    override fun getSelectedEventCalendars(): List<Long> {
        val ids = LocalStorage.getString(context, PREFERENCES_NAME, KEYS.EVENT_CALENDARS)
        if(ids.isEmpty()) return emptyList()
        return ids.split(",").map { it.toLong() }
    }
    override fun setSelectedEventCalendars(calendarIds: List<Long>) = LocalStorage.putString(context, PREFERENCES_NAME, KEYS.EVENT_CALENDARS, calendarIds.joinToString(","))

    override fun getSelectedAssignmentsCalendars(): List<Long> {
        val ids = LocalStorage.getString(context, PREFERENCES_NAME, KEYS.ASSIGNMENTS_CALENDARS)
        if(ids.isEmpty()) return emptyList()
        return ids.split(",").map { it.toLong() }
    }
    override fun setSelectedAssignmentsCalendars(calendarIds: List<Long>) = LocalStorage.putString(context, PREFERENCES_NAME, KEYS.ASSIGNMENTS_CALENDARS, calendarIds.joinToString(","))


}