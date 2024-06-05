package com.theminimalismhub.taskmanager.feature_task.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LifecycleResumeEffect
import com.theminimalismhub.taskmanager.R
import com.theminimalismhub.taskmanager.core.consts.Padding
import com.theminimalismhub.taskmanager.feature_task.domain.model.Task
import com.theminimalismhub.taskmanager.utils.TimeConverter
import kotlinx.coroutines.delay
import java.util.Date
import kotlin.time.Duration.Companion.seconds

@Composable
fun EventTile(
    task: Task,
    previousEndTime: Long?
) {

    var timeToShow by remember { mutableStateOf(if(previousEndTime == null) TimeConverter.getFormattedTime(task.timeStart) else TimeConverter.getPreciseFormattedTimeUntil(task.timeStart, previousEndTime)) }
    LaunchedEffect(Unit) {
        while (previousEndTime != null) {
            delay(1.seconds * 10)
            timeToShow = TimeConverter.getPreciseFormattedTimeUntil(task.timeStart, previousEndTime)
        }
    }

    Row(
        modifier = Modifier
            .padding(horizontal = Padding.ITEM_M)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(5.dp)
                .height(44.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(Color(task.color?.toInt() ?: 0))
        )
        if(false) Column(modifier = Modifier.width(60.dp)) {

            if (timeToShow != null) Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = timeToShow!!,
                    style = MaterialTheme.typography.headlineMedium,
                    fontSize = 24.sp
                )
                Text(
                    modifier = Modifier.alpha(0.7f),
                    text = if(previousEndTime == null) "START TIME" else "AFTERWARD",
                    style = MaterialTheme.typography.labelSmall
                )
            }

            else Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.alpha(0.4f),
                    text = "00:00",
                    style = MaterialTheme.typography.headlineMedium,
                    fontSize = 24.sp
                )
                Text(
                    modifier = Modifier.alpha(0.7f),
                    text = "SEQUENTIAL",
                    style = MaterialTheme.typography.labelSmall
                )
            }

        }

        Spacer(modifier = Modifier.width(Padding.CONTAINER_S))
        Column {
            Text(
                modifier = Modifier.alpha(0.6f),
                text = TimeConverter.getFormattedDateInterval(task.timeStart, task.timeEnd),
                style = MaterialTheme.typography.labelMedium
            )
            Spacer(modifier = Modifier.height(Padding.ITEM_S))
            Text(
                text = task.title,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }

}
