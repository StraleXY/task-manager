package com.theminimalismhub.taskmanager.feature_settings.presentation

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.theminimalismhub.taskmanager.core.consts.Corner
import com.theminimalismhub.taskmanager.core.consts.Padding
import com.theminimalismhub.taskmanager.core.navigation.pages.Page
import com.theminimalismhub.taskmanager.feature_assignments.presentation.AssignmentsEvent
import com.theminimalismhub.taskmanager.feature_calendar.presentation.composables.CalendarTile

@Composable
fun SettingsPage(vm: SettingsVM = hiltViewModel()) {

    val context = LocalContext.current
    var calendarsVisible by remember { mutableStateOf(true) }
    vm.init(context)

    Page {
        Spacer(modifier = Modifier.height(Padding.SECTION))
        Text(
            text = "Task Manager Settings",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(Padding.SECTION))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { calendarsVisible = !calendarsVisible },
            shape = Corner.SMALL,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(6.dp))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Padding.CONTAINER_S),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = vm.state.value.selectedCalendar.name)
                Icon(imageVector = if(calendarsVisible) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown, contentDescription = "")
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(tween(250))
        ){
            AnimatedVisibility(
                visible = calendarsVisible,
                enter = fadeIn(tween(250)) + expandVertically(tween(250)) { it/5 },
                exit = fadeOut(tween(250)) + shrinkVertically(tween(250)) { it/5 }
            ) {
                Column {
                    Spacer(modifier = Modifier.height(Padding.SECTION))
                    vm.state.value.calendars.forEach { calendar ->
                        CalendarTile(calendar = calendar) {
                            vm.onEvent(SettingsEvent.SelectCalendar(it.id))
                            calendarsVisible = false
                        }
                        Spacer(modifier = Modifier.height(Padding.SECTION))
                    }
                }
            }
        }
    }
}