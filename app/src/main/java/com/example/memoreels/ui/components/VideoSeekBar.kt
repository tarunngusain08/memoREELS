package com.example.memoreels.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.delay
import java.util.Locale

/**
 * Elegant seek bar that appears when the video is paused.
 * Shows elapsed / total time with a smooth slider.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoSeekBar(
    exoPlayer: ExoPlayer,
    visible: Boolean,
    modifier: Modifier = Modifier
) {
    var currentPosition by remember { mutableLongStateOf(0L) }
    var duration by remember { mutableLongStateOf(0L) }
    var isSeeking by remember { mutableStateOf(false) }
    var seekPosition by remember { mutableFloatStateOf(0f) }

    // Poll player position while visible
    LaunchedEffect(visible) {
        while (visible) {
            if (!isSeeking) {
                currentPosition = exoPlayer.currentPosition.coerceAtLeast(0)
                duration = exoPlayer.duration.coerceAtLeast(0)
            }
            delay(200)
        }
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(tween(300)) + slideInVertically(tween(300)) { it / 3 },
        exit = fadeOut(tween(300)) + slideOutVertically(tween(300)) { it / 3 },
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.Black.copy(alpha = 0.55f))
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Column {
                // Time labels
                val elapsedTime = formatDuration(
                    if (isSeeking) (seekPosition * duration).toLong()
                    else currentPosition
                )
                val totalTime = formatDuration(duration)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = elapsedTime,
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 11.sp,
                        modifier = Modifier.semantics {
                            contentDescription = "Elapsed time, $elapsedTime"
                        }
                    )
                    Text(
                        text = totalTime,
                        color = Color.White.copy(alpha = 0.6f),
                        fontSize = 11.sp,
                        modifier = Modifier.semantics {
                            contentDescription = "Total duration, $totalTime"
                        }
                    )
                }

                // Seek slider
                val sliderValue = if (isSeeking) seekPosition
                    else if (duration > 0) currentPosition.toFloat() / duration
                    else 0f

                Slider(
                    value = sliderValue.coerceIn(0f, 1f),
                    onValueChange = { value ->
                        isSeeking = true
                        seekPosition = value
                    },
                    onValueChangeFinished = {
                        if (duration > 0) {
                            exoPlayer.seekTo((seekPosition * duration).toLong())
                        }
                        isSeeking = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp)
                        .semantics {
                            contentDescription = "Video seek bar, drag to change position"
                        },
                    colors = SliderDefaults.colors(
                        thumbColor = Color(0xFFE53935),
                        activeTrackColor = Color(0xFFE53935),
                        inactiveTrackColor = Color.White.copy(alpha = 0.25f)
                    )
                )
            }
        }
    }
}

/** Formats milliseconds as mm:ss. */
private fun formatDuration(ms: Long): String {
    val totalSeconds = ms / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format(Locale.US, "%d:%02d", minutes, seconds)
}
