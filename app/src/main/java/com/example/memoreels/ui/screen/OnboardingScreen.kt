package com.example.memoreels.ui.screen

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun OnboardingScreen(
    onComplete: () -> Unit
) {
    val context = LocalContext.current
    val permissionsState = rememberMultiplePermissionsState(
        permissions = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            listOf(
                android.Manifest.permission.READ_MEDIA_VIDEO,
                android.Manifest.permission.READ_MEDIA_IMAGES
            )
        } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            listOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        } else {
            listOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    ) { permissions ->
        if (permissions.all { it.value }) {
            onComplete()
        }
    }

    LaunchedEffect(Unit) {
        if (permissionsState.allPermissionsGranted) {
            onComplete()
        }
    }

    if (permissionsState.allPermissionsGranted) {
        return
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "memoREELS",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Rediscover your local videos and photos in a Reels-style feed. " +
                    "We need access to your media to show them - all data stays on your device.",
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(48.dp))
            Button(
                onClick = { permissionsState.launchMultiplePermissionRequest() },
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text("Grant Access")
            }
            if (permissionsState.shouldShowRationale) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Permission is required to access your videos",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 14.sp
                )
            }
            if (!permissionsState.allPermissionsGranted && !permissionsState.shouldShowRationale) {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", context.packageName, null)
                        }
                        context.startActivity(intent)
                    },
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Text("Open Settings")
                }
            }
        }
    }
}
