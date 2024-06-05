package com.theminimalismhub.taskmanager.feature_calendar.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.theminimalismhub.taskmanager.core.consts.Padding
import com.theminimalismhub.taskmanager.feature_calendar.domain.model.Calendar

@Composable
fun CalendarTile(
    calendar: Calendar,
    onClick: (Calendar) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = Padding.ITEM_M)
            .clickable { onClick(calendar) }
    ) {
        Text(text = calendar.name)
        Text(
            modifier = Modifier.alpha(0.6f),
            text = calendar.accountName,
            style = MaterialTheme.typography.labelMedium
        )
    }
}