package com.theminimalismhub.taskmanager.feature_my_day.widget

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.height
import androidx.glance.layout.Spacer
import androidx.glance.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.theminimalismhub.taskmanager.core.consts.Padding
import com.theminimalismhub.taskmanager.feature_task.domain.model.Task
import com.theminimalismhub.taskmanager.utils.CalendarUtils
import com.theminimalismhub.taskmanager.utils.TimeConverter
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

class EventsWidget : GlanceAppWidget() {

    private lateinit var context: Context
    private lateinit var id: GlanceId

    override suspend fun provideGlance(context: Context, id: GlanceId) {

        this.context = context
        this.id = id

        var tasks = CalendarUtils.getTodayTasks(context, listOf("9", "11", "20", "24", "27", "28", "29")).sortedBy { it.timeStart }

        fun update() {
            tasks = CalendarUtils.getTodayTasks(context, listOf("9", "11", "20", "24", "27", "28", "29")).sortedBy { it.timeStart }
        }

        provideContent {
            Main(task = tasks.first(), onEnd = { update() })
        }
    }

    @Composable
    private fun Main(
        task: Task,
        onEnd: () -> Unit
    ) {

        val isUpcoming by remember { mutableStateOf(task.timeStart > System.currentTimeMillis()) }
        val timeToShow by remember { mutableStateOf(TimeConverter.getPreciseFormattedTimeUntil(if(isUpcoming) task.timeStart else task.timeEnd, inclusive = true)) }


        Column(
            modifier = GlanceModifier
                .background(Color(0.2f, 0.2f, 0.2f))
                .cornerRadius(24.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = task.title,
                style = TextStyle(
                    color = ColorProvider(Color(task.color?.toInt() ?: 0)),
                    fontSize = 26.sp,
                    fontWeight = androidx.glance.text.FontWeight.Normal
                )
            )
            Spacer(modifier = GlanceModifier.height(Padding.ITEM_S))
            Text(
                text = TimeConverter.getFormattedDateInterval(task.timeStart, task.timeEnd),
                style = TextStyle(
                    color = ColorProvider(Color(1f, 1f, 1f,  0.8f)),
                    fontSize = 13.sp,
                    fontWeight = androidx.glance.text.FontWeight.Normal
                )
            )
            Spacer(modifier = GlanceModifier.height(Padding.ITEM_M))
            Text(
                text = timeToShow!!,
                style = TextStyle(
                    color = ColorProvider(Color(1f, 1f, 1f)),
                    fontSize = 65.sp
                )
            )
            Spacer(modifier = GlanceModifier.height(Padding.ITEM_S))
            Text(
                text = if(isUpcoming) "UPCOMING" else "ONGOING",
                style = TextStyle(
                    color = ColorProvider(Color(1f, 1f, 1f,  0.8f)),
                    fontSize = 13.sp
                )
            )
        }
    }

}