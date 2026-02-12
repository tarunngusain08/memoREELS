package com.example.memoreels.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Displays video using a shared ExoPlayer instance.
 * Does NOT create or release the player -- caller owns the lifecycle.
 */
@Composable
fun VideoPlayer(
    exoPlayer: ExoPlayer,
    modifier: Modifier = Modifier,
    onTap: () -> Unit = {},
    onDoubleTap: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()

    // Heart animation state for double-tap
    var showHeart by remember { mutableStateOf(false) }
    val heartScale = remember { Animatable(0f) }
    val heartAlpha = remember { Animatable(0f) }
    val heartRotation = remember { Animatable(0f) }

    // Burst ring animation state
    val burstScale = remember { Animatable(0f) }
    val burstAlpha = remember { Animatable(0f) }

    // Fast-forward state
    var isFastForwarding by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { onTap() },
                    onDoubleTap = {
                        onDoubleTap()
                        showHeart = true
                        scope.launch {
                            // Reset all animation states
                            heartScale.snapTo(0.3f)
                            heartAlpha.snapTo(1f)
                            heartRotation.snapTo(0f)
                            burstScale.snapTo(0.2f)
                            burstAlpha.snapTo(0.6f)

                            // Spring bounce for heart scale
                            launch {
                                heartScale.animateTo(
                                    1.0f,
                                    spring(
                                        dampingRatio = 0.4f,
                                        stiffness = Spring.StiffnessLow
                                    )
                                )
                            }

                            // Subtle rotation: 0 -> -15 -> 0
                            launch {
                                heartRotation.animateTo(-15f, tween(120))
                                heartRotation.animateTo(0f, tween(180))
                            }

                            // Burst ring: scales out and fades
                            launch {
                                burstScale.animateTo(1.5f, tween(400))
                            }
                            launch {
                                delay(100)
                                burstAlpha.animateTo(0f, tween(350))
                            }

                            // Hold then fade out heart
                            delay(500)
                            heartAlpha.animateTo(0f, tween(300))
                            showHeart = false
                        }
                    },
                    onPress = {
                        val job = scope.launch {
                            delay(500)
                            isFastForwarding = true
                            exoPlayer.setPlaybackParameters(
                                exoPlayer.playbackParameters.withSpeed(2.0f)
                            )
                        }
                        tryAwaitRelease()
                        job.cancel()
                        if (isFastForwarding) {
                            isFastForwarding = false
                            exoPlayer.setPlaybackParameters(
                                exoPlayer.playbackParameters.withSpeed(1.0f)
                            )
                        }
                    }
                )
            }
    ) {
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    player = exoPlayer
                    useController = false
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                }
            },
            update = { view ->
                view.player = exoPlayer
            },
            modifier = Modifier.fillMaxSize()
        )

        // Heart animation overlay
        if (showHeart) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                // Burst ring behind the heart
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .scale(burstScale.value)
                        .alpha(burstAlpha.value)
                        .border(
                            width = 3.dp,
                            color = Color.White.copy(alpha = 0.7f),
                            shape = CircleShape
                        )
                        .background(
                            Color.White.copy(alpha = 0.12f),
                            CircleShape
                        )
                )

                // Shadow/glow heart (slightly offset and larger, white tint)
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.35f),
                    modifier = Modifier
                        .size(128.dp)
                        .scale(heartScale.value)
                        .alpha(heartAlpha.value)
                        .rotate(heartRotation.value)
                )

                // Primary red heart
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    tint = Color(0xFFE53935),
                    modifier = Modifier
                        .size(120.dp)
                        .scale(heartScale.value)
                        .alpha(heartAlpha.value)
                        .rotate(heartRotation.value)
                )
            }
        }

        // Fast-forward indicator
        if (isFastForwarding) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 48.dp)
                    .background(
                        Color.Black.copy(alpha = 0.5f),
                        RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "2x",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }
    }
}
