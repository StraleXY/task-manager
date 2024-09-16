package com.theminimalismhub.taskmanager.feature_assignments.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.theminimalismhub.taskmanager.core.consts.Corner
import com.theminimalismhub.taskmanager.core.consts.Padding
import com.theminimalismhub.taskmanager.core.navigation.pages.Page
import com.theminimalismhub.taskmanager.feature_calendar.presentation.composables.CalendarTile
import com.theminimalismhub.taskmanager.feature_task.presentation.TaskTile
import com.theminimalismhub.taskmanager.utils.TimeConverter

@Composable
fun AssignmentsPage(vm: AssignmentsVM = hiltViewModel()) {

    val context = LocalContext.current
    vm.init(context)

    Column {
        Spacer(modifier = Modifier.height(if(vm.state.value.tasks.isEmpty()) Padding.SECTION else Padding.SECTION_MASSIVE))
        vm.state.value.tasks.sortedBy { it.timeStart } .forEach { task ->
            TaskTile(task = task)
            Spacer(modifier = Modifier.height(Padding.SECTION))
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
                        text = "No Assignments",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}