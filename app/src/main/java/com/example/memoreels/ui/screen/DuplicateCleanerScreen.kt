package com.example.memoreels.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.memoreels.ui.components.MediaThumbnail
import com.example.memoreels.ui.viewmodel.DuplicateCleanerViewModel
import com.example.memoreels.ui.viewmodel.DuplicateGroup

@Composable
fun DuplicateCleanerScreen(
    viewModel: DuplicateCleanerViewModel = hiltViewModel(),
    onBack: () -> Unit = {}
) {
    val groups by viewModel.groups.collectAsState()
    val isScanning by viewModel.isScanning.collectAsState()
    val progress by viewModel.progress.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = Color.White)
            }
            Text("Duplicate Cleaner", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        when {
            isScanning -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(color = Color(0xFFE53935))
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(progress, color = Color.White.copy(alpha = 0.7f), fontSize = 14.sp)
                    }
                }
            }
            groups.isEmpty() -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = if (progress.isNotBlank()) progress else "Scan your gallery to find duplicate photos",
                            color = Color.White.copy(alpha = 0.5f),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(32.dp)
                        )
                        Button(
                            onClick = { viewModel.scanForDuplicates() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935))
                        ) {
                            Text("Scan for Duplicates", color = Color.White)
                        }
                    }
                }
            }
            else -> {
                Text(
                    text = "${groups.size} groups with ${groups.sumOf { it.uris.size }} total photos",
                    color = Color.White.copy(alpha = 0.5f),
                    fontSize = 13.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(groups) { group ->
                        DuplicateGroupCard(group)
                    }
                }
            }
        }
    }
}

@Composable
private fun DuplicateGroupCard(group: DuplicateGroup) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White.copy(alpha = 0.05f))
            .padding(12.dp)
    ) {
        Text(
            text = "${group.uris.size} similar photos",
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            items(group.uris) { uri ->
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .then(
                            if (uri == group.suggestedKeep)
                                Modifier.border(2.dp, Color(0xFF4CAF50), RoundedCornerShape(8.dp))
                            else Modifier
                        )
                ) {
                    MediaThumbnail(contentUri = uri, contentDescription = "Duplicate photo")
                    if (uri == group.suggestedKeep) {
                        Icon(
                            Icons.Default.CheckCircle,
                            "Suggested keep",
                            tint = Color(0xFF4CAF50),
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(2.dp)
                                .size(16.dp)
                        )
                    }
                }
            }
        }
    }
}
