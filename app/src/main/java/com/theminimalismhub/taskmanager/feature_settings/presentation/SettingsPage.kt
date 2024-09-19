package com.theminimalismhub.taskmanager.feature_settings.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.List
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceTheme.colors
import androidx.hilt.navigation.compose.hiltViewModel
import com.theminimalismhub.taskmanager.core.consts.Padding
import com.theminimalismhub.taskmanager.core.navigation.NavigationController
import com.theminimalismhub.taskmanager.core.navigation.pages.Page
import com.theminimalismhub.taskmanager.feature_calendar.domain.model.Calendar
import com.theminimalismhub.taskmanager.feature_calendar.presentation.composables.CalendarTile

@Composable
fun SettingsPage(vm: SettingsVM = hiltViewModel()) {
    Page {
        Spacer(modifier = Modifier.height(Padding.SECTION))

        Text(
            modifier = Modifier.padding(start = Padding.ITEM_S),
            text = "Settings",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(Padding.SECTION_LARGE))

        SettingsSegment("CALENDARS")
        Spacer(modifier = Modifier.height(Padding.ITEM_L))
        SettingsTile(
            title = "Event Calendars",
            description = "Chose which calendars will be displayed on 'Events' page. This page shows time until start/end of the upcoming events in the next 2 days.",
            icon = Icons.Default.DateRange
        ) {
            selectCalendarsDialog(
                title = "Pick Event Calendars",
                description = "These calendars will be displayed on 'Events' page.",
                nc = vm.navigationController,
                calendars = vm.state.value.calendars,
                selection = vm.state.value.eventsCalendars
            ) { vm.onEvent(SettingsEvent.SaveEventCalendars(it)) }
        }
        Spacer(modifier = Modifier.height(Padding.ITEM_L))
        SettingsTile(
            title = "Assignments Calendars",
            description = "Chose which calendars will be displayed on 'Assignments' page. This page shows time remaining to complete each assignment for the next 365 days.",
            icon = Icons.Default.List
        ) {
            selectCalendarsDialog(
                title = "Pick Assignment Calendars",
                description = "These calendars will be displayed on 'Assignments' page.",
                nc = vm.navigationController,
                calendars = vm.state.value.calendars,
                selection = vm.state.value.assignmentsCalendars
            ) { vm.onEvent(SettingsEvent.SaveAssignmentsCalendars(it)) }
        }

    }
}

private fun selectCalendarsDialog(
    title: String,
    description: String,
    nc: NavigationController,
    calendars: List<Calendar>,
    selection: List<Long>,
    save: (List<Long>) -> Unit,
) {

    var staged: List<Long> = emptyList()

    nc.dialog.action(
        title = title,
        description = description,
        actionLabel = "SAVE",
        onAction = { save(staged) }
    ) {

        val mSelection: MutableList<Long> = remember { mutableStateListOf(*selection.toTypedArray()) }

        Spacer(modifier = Modifier.height(Padding.ITEM_M))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(0.dp, 450.dp)
        ) {
            items(calendars.sortedBy { it.accountName }){
                CalendarTile(
                    calendar = it,
                    isSelected = mSelection.contains(it.id),
                    onCheckedChange = {
                        if (mSelection.contains(it.id)) mSelection.remove(it.id)
                        else mSelection.add(it.id)
                        staged = mSelection.toList()
                    }
                )
                Spacer(modifier = Modifier.height(Padding.ITEM_M))
            }
        }
    }
}


@Composable
private fun SettingsSegment(
    name: String
) {
    Row(
        modifier = Modifier.padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = name,
            color = MaterialTheme.colorScheme.tertiary,
            style = MaterialTheme.typography.labelSmall
                .copy(fontSize = 13.sp),
            modifier = Modifier.alpha(0.85f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Divider()
    }
}


@Composable
private fun SettingsTile(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    icon: ImageVector,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Row (
        modifier = modifier
            .fillMaxWidth()
            .alpha(if (enabled) 1f else 0.45f)
            .padding(Padding.ITEM_S)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = if (enabled) rememberRipple(true) else null,
            ) { if (enabled) onClick() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column (
            modifier = Modifier.fillMaxWidth(0.85f),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title.uppercase(),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(bottom = 6.dp)
            )
            Text(
                text = description,
                color = MaterialTheme.colorScheme.tertiary,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
            )
        }
        
        Spacer(modifier = Modifier.width(Padding.ITEM_L))
        Icon(
            imageVector = icon,
            contentDescription = icon.name,
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .size(24.dp)
                .alpha(0.8f)
        )
    }
}