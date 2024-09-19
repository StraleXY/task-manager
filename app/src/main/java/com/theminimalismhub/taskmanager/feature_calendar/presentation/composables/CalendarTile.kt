package com.theminimalismhub.taskmanager.feature_calendar.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.theminimalismhub.taskmanager.core.consts.Padding
import com.theminimalismhub.taskmanager.feature_calendar.domain.model.Calendar

@Composable
fun CalendarTile(
    calendar: Calendar,
    isSelected: Boolean,
    onCheckedChange: (Calendar) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(40.dp)
                    .background(Color(calendar.color?.toInt() ?: 0), RoundedCornerShape(100))
            )
            Spacer(modifier = Modifier.width(Padding.ITEM_L))
            Column {
                Text(
                    text = calendar.name
                )
                Text(
                    modifier = Modifier.alpha(0.6f),
                    text = calendar.accountName,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
        Checkbox(checked = isSelected, onCheckedChange = { onCheckedChange(calendar) })
    }
}