package com.theminimalismhub.taskmanager.utils

import java.text.SimpleDateFormat
import java.time.Duration
import java.time.Instant
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

        fun getFormattedDate(timestamp: Long) : String {
            val dateFormat = SimpleDateFormat("EEE, MMM dd - hh:mm", Locale.US)
            return dateFormat.format(Date(timestamp)).replace("-", "at")
        }

        fun getCurrentFormattedDate() : String {
            val dateFormat = SimpleDateFormat("EEE, MMM dd", Locale.US)
            return dateFormat.format(Date(getCurrentMilli())).replace("-", "at")
        }

        fun getFormattedTimeUntil(timestamp: Long) : Pair<Int, String> {
            val currentInstant = Instant.now()
            val targetInstant = Instant.ofEpochMilli(timestamp)
            val duration = Duration.between(currentInstant, targetInstant)

            val daysUntil = duration.toDays().toInt()
            if (daysUntil != 0) return Pair(daysUntil, "DAYS")

            val hoursUntil = duration.toHours().toInt()
            if (hoursUntil != 0) return Pair(hoursUntil, "HOURS")

            val minutesUntil = duration.toMinutes().toInt()
            return Pair(minutesUntil, "MIN")
        }
    }
}