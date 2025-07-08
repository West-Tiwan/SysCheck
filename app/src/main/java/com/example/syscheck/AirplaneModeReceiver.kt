package com.example.syscheck

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.widget.Toast

class AirplaneModeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        // It's good practice to check the intent action
        if (intent?.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
            val isAirplaneModeEnabled = Settings.Global.getInt(
                context?.contentResolver, // Use the ContentResolver from the context
                Settings.Global.AIRPLANE_MODE_ON, // The name of the setting
                0 // Default value if the setting is not found (0 for off)
            ) != 0 // Convert to boolean (1 means on, 0 means off)

            Toast.makeText(
                context,
                "Airplane mode state: ${if (isAirplaneModeEnabled) "Enabled" else "Disabled"}",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
