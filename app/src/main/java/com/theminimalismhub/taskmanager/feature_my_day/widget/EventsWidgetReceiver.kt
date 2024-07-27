package com.theminimalismhub.taskmanager.feature_my_day.widget

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class EventsWidgetReceiver : GlanceAppWidgetReceiver() {

    override val glanceAppWidget: GlanceAppWidget = EventsWidget()
    override fun onEnabled(context: Context?) {
        super.onEnabled(context)

        context?.let {
            val request = PeriodicWorkRequestBuilder<EventsTimeWorker>(1L, TimeUnit.SECONDS).build()
            WorkManager
                .getInstance(context)
                .enqueueUniquePeriodicWork(
                    WORKER_NAME,
                    ExistingPeriodicWorkPolicy.UPDATE,
                    request
                )
        }

    }

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)

        context?.let {
            WorkManager
                .getInstance(context)
                .cancelUniqueWork(WORKER_NAME)
        }
    }
}