package com.theminimalismhub.taskmanager.core.local_storage

import android.content.Context
import com.theminimalismhub.taskmanager.feature_calendar.domain.use_cases.CalendarPref

class LocalPref constructor(
    private val context: Context
) : CalendarPref {

    private val PREFERENCES_NAME: String = "tasker_prefs"
    private object KEYS {
        const val SELECTED_CALENDAR_ID: String = "selected_calendar_id"
    }

    override fun getSelectedCalendarId(): Long = LocalStorage.getLong(context, PREFERENCES_NAME, KEYS.SELECTED_CALENDAR_ID, -999L)
    override fun saveSelectedCalendarId(id: Long) = LocalStorage.putLong(context, PREFERENCES_NAME, KEYS.SELECTED_CALENDAR_ID, id)

}