package com.theminimalismhub.taskmanager.feature_my_day.widget

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventsWidgetReceiver : GlanceAppWidgetReceiver() {

    override val glanceAppWidget: GlanceAppWidget = EventsWidget()

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
        context?.let {
            val intent = Intent(context, TimeKeeperService::class.java)
            context.startForegroundService(intent)
        }
    }
}