package com.example.memoreels.ui.screen

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.memoreels.ui.components.VideoPlayer
import com.example.memoreels.ui.components.VideoSeekBar
import kotlinx.coroutines.delay

@Composable
fun VideoPlayerScreen(
    videoUri: String,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    var isPlayerReleased by remember { mutableStateOf(false) }
    var isPaused by remember { mutableStateOf(false) }
    var playbackState by remember { mutableIntStateOf(Player.STATE_IDLE) }
    var hasError by remember { mutableStateOf(false) }

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(C.USAGE_MEDIA)
                    .setContentType(C.AUDIO_CONTENT_TYPE_MOVIE)
                    .build(),
                /* handleAudioFocus= */ true
            )
            repeatMode = Player.REPEAT_MODE_ONE
            playWhenReady = true
            setMediaItem(MediaItem.fromUri(Uri.parse(videoUri)))
            prepare()
        }
    }

    // Listen to player state changes
    DisposableEffect(exoPlayer) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                playbackState = state
                if (state == Player.STATE_IDLE && exoPlayer.playerError != null) {
                    hasError = true
                }
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                isPaused = !isPlaying
            }
        }
        exoPlayer.addListener(listener)
        onDispose {
            exoPlayer.removeListener(listener)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            isPlayerReleased = true
            exoPlayer.release()
        }
    }

    // Poll playback state for seek bar updates
    LaunchedEffect(Unit) {
        while (true) {
            if (!isPlayerReleased) {
                try {
                    isPaused = !exoPlayer.isPlaying
                } catch (_: Exception) { }
            }
            delay(200)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        when {
            hasError -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Unable to play this video",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 16.sp
                    )
                }
            }
            playbackState == Player.STATE_BUFFERING -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            }
        }

        VideoPlayer(
            exoPlayer = exoPlayer,
            modifier = Modifier.fillMaxSize(),
            onTap = {
                if (!isPlayerReleased) {
                    try {
                        exoPlayer.playWhenReady = !exoPlayer.playWhenReady
                    } catch (_: Exception) { }
                }
            }
        )

        // Seek bar (visible when paused)
        VideoSeekBar(
            exoPlayer = exoPlayer,
            visible = isPaused && !hasError,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        )

        // Back button
        IconButton(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }
    }
}
