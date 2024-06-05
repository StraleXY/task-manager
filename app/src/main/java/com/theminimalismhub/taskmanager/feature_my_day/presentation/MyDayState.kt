package com.theminimalismhub.taskmanager.feature_my_day.presentation

import com.theminimalismhub.taskmanager.feature_task.domain.model.Task

data class MyDayState(
    val tasks: List<Task> = emptyList()
)
