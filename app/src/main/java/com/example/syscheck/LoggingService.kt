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
            try {
                while (running) {
                    Log.d("LoggingService", "Service is running...")
                    sleep(1000)
                }
            } catch (e: InterruptedException) {
                Log.d("LoggingService", "Service thread interrupted.")
            }
        }
        thread.start()
        return START_STICKY
    }

    override fun onDestroy() {
        running = false
        thread.interrupt()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}