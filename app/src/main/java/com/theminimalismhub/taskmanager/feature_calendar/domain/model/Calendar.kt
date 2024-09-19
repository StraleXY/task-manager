package com.theminimalismhub.taskmanager.feature_calendar.domain.model

data class Calendar(
    val id: Long = 0L,
    val name: String = "",
    val accountName: String = "",
    val color: String? = null
)
