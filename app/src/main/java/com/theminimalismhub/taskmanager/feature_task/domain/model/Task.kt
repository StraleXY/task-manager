package com.theminimalismhub.taskmanager.feature_task.domain.model

data class Task(
    val id: Long,
    val title: String = "",
    val timeStart: Long = 0L,
    val timeEnd: Long = 0L,
    val calendarId: Long = 0L,
    val calendarName: String = "",
    val color: String? = null
)
