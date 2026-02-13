package com.example.memoreels.ui.screen

import androidx.compose.animation.animateColorAsState
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.memoreels.data.preferences.FeedMode
import com.example.memoreels.ui.viewmodel.VideoFeedViewModel

/**
 * Full-screen Settings tab. Replaces the floating SettingsButton.
 * Contains feed mode, playback preferences, and feature navigation.
 */
@Composable
fun SettingsScreen(
    viewModel: VideoFeedViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit = {}
) {
    val feedMode by viewModel.feedMode.collectAsState()
    val autoMute by viewModel.autoMute.collectAsState()
    val loopVideos by viewModel.loopVideos.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            text = "Settings",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --- Playback Section ---
        Text(
            text = "Playback",
            color = Color.White.copy(alpha = 0.5f),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(8.dp))

        SettingsToggleRow(
            title = "Separate Photos & Videos",
            description = if (feedMode == FeedMode.SEPARATE)
                "Photos shown in their own tab"
            else
                "Photos mixed into the video feed",
            checked = feedMode == FeedMode.SEPARATE,
            onCheckedChange = { isSeparate ->
                viewModel.setFeedMode(
                    if (isSeparate) FeedMode.SEPARATE else FeedMode.UNIFIED
                )
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        SettingsToggleRow(
            title = "Start Muted",
            description = if (autoMute)
                "Videos start with sound off"
            else
                "Videos start with sound on",
            checked = autoMute,
            onCheckedChange = { viewModel.setAutoMute(it) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        SettingsToggleRow(
            title = "Loop Videos",
            description = if (loopVideos)
                "Videos replay automatically"
            else
                "Videos auto-advance to next",
            checked = loopVideos,
            onCheckedChange = { viewModel.setLoopVideos(it) }
        )

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
        Spacer(modifier = Modifier.height(16.dp))

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
            Triple("duplicateCleaner", "Duplicate Cleaner", "Find and clean similar photos"),
            Triple("memoryMap", "Memory Map", "See where your memories were made"),
            Triple("peopleAlbums", "People Albums", "Face detection & grouping"),
            Triple("nearbySharing", "Nearby Sharing", "Share via Wi-Fi Direct")
        )

        features.forEach { (route, title, desc) ->
            SettingsNavButton(
                title = title,
                description = desc,
                onClick = { onNavigate(route) }
            )
            Spacer(modifier = Modifier.height(6.dp))
        }

        Spacer(modifier = Modifier.height(32.dp))
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
