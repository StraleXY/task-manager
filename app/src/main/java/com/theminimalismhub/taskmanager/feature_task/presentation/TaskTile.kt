package com.theminimalismhub.taskmanager.feature_task.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import com.theminimalismhub.taskmanager.core.consts.Padding
import com.theminimalismhub.taskmanager.feature_task.domain.model.Task
import com.theminimalismhub.taskmanager.utils.TimeConverter
import java.util.Date

@Composable
fun TaskTile(task: Task) {
    val timeToShow = TimeConverter.getFormattedTimeUntil(task.timeStart)

    Row(
        modifier = Modifier
            .padding(horizontal = Padding.ITEM_M)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = timeToShow.first.toString(),
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = timeToShow.second,
                style = MaterialTheme.typography.labelSmall
            )

        }
        Spacer(modifier = Modifier.width(Padding.CONTAINER_M))
        Column {
            Text(
                text = task.title,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                modifier = Modifier.alpha(0.6f),
                text = TimeConverter.getFormattedDate(task.timeStart),
                style = MaterialTheme.typography.labelMedium
            )

        }
    }

}