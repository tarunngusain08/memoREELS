package com.example.memoreels.ui.screen

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.memoreels.ui.viewmodel.NearbySharingViewModel
import com.example.memoreels.ui.viewmodel.SharingState

@Composable
fun NearbySharingScreen(
    viewModel: NearbySharingViewModel = hiltViewModel(),
    onBack: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    val devices by viewModel.devices.collectAsState()
    val statusMessage by viewModel.statusMessage.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                viewModel.stop()
                onBack()
            }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = Color.White)
            }
            Text(
                "Nearby Sharing",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        when (state) {
            SharingState.IDLE -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(32.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            Icons.Default.Share,
                            contentDescription = null,
                            tint = Color(0xFFE53935),
                            modifier = Modifier.size(72.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Share Memories Nearby",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            "Share albums with friends and family over Wi-Fi Direct. No internet needed.",
                            color = Color.White.copy(alpha = 0.6f),
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = { viewModel.startAdvertising() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFE53935)
                            )
                        ) {
                            Text("Send Memories", color = Color.White)
                        }
                        OutlinedButton(
                            onClick = { viewModel.startDiscovering() }
                        ) {
                            Text("Receive Memories", color = Color(0xFFE53935))
                        }
                    }
                }
            }

            SharingState.ADVERTISING, SharingState.DISCOVERING -> {
                Column(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(32.dp))

                    // Pulsing radar animation
                    val transition = rememberInfiniteTransition(label = "radar")
                    val scale by transition.animateFloat(
                        initialValue = 0.8f,
                        targetValue = 1.2f,
                        animationSpec = infiniteRepeatable(tween(1000), RepeatMode.Reverse),
                        label = "pulse"
                    )
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .scale(scale)
                            .clip(CircleShape)
                            .background(Color(0xFFE53935).copy(alpha = 0.3f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Share,
                            null,
                            tint = Color.White,
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        statusMessage,
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )

                    if (devices.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            "Nearby Devices",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(devices, key = { it.endpointId }) { device ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(Color.White.copy(alpha = 0.08f))
                                        .clickable {
                                            viewModel.connectToDevice(device.endpointId)
                                        }
                                        .padding(14.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(CircleShape)
                                            .background(
                                                if (device.isConnected)
                                                    Color(0xFF4CAF50)
                                                else Color(0xFFE53935)
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            device.name.take(1).uppercase(),
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            device.name,
                                            color = Color.White,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                        Text(
                                            if (device.isConnected) "Connected"
                                            else "Tap to connect",
                                            color = Color.White.copy(alpha = 0.5f),
                                            fontSize = 12.sp
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))
                    OutlinedButton(onClick = { viewModel.stop() }) {
                        Text("Stop", color = Color(0xFFE53935))
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            SharingState.CONNECTED, SharingState.TRANSFERRING -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.Check,
                            null,
                            tint = Color(0xFF4CAF50),
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            statusMessage,
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            SharingState.COMPLETE -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("✅", fontSize = 48.sp)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            "Transfer Complete!",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Button(
                            onClick = { viewModel.stop() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFE53935)
                            )
                        ) {
                            Text("Done", color = Color.White)
                        }
                    }
                }
            }

            SharingState.ERROR -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            statusMessage,
                            color = Color(0xFFE53935),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.stop() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFE53935)
                            )
                        ) {
                            Text("Retry", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}
