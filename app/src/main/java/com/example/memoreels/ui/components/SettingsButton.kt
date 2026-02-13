package com.example.memoreels.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.memoreels.data.preferences.FeedMode
import kotlinx.coroutines.launch

/**
 * Small semi-transparent settings gear icon that opens a bottom sheet
 * with feed mode, auto-mute, loop, and rescan options.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsButton(
    currentMode: FeedMode,
    onModeChanged: (FeedMode) -> Unit,
    modifier: Modifier = Modifier,
    autoMute: Boolean = false,
    onAutoMuteChanged: (Boolean) -> Unit = {},
    loopVideos: Boolean = true,
    onLoopVideosChanged: (Boolean) -> Unit = {},
    onRescanMedia: () -> Unit = {},
    onNavigate: (String) -> Unit = {}
) {
    var showSheet by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()

    // Floating gear button
    Box(
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(Color.Black.copy(alpha = 0.35f))
            .clickable { showSheet = true },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = "Settings",
            tint = Color.White.copy(alpha = 0.85f),
            modifier = Modifier.size(22.dp)
        )
    }

    // Bottom sheet with settings
    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState,
            containerColor = Color(0xFF1A1A1A),
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
            dragHandle = {
                Box(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .width(36.dp)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(Color.White.copy(alpha = 0.3f))
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp)
                    .padding(bottom = 32.dp)
            ) {
                Text(
                    text = "Settings",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(20.dp))

                // --- Feed Mode Toggle ---
                SettingsToggleRow(
                    title = "Separate Photos & Videos",
                    description = if (currentMode == FeedMode.SEPARATE)
                        "Photos shown in their own tab"
                    else
                        "Photos mixed into the video feed",
                    checked = currentMode == FeedMode.SEPARATE,
                    onCheckedChange = { isSeparate ->
                        val newMode = if (isSeparate) FeedMode.SEPARATE
                        else FeedMode.UNIFIED
                        onModeChanged(newMode)
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
                Spacer(modifier = Modifier.height(10.dp))

                // --- Auto-mute Toggle ---
                SettingsToggleRow(
                    title = "Start Muted",
                    description = if (autoMute)
                        "Videos start with sound off"
                    else
                        "Videos start with sound on",
                    checked = autoMute,
                    onCheckedChange = onAutoMuteChanged
                )

                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
                Spacer(modifier = Modifier.height(10.dp))

                // --- Loop Videos Toggle ---
                SettingsToggleRow(
                    title = "Loop Videos",
                    description = if (loopVideos)
                        "Videos replay automatically"
                    else
                        "Videos stop at the end",
                    checked = loopVideos,
                    onCheckedChange = onLoopVideosChanged
                )

                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
                Spacer(modifier = Modifier.height(14.dp))

                // --- Rescan Media Button ---
                SettingsNavButton(
                    title = "Rescan Media",
                    description = "Re-analyze photos and videos for AI tags",
                    onClick = {
                        onRescanMedia()
                        scope.launch { sheetState.hide(); showSheet = false }
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
                Spacer(modifier = Modifier.height(10.dp))

                // --- Features Section ---
                Text(
                    text = "Features",
                    color = Color.White.copy(alpha = 0.5f),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(8.dp))

                val features = listOf(
                    Triple("timeCapsules", "Time Capsules", "Seal memories for the future"),
                    Triple("journal", "Memory Journal", "Timeline diary of your days"),
                    Triple("moodFeed", "Mood Feed", "Browse memories by mood"),
                    Triple("duplicateCleaner", "Duplicate Cleaner", "Find and clean similar photos"),
                    Triple("memoryMap", "Memory Map", "See where your memories were made"),
                    Triple("highlightReels", "Highlight Reels", "Auto-generate montages"),
                    Triple("peopleAlbums", "People Albums", "Face detection & grouping"),
                    Triple("nearbySharing", "Nearby Sharing", "Share via Wi-Fi Direct")
                )

                features.forEach { (route, title, desc) ->
                    SettingsNavButton(
                        title = title,
                        description = desc,
                        onClick = {
                            scope.launch { sheetState.hide(); showSheet = false }
                            onNavigate(route)
                        }
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                }
            }
        }
    }
}

@Composable
private fun SettingsNavButton(
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(Color(0xFF2A2A2A))
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Column {
            Text(
                text = title,
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = description,
                color = Color.White.copy(alpha = 0.55f),
                fontSize = 12.sp
            )
        }
    }
}

@Composable
private fun SettingsToggleRow(
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val thumbColor by animateColorAsState(
        targetValue = if (checked) Color(0xFFE53935) else Color.Gray,
        animationSpec = tween(250),
        label = "thumb"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(Color(0xFF2A2A2A))
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = description,
                color = Color.White.copy(alpha = 0.55f),
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = thumbColor,
                checkedTrackColor = Color(0xFFE53935).copy(alpha = 0.3f),
                uncheckedThumbColor = Color.Gray,
                uncheckedTrackColor = Color.DarkGray
            )
        )
    }
}
