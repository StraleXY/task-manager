package com.theminimalismhub.taskmanager.feature_my_day.presentation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import com.theminimalismhub.taskmanager.Pages
import com.theminimalismhub.taskmanager.core.OnLifecycleEvent
import com.theminimalismhub.taskmanager.core.consts.Padding
import com.theminimalismhub.taskmanager.feature_task.presentation.ActiveEventTile
import com.theminimalismhub.taskmanager.feature_task.presentation.BetweenEvents
import com.theminimalismhub.taskmanager.feature_task.presentation.EventTile
import com.theminimalismhub.taskmanager.utils.TimeConverter

@Composable
fun MyDayPage(vm: MyDayVM = hiltViewModel()) {

    OnLifecycleEvent { owner, event ->
        when(event) {
            Lifecycle.Event.ON_START -> vm.load()
            Lifecycle.Event.ON_RESUME -> vm.load()
            else -> { }
        }
    }

    Column {
        Spacer(modifier = Modifier.height(if(vm.state.value.tasks.isEmpty()) 0.dp else Padding.SECTION_MASSIVE))
        vm.state.value.tasks.forEachIndexed { index, task ->
            if(index == 0) {
                ActiveEventTile(task = task) { vm.load() }
                Spacer(modifier = Modifier.height(Padding.SECTION_MASSIVE))
            }
            else {
                val onSameDay = TimeConverter.areOnSameDay(task.timeStart, vm.state.value.tasks.get(index - 1).timeStart)
                if(!onSameDay) {
                    Text(
                        modifier = Modifier
                            .alpha(0.8f)
                            .padding(start = Padding.ITEM_M, top = Padding.SECTION_LARGE),
                        text = "Tomorrow's Events:",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Spacer(modifier = Modifier.height(Padding.SECTION))
                    BetweenEvents(
                        previousTime = null,
                        nextTime = task.timeStart
                    )
                }
                else {
                    BetweenEvents(
                        previousTime = vm.state.value.tasks.get(index - 1).timeEnd,
                        nextTime = task.timeStart,
                        hideTop = index == 1
                    )
                }
                EventTile(
                    task = task,
                    previousEndTime = if(!onSameDay) null else vm.state.value.tasks.get(index - 1).timeEnd
                )
//                Spacer(modifier = Modifier.height(Padding.SECTION))
            }
        }
        if(vm.state.value.tasks.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .drawBehind {
                        val strokeWidth = 1.dp.toPx()
                        val dashLength = 10.dp.toPx()
                        val dashSpacing = 6.dp.toPx()

                        // Create a path effect for dashes
                        val pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashLength, dashSpacing), 0f)

                        // Draw a rounded dashed rectangle
                        drawRoundRect(
                            color = Color.DarkGray, // Border color
                            size = size,
                            style = Stroke(width = strokeWidth, pathEffect = pathEffect),
                            cornerRadius = CornerRadius(16.dp.toPx()) // For rounded corners (optional)
                        )
                    }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No Events",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}