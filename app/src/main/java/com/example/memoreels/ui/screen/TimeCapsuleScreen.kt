package com.example.memoreels.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.memoreels.data.model.TimeCapsuleEntity
import com.example.memoreels.ui.components.MediaThumbnail
import com.example.memoreels.ui.viewmodel.TimeCapsuleViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

@Composable
fun TimeCapsuleScreen(
    viewModel: TimeCapsuleViewModel = hiltViewModel(),
    onBack: () -> Unit = {},
    onMediaClick: (String) -> Unit = {}
) {
    val capsules by viewModel.capsules.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
    ) {
        // Top bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
            Text(
                text = "Time Capsules",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        if (capsules.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.Lock,
                        contentDescription = null,
                        tint = Color.White.copy(alpha = 0.3f),
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No time capsules yet.\nSeal your memories for the future!",
                        color = Color.White.copy(alpha = 0.5f),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(capsules, key = { it.id }) { capsule ->
                    TimeCapsuleCard(
                        capsule = capsule,
                        onReveal = { viewModel.openCapsule(capsule.id) },
                        onDelete = { viewModel.deleteCapsule(capsule.id) },
                        onMediaClick = onMediaClick
                    )
                }
            }
        }
    }
}

@Composable
private fun TimeCapsuleCard(
    capsule: TimeCapsuleEntity,
    onReveal: () -> Unit,
    onDelete: () -> Unit,
    onMediaClick: (String) -> Unit
) {
    val now = System.currentTimeMillis()
    val isUnlocked = now >= capsule.unlockDate
    val isOpened = capsule.isOpened
    var showContent by remember { mutableStateOf(isOpened) }
    val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

    val gradient = if (isUnlocked && !isOpened) {
        Brush.horizontalGradient(listOf(Color(0xFF880E4F), Color(0xFFAD1457)))
    } else if (isOpened) {
        Brush.horizontalGradient(listOf(Color(0xFF1B5E20), Color(0xFF2E7D32)))
    } else {
        Brush.horizontalGradient(listOf(Color(0xFF37474F), Color(0xFF455A64)))
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(gradient)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    if (isUnlocked) Icons.Default.Star else Icons.Default.Lock,
                    contentDescription = null,
                    tint = if (isUnlocked) Color(0xFFFFD54F) else Color.White.copy(alpha = 0.5f),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = capsule.title,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
            IconButton(onClick = onDelete, modifier = Modifier.size(24.dp)) {
                Icon(Icons.Default.Delete, "Delete", tint = Color.White.copy(alpha = 0.5f), modifier = Modifier.size(16.dp))
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        if (!isUnlocked) {
            val daysLeft = TimeUnit.MILLISECONDS.toDays(capsule.unlockDate - now)
            Text(
                text = "Unlocks ${dateFormat.format(Date(capsule.unlockDate))} ($daysLeft days)",
                color = Color.White.copy(alpha = 0.6f),
                fontSize = 13.sp
            )
        } else if (!isOpened) {
            Button(
                onClick = {
                    onReveal()
                    showContent = true
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD54F))
            ) {
                Text("Reveal Memories", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }

        AnimatedVisibility(
            visible = showContent && isOpened,
            enter = fadeIn(tween(600)) + scaleIn(tween(600), initialScale = 0.8f)
        ) {
            val uris = capsule.mediaUris.split(",").filter { it.isNotBlank() }
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(top = 10.dp)
            ) {
                items(uris) { uri ->
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onMediaClick(uri) }
                    ) {
                        MediaThumbnail(contentUri = uri, contentDescription = "Memory")
                    }
                }
            }
        }

        if (isOpened) {
            Text(
                text = "Opened ${dateFormat.format(Date(capsule.createdAt))}",
                color = Color.White.copy(alpha = 0.4f),
                fontSize = 11.sp,
                modifier = Modifier.padding(top = 6.dp)
            )
        }
    }
}
