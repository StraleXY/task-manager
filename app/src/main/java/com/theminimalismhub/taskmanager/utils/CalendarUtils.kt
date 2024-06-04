package com.theminimalismhub.taskmanager.utils

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.CalendarContract
import android.util.Log
import com.theminimalismhub.taskmanager.feature_calendar.domain.model.Calendar
import com.theminimalismhub.taskmanager.feature_task.domain.model.Task
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.Date

@SuppressLint("Range")
class CalendarUtils {

    companion object {

        fun getAllCalendars(context: Context): List<Calendar> {
            val contentResolver: ContentResolver = context.contentResolver
            val uri = CalendarContract.Calendars.CONTENT_URI
            val projection = arrayOf(
                CalendarContract.Calendars._ID,
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
                CalendarContract.Calendars.ACCOUNT_NAME
            )

            val cursor: Cursor? = contentResolver.query(
                uri,
                projection,
                null,
                null,
                null
            )

            val calendars = mutableListOf<Calendar>()

            cursor?.use {
                while (it.moveToNext()) {
                    val calendarId = it.getLong(it.getColumnIndex(CalendarContract.Calendars._ID))
                    val calendarName = it.getString(it.getColumnIndex(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME))
                    val accountName = it.getString(it.getColumnIndex(CalendarContract.Calendars.ACCOUNT_NAME))

                    val calendarInfo = Calendar(
                        id = calendarId,
                        name = calendarName,
                        accountName = accountName
                    )

                    calendars.add(calendarInfo)
                }
            }

            return calendars
        }

        fun getCalendarEvents(context: Context, calendarId: String = "24", daysToShow: Long = 30) : List<Task> {
            val contentResolver: ContentResolver = context.contentResolver
            val projection = arrayOf(
                CalendarContract.Events._ID,
                CalendarContract.Events.TITLE,
                CalendarContract.Events.DTSTART,
                CalendarContract.Events.DTEND,
                CalendarContract.Events.CALENDAR_ID,
                CalendarContract.Events.ACCOUNT_NAME,
                CalendarContract.Events.CALENDAR_DISPLAY_NAME,
                CalendarContract.Events.DISPLAY_COLOR
            )

            val currentTime = LocalDateTime.now().toEpochSecond(ZonedDateTime.now().offset) * 1000
            val targetTime = LocalDateTime
                .of(LocalDateTime.now().year, LocalDateTime.now().month, LocalDateTime.now().dayOfMonth, 23, 59)
                .plusDays(daysToShow - 1)
                .toEpochSecond(ZonedDateTime.now().offset) * 1000
            Log.i("CalendarUtils", "from: $currentTime to $targetTime")

            val selection = "${CalendarContract.Events.DTEND} > ? AND ${CalendarContract.Events.DTSTART} < ? AND ${CalendarContract.Events.CALENDAR_ID} == ?"
            val selectionArgs = arrayOf(currentTime.toString(), targetTime.toString(), calendarId)

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
                    try {
                        val eventId = it.getLong(it.getColumnIndex(CalendarContract.Events._ID))
                        val title = it.getString(it.getColumnIndex(CalendarContract.Events.TITLE))
                        val startTime = it.getLong(it.getColumnIndex(CalendarContract.Events.DTSTART))
                        val endTime = it.getLong(it.getColumnIndex(CalendarContract.Events.DTEND))
                        val name = it.getString(it.getColumnIndex(CalendarContract.Events.ACCOUNT_NAME))
                        val calendarId = it.getLong(it.getColumnIndex(CalendarContract.Events.CALENDAR_ID))
                        val calendarName = it.getString(it.getColumnIndex(CalendarContract.Events.CALENDAR_DISPLAY_NAME))
                        val color = it.getString(it.getColumnIndex(CalendarContract.Events.DISPLAY_COLOR))

                        tasks.add(Task(
                            id = eventId,
                            title = title,
                            timeStart = startTime,
                            timeEnd = endTime,
                            calendarId = calendarId,
                            calendarName = calendarName,
                            color = color
                        ))
                    }
                    catch (ex: Exception) {
                        Log.e("CalendarUtils Exception", ex.stackTraceToString())
                    }
                }
            }

            return tasks.toList()
        }

        fun getTodayTasks(context: Context, calendars: List<String>) : List<Task> {
            var task = mutableListOf<Task>()
            calendars.forEach {
                task.addAll(getCalendarEvents(context, it, 2))
            }
            return task
        }
    }
}