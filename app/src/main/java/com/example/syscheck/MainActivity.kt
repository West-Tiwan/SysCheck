package com.example.syscheck

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.syscheck.ui.theme.SysCheckTheme
import androidx.core.net.toUri
import com.example.syscheck.ui.screen.AboutActivity
import com.example.syscheck.ui.screen.CameraActivity
import com.example.syscheck.ui.screen.ForegroundLogging
import com.example.syscheck.ui.screen.LoggingService
import com.example.syscheck.ui.screen.TaskListActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SysCheckTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1001)
        }
    }
    Column (
        modifier = Modifier.padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                val intent = Intent(context, AboutActivity::class.java)
                context.startActivity(intent)
            }
        ) {
            Text(text = "go to about page")
        }
        Button(
            onClick = {
                val intent = Intent(context, LoggingService::class.java)
                context.startService(intent)
            }
        ) {
            Text(text = "start logging service")
        }
        Button(
            onClick = {
                val intent = Intent(context, LoggingService::class.java)
                context.stopService(intent)
            }
        ) {
            Text(text = "stop logging service")
        }
        Button(
            onClick = {
                val number = 1234567890
                val intent = Intent(
                    Intent.ACTION_DIAL
                ).apply {
                    data = "tel:$number".toUri()
                }
                try {
                    context.startActivity(intent)
                } catch (e: Error) {
                    Toast.makeText(context, "can't find dialer", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text(text = "open dialer")
        }
        Button(
            onClick = {
                val intent = Intent(context, ForegroundLogging::class.java)
                ContextCompat.startForegroundService(context, intent)
            }
        ) {
            Text(text = "start foreground service")
        }
        Button(
            onClick = {
                val intent = Intent(context, CameraActivity::class.java)
                context.startActivity(intent)
            }
        ) {
            Text(text = "open camera")
        }
        Button(
            onClick = {
                val intent = Intent(context, TaskListActivity::class.java)
                context.startActivity(intent)
            }
        ) {
            Text(text = "open Task List Screen")
        }
    }
}