package com.theminimalismhub.taskmanager.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.util.Date
import java.util.Locale

class TimeConverter {
    companion object {

        fun getCurrentMilli() : Long {
            return Instant.now().toEpochMilli()
        }

        fun getMillisUntil(timestamp: Long) : Long {
            return timestamp - getCurrentMilli()
        }

        fun getFormattedDate(timestamp: Long, allDay: Boolean = false) : String {
            val dateFormat =
                if (allDay) SimpleDateFormat("EEE, MMM dd", Locale.US)
                else SimpleDateFormat("EEE, MMM dd - HH:mm", Locale.US)
            return dateFormat.format(Date(timestamp)).replace("-", "at")
        }

        fun getFormattedTime(timestamp: Long) : String {
            val dateFormat = SimpleDateFormat("HH:mm", Locale.US)
            return dateFormat.format(Date(timestamp))
        }

        fun getFormattedDateInterval(from: Long, to: Long) : String {
            val dateFormat = SimpleDateFormat("HH:mm", Locale.US)
            return dateFormat.format(Date(from)) + " - " + dateFormat.format(Date(to))
        }

        fun getCurrentFormattedDate() : String {
            val dateFormat = SimpleDateFormat("EEE, MMM dd", Locale.US)
            return dateFormat.format(Date(getCurrentMilli())).replace("-", "at")
        }

        fun areOnSameDay(one: Long, two: Long) : Boolean {
            val first = LocalDateTime.ofInstant(Instant.ofEpochMilli(one), ZonedDateTime.now().offset)
            val second = LocalDateTime.ofInstant(Instant.ofEpochMilli(two), ZonedDateTime.now().offset)
            return first.year == second.year && first.month == second.month && first.dayOfMonth == second.dayOfMonth
        }

        fun getFormattedTimeUntil(timestamp: Long) : Pair<Int, String> {
            val currentInstant = Instant.now()
            val targetInstant = Instant.ofEpochMilli(timestamp)
            val duration = Duration.between(currentInstant, targetInstant)

            val daysUntil = duration.toDays().toInt()
            if (daysUntil != 0) return Pair(daysUntil + 1, "DAYS")

            val hoursUntil = duration.toHours().toInt()
            if (hoursUntil != 0) return Pair(hoursUntil, "HOURS")

            val minutesUntil = duration.toMinutes().toInt()
            return Pair(minutesUntil, "MIN")
        }

        fun getPreciseFormattedTimeUntil(timestamp: Long, from: Long? = null, shortFormat: Boolean = true, inclusive: Boolean = false) : String? {

            val currentInstant = if(from == null) Instant.now() else Instant.ofEpochMilli(from)
            val targetInstant = Instant.ofEpochMilli(if(inclusive) timestamp + 60000 else timestamp)

            val duration = Duration.between(currentInstant, targetInstant)
            if (duration.isZero) return null

            var formatted = ""

            val hoursUntil = duration.toHours().toInt()
            if (hoursUntil != 0) formatted +=
                if(shortFormat) "%02d:".format(hoursUntil)
                else "$hoursUntil Hours "
            else if(shortFormat) formatted += "00:"

            val minutesUntil = duration.toMinutes().toInt() - hoursUntil * 60
            if (minutesUntil != 0) formatted +=
                if(shortFormat) "%02d".format(minutesUntil)
                else "$minutesUntil Minutes"
            else if (shortFormat) formatted += "00"

            return formatted
        }
    }
}