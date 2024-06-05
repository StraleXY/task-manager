package com.theminimalismhub.taskmanager.feature_task.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.theminimalismhub.taskmanager.core.consts.Padding
import com.theminimalismhub.taskmanager.utils.TimeConverter

@Composable
fun BetweenEvents(
    previousTime: Long,
    nextTime: Long
) {

    val timeToShow by remember { mutableStateOf(TimeConverter.getPreciseFormattedTimeUntil(nextTime, previousTime, shortFormat = false)) }

    Row(
        modifier = Modifier
            .padding(horizontal = Padding.ITEM_M, vertical = Padding.ITEM_S)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(1.5.dp)
                .height(if(timeToShow == null) 20.dp else 60.dp)
                .offset(1.5.dp, 0.dp)
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
        timeToShow?.let {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp)
                        .offset((-9).dp, 0.dp)
                        .border(BorderStroke(Padding.ITEM_S, MaterialTheme.colorScheme.background), CircleShape)
                        .background(MaterialTheme.colorScheme.onTertiary, CircleShape)
                )
                Text(
                    modifier = Modifier,
                    text = "$timeToShow Later",
                    style = MaterialTheme.typography.displaySmall,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    }
}