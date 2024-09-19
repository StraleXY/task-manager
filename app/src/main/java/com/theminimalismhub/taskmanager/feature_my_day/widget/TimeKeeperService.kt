package com.theminimalismhub.taskmanager.feature_my_day.widget

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.glance.appwidget.GlanceAppWidgetManager
import com.theminimalismhub.taskmanager.MainActivity
import com.theminimalismhub.taskmanager.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import javax.inject.Inject

@AndroidEntryPoint
class TimeKeeperService : Service() {

    @Inject
    lateinit var context: Context
    private var isServiceRunning = false
    private val CHANNEL_ID = "ForegroundServiceChannel"

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("Worker", "Starting command")
        createNotificationChannel()
        val notification = buildNotification()
        startForeground(1, notification)
        isServiceRunning = true
        startService()
        return START_STICKY
    }

    private fun startService() {
        Thread {
            val dispatcherScope = CoroutineScope(Executors.newSingleThreadExecutor().asCoroutineDispatcher())
            while (isServiceRunning) {
                Thread.sleep(30000)
                val manager = GlanceAppWidgetManager(context)
                val widget = EventsWidget()
                dispatcherScope.launch {
                    val glanceIds = manager.getGlanceIds(widget.javaClass)
                    glanceIds.forEach { glanceId ->
                        widget.update(context, glanceId)
                    }

                    context.getSharedPreferences("TKS", Context.MODE_PRIVATE)
                        .edit()
                        .putLong("LU", System.currentTimeMillis())
                        .apply()
                }
            }
        }.start()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Time keeper channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        val manager: NotificationManager =
            getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    private fun buildNotification(): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Task Manager Time Keeper")
            .setContentText("Updating widget in progress!")
//            .setSmallIcon(R.drawable.home_pin)
            .setContentIntent(null)
            .build()
    }

}