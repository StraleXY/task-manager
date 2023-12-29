package com.theminimalismhub.taskmanager.feature_home_page.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.theminimalismhub.taskmanager.core.navigation.pages.Page
import com.theminimalismhub.taskmanager.utils.CalendarUtils
import com.theminimalismhub.taskmanager.utils.TimeConverter

@Composable
fun HomePage(vm: HomePageVM = hiltViewModel()) {

    val context = LocalContext.current
    vm.init(context)

    Page {
        Text(
            text = "Task Manager",
            style = MaterialTheme.typography.headlineMedium
        )
        vm.state.value.tasks.forEach { task ->
            Text(text = TimeConverter.getFormattedTimeUntil(task.timeStart))
            Text(text = task.title)
        }
    }
}