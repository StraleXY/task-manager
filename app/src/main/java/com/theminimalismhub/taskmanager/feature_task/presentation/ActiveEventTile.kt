package com.theminimalismhub.taskmanager.feature_task.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.theminimalismhub.taskmanager.core.consts.Padding
import com.theminimalismhub.taskmanager.feature_task.domain.model.Task
import com.theminimalismhub.taskmanager.utils.TimeConverter
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun ActiveEventTile(
    task: Task,
    onEnd: () -> Unit
) {
    var isUpcoming by remember { mutableStateOf(task.timeStart > System.currentTimeMillis()) }
    var timeToShow by remember { mutableStateOf(TimeConverter.getPreciseFormattedTimeUntil(if(isUpcoming) task.timeStart else task.timeEnd, inclusive = true)) }
    LaunchedEffect(task) {
        while (true) {
            delay(1.seconds)
            isUpcoming = task.timeStart > System.currentTimeMillis()
            timeToShow = TimeConverter.getPreciseFormattedTimeUntil(if(isUpcoming) task.timeStart else task.timeEnd, inclusive = true)
            if(timeToShow == "00:00" && !isUpcoming) {
                onEnd()
                break
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(horizontal = Padding.ITEM_M)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = task.title,
            style = MaterialTheme.typography.headlineMedium,
            color = Color(task.color?.toInt() ?: 0)
        )
        Spacer(modifier = Modifier.height(Padding.ITEM_S))
        Text(
            modifier = Modifier.alpha(0.6f),
            text = TimeConverter.getFormattedDateInterval(task.timeStart, task.timeEnd),
            style = MaterialTheme.typography.labelMedium
        )
        Spacer(modifier = Modifier.height(Padding.ITEM_M))
        Text(
            text = timeToShow!!,
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 65.sp
        )
        Spacer(modifier = Modifier.height(Padding.ITEM_S))
        Text(
            modifier = Modifier.alpha(0.8f),
            text = if(isUpcoming) "UPCOMING" else "ONGOING",
            style = MaterialTheme.typography.labelMedium,
        )
    }
}