package com.example.memoreels.ui.screen

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.clickable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.memoreels.ui.components.MediaThumbnail
import com.example.memoreels.ui.viewmodel.DayGroup
import com.example.memoreels.ui.viewmodel.JournalViewModel

@Composable
fun JournalScreen(
    viewModel: JournalViewModel = hiltViewModel(),
    onBack: () -> Unit = {},
    onMediaClick: (String) -> Unit = {}
) {
    val dayGroups by viewModel.dayGroups.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

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
            Text("Memory Journal", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        when {
            isLoading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color.White)
                }
            }
            dayGroups.isEmpty() -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No memories yet", color = Color.White.copy(alpha = 0.5f), fontSize = 16.sp)
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    items(dayGroups, key = { it.date }) { group ->
                        DayEntry(
                            group = group,
                            onSaveNote = { note -> viewModel.saveNote(group.date, note) },
                            onMediaClick = onMediaClick
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DayEntry(
    group: DayGroup,
    onSaveNote: (String) -> Unit,
    onMediaClick: (String) -> Unit
) {
    var isEditing by remember { mutableStateOf(false) }
    var noteText by remember(group.note) { mutableStateOf(group.note ?: "") }

    Column {
        // Date header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = group.displayDate,
                color = Color(0xFFE53935),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${group.photos.size} memories",
                color = Color.White.copy(alpha = 0.4f),
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Photo strip
        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            items(group.photos) { photo ->
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    MediaThumbnail(
                        contentUri = photo.uri,
                        contentDescription = photo.displayName
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Note section
        if (isEditing) {
            OutlinedTextField(
                value = noteText,
                onValueChange = { noteText = it },
                placeholder = { Text("Write about this day...", color = Color.White.copy(alpha = 0.3f)) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = Color(0xFFE53935),
                    unfocusedBorderColor = Color.White.copy(alpha = 0.2f)
                ),
                modifier = Modifier.fillMaxWidth(),
                maxLines = 4
            )
            Row(
                modifier = Modifier.padding(top = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Save",
                    color = Color(0xFFE53935),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .clickable {
                            onSaveNote(noteText)
                            isEditing = false
                        }
                        .padding(4.dp)
                )
                Text(
                    text = "Cancel",
                    color = Color.White.copy(alpha = 0.5f),
                    fontSize = 13.sp,
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .clickable { isEditing = false }
                        .padding(4.dp)
                )
            }
        } else if (group.note != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White.copy(alpha = 0.05f))
                    .padding(10.dp)
            ) {
                Text(
                    text = group.note,
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { isEditing = true }, modifier = Modifier.size(20.dp)) {
                    Icon(Icons.Default.Edit, "Edit", tint = Color.White.copy(alpha = 0.4f), modifier = Modifier.size(14.dp))
                }
            }
        } else {
            Text(
                text = "+ Add a note about this day",
                color = Color.White.copy(alpha = 0.3f),
                fontSize = 13.sp,
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .clickable { isEditing = true }
                    .padding(vertical = 4.dp)
            )
        }
    }
}
