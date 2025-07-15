package com.example.syscheck.ui.screen

import android.Manifest
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import coil3.compose.AsyncImage
import com.example.syscheck.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CameraActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CameraScreen()
        }
    }
}

@Composable
fun CameraScreen() {
    val context = LocalContext.current
    var imageUriForCamera by remember { mutableStateOf<Uri?>(null) }
    var displayedImageUri by remember { mutableStateOf<Uri?>(null) }
    val fileProviderAuthority = "fileprovider1"

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = {success ->
            if (success) {
                displayedImageUri = imageUriForCamera
                Toast.makeText(context, "photo taken", Toast.LENGTH_SHORT).show()
            }
            else Toast.makeText(context, "photo NOT taken", Toast.LENGTH_SHORT).show()
        }
    )

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {granted ->
            if (granted) {
                val photoFile: File? = try {
                    createImageFile(context)
                } catch (ex: Exception) {
                    Log.e("CameraScreen", "Error creating image file", ex)
                    Toast.makeText(context, "Error creating image file", Toast.LENGTH_SHORT).show()
                    null
                }
                photoFile?.let {
                    val uri = FileProvider.getUriForFile(
                        context,
                        fileProviderAuthority,
                        it
                    )
                    imageUriForCamera = uri
                    cameraLauncher.launch(uri)
                }
            } else {
                Toast.makeText(context, "Camera permission denied.", Toast.LENGTH_SHORT).show()
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }) {
            Text("Launch Camera")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (displayedImageUri != null) {
            Log.d("CameraScreen", "AsyncImage loading URI: $displayedImageUri")
            AsyncImage(
                model = displayedImageUri,
                contentDescription = "Captured Image",
                modifier = Modifier.fillMaxWidth(),
                placeholder = painterResource(id = R.drawable.ic_launcher_background),
                error = painterResource(id = R.drawable.ic_launcher_foreground),
                onSuccess = { successState ->
                    Log.d("CameraScreen", "AsyncImage loaded successfully.")
                },
                onError = { errorState ->
                    Log.e("CameraScreen", "AsyncImage error: ${errorState.result.throwable.localizedMessage}")
                }
            )
        } else {
            Text("No image captured yet.")
        }
    }
}

fun createImageFile(context: Context): File {
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(
        "JPEG_${timeStamp}_",
        ".jpg",
        storageDir
    )
}