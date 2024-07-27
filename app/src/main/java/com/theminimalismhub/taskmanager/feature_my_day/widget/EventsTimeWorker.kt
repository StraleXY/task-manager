package com.theminimalismhub.taskmanager.feature_my_day.widget

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

const val WORKER_NAME = "update-event-time-worker"

class EventsTimeWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {

        return Result.success()
    }

}