package com.example.syscheck

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.lang.Thread.sleep

class LoggingService: Service() {
    private var running = true
    private lateinit var thread: Thread

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        thread = Thread {
            while (running) {
                Log.d("LoggingService", "Service is running")
                sleep(1000)
            }
        }
        thread.start()
        return START_STICKY
    }

    override fun onDestroy() {
        running = false
        try {
            thread.interrupt()
        } catch (e: Exception) {
            Log.d("LoggingService", "logging service stopped error - $e")
        }
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}