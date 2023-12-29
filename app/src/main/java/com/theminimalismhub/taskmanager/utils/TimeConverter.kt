package com.theminimalismhub.taskmanager.utils

import java.time.Duration
import java.time.Instant

class TimeConverter {
    companion object {

        fun getMillisUntil(timestamp: Long) : Long {
            return timestamp - Instant.now().toEpochMilli()
        }

        fun getFormattedTimeUntil(timestamp: Long) : String {
            val currentInstant = Instant.now()
            val targetInstant = Instant.ofEpochMilli(timestamp)
            val duration = Duration.between(currentInstant, targetInstant)

            val daysUntil = duration.toDays().toInt()
            if (daysUntil != 0) return "${daysUntil}d"

            val hoursUntil = duration.toHours().toInt()
            if (hoursUntil != 0) return "${hoursUntil}h"

            val minutesUntil = duration.toMinutes().toInt()
            return "${minutesUntil}m"
        }
    }
}