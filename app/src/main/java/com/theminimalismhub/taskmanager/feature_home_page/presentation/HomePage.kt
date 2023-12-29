package com.theminimalismhub.taskmanager.feature_home_page.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.theminimalismhub.taskmanager.core.consts.Padding
import com.theminimalismhub.taskmanager.core.navigation.pages.Page
import com.theminimalismhub.taskmanager.feature_task.presentation.TaskTile
import com.theminimalismhub.taskmanager.utils.CalendarUtils
import com.theminimalismhub.taskmanager.utils.TimeConverter

@Composable
fun HomePage(vm: HomePageVM = hiltViewModel()) {

    val context = LocalContext.current
    vm.init(context)

    Page {

        Spacer(modifier = Modifier.height(Padding.SECTION))
        Text(
            text = "Task Manager",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            modifier = Modifier.alpha(0.6f).padding(start = 2.dp),
            text = TimeConverter.getCurrentFormattedDate(),
            style = MaterialTheme.typography.labelMedium.copy(fontSize = 14.sp)
        )

        Spacer(modifier = Modifier.height(Padding.SECTION_LARGE))
        vm.state.value.tasks.forEach { task ->
            TaskTile(task = task)
            Spacer(modifier = Modifier.height(Padding.SECTION))
        }
    }
}