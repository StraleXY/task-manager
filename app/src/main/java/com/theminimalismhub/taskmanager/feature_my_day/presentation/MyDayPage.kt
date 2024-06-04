package com.theminimalismhub.taskmanager.feature_my_day.presentation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.theminimalismhub.taskmanager.core.consts.Padding
import com.theminimalismhub.taskmanager.core.navigation.pages.Page
import com.theminimalismhub.taskmanager.feature_task.presentation.ActiveEventTile
import com.theminimalismhub.taskmanager.feature_task.presentation.EventTile
import com.theminimalismhub.taskmanager.utils.TimeConverter

@Composable
fun MyDayPage(vm: MyDayVM = hiltViewModel()) {

    val context = LocalContext.current
    vm.init(context)

    Page {
        Spacer(modifier = Modifier.height(Padding.SECTION_MASSIVE))
        vm.state.value.tasks.forEachIndexed { index, task ->
            if(index == 0) {
                ActiveEventTile(task = task)
                Spacer(modifier = Modifier.height(Padding.SECTION_MASSIVE))
                Spacer(modifier = Modifier.height(Padding.SECTION))

            }
            else {
                val onSameDay = TimeConverter.areOnSameDay(task.timeStart, vm.state.value.tasks.get(index - 1).timeStart)
                if(!onSameDay) {
                    Spacer(modifier = Modifier.height(Padding.SECTION_LARGE))
                    Text(
                        modifier = Modifier.alpha(0.8f).padding(start = Padding.ITEM_M),
                        text = "Tomorrow's Events:",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Spacer(modifier = Modifier.height(Padding.SECTION))
                }
                EventTile(
                    task = task,
                    previousEndTime = if(!onSameDay) null else vm.state.value.tasks.get(index - 1).timeEnd
                )
                Spacer(modifier = Modifier.height(Padding.SECTION))
            }
        }
    }
}