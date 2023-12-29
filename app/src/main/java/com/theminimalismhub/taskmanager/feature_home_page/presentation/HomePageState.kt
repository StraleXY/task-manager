package com.theminimalismhub.taskmanager.feature_home_page.presentation

import com.theminimalismhub.taskmanager.feature_task.domain.model.Task

data class HomePageState(
    val tasks: List<Task> = emptyList()
)
