package com.example.syscheck.ui.screen

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.syscheck.R

class ForegroundLogging: Service() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notif = NotificationCompat.Builder(this, "channel_id")
            .setContentTitle("logger")
            .setContentText("Foreground service is running")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()

        startForeground(1, notif)

        // Simulate background task
        Thread {
            repeat(10) {
                Log.d("ForegroundService", "Tick $it")
                Thread.sleep(1000)
            }
            stopSelf()
        }.start()

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            "channel_id",
            "Logging Service",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }
}