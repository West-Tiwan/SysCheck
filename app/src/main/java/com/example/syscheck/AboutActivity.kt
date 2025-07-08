package com.example.syscheck

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

class AboutActivity: ComponentActivity() {
    private lateinit var receiver: AirplaneModeReceiver

    override fun onStart() {
        super.onStart()
        receiver = AirplaneModeReceiver()
        val filter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        registerReceiver(receiver, filter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiver)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            About()
        }
    }
}

@Composable
fun About() {
    Text(text = "Welcome to the Second Activity!")
}
