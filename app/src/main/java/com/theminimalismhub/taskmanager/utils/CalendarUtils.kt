package com.theminimalismhub.taskmanager.utils

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.CalendarContract
import android.util.Log
import com.theminimalismhub.taskmanager.feature_task.domain.model.Task
import java.util.Date

@SuppressLint("Range")
class CalendarUtils {

    companion object {

        fun getCalendarEvents(context: Context, calendarId: String = "24") : List<Task> {
            val contentResolver: ContentResolver = context.contentResolver
            val projection = arrayOf(
                CalendarContract.Events._ID,
                CalendarContract.Events.TITLE,
                CalendarContract.Events.DTSTART,
                CalendarContract.Events.DTEND,
                CalendarContract.Events.CALENDAR_ID,
                CalendarContract.Events.ACCOUNT_NAME,
                CalendarContract.Events.CALENDAR_DISPLAY_NAME
            )

            val currentTime = System.currentTimeMillis()
            val selection = "${CalendarContract.Events.DTEND} > ? AND ${CalendarContract.Events.CALENDAR_ID} == ?"
            val selectionArgs = arrayOf(currentTime.toString(), calendarId)

            val cursor: Cursor? = contentResolver.query(
                CalendarContract.Events.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null
            )

            val tasks = mutableListOf<Task>()

            cursor?.use {
                while (it.moveToNext()) {
                    val eventId = it.getLong(it.getColumnIndex(CalendarContract.Events._ID))
                    val title = it.getString(it.getColumnIndex(CalendarContract.Events.TITLE))
                    val startTime = it.getLong(it.getColumnIndex(CalendarContract.Events.DTSTART))
                    val endTime = it.getLong(it.getColumnIndex(CalendarContract.Events.DTEND))
                    val name = it.getString(it.getColumnIndex(CalendarContract.Events.ACCOUNT_NAME))
                    val calendarId = it.getLong(it.getColumnIndex(CalendarContract.Events.CALENDAR_ID))
                    val calendarName = it.getString(it.getColumnIndex(CalendarContract.Events.CALENDAR_DISPLAY_NAME))

                    tasks.add(Task(
                        id = eventId,
                        title = title,
                        timeStart = startTime,
                        timeEnd = endTime,
                        calendarId = calendarId,
                        calendarName = calendarName
                    ))
                }
            }

            return tasks.toList()
        }
    }
}