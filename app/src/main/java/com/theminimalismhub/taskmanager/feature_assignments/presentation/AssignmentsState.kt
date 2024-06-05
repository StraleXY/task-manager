package com.theminimalismhub.taskmanager.feature_assignments.presentation

import com.theminimalismhub.taskmanager.feature_calendar.domain.model.Calendar
import com.theminimalismhub.taskmanager.feature_task.domain.model.Task

data class AssignmentsState(
    val tasks: List<Task> = emptyList()
)
