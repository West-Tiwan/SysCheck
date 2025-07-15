package com.example.syscheck.ui.screen

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat

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
            val context = LocalContext.current
            var contacts by remember { mutableStateOf(listOf<String>()) }

            ContactPermissionHandler {
                contacts = getContacts(context)
            }
            About(contacts)
        }
    }
}

@Composable
fun About(contactList: List<String>) {
    LazyColumn {
        items(contactList) { contact ->
            Text(text = contact, modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun ContactPermissionHandler(onPermissionGranted: () -> Unit) {
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onPermissionGranted()
        } else {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionLauncher.launch(Manifest.permission.READ_CONTACTS)
        } else {
            onPermissionGranted()
        }
    }
}


fun getContacts(context: Context): List<String> {
    val contactList = mutableListOf<String>()
    val resolver = context.contentResolver
    val cursor = resolver.query(
        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        arrayOf(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME),
        null,
        null,
        null
    )
    cursor?.use {
        val nameList = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
        while (it.moveToNext()) {
            val name = it.getString(nameList)
            contactList.add(name)
        }
    }
    return contactList
}