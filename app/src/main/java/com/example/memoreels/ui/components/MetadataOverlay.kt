package com.example.memoreels.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.memoreels.domain.model.Video
import kotlinx.coroutines.delay

@Composable
fun MetadataOverlay(
    video: Video,
    visible: Boolean,
    modifier: Modifier = Modifier
) {
    var showOverlay by remember { mutableStateOf(visible) }

    LaunchedEffect(video.uri, visible) {
        showOverlay = true
        delay(3000)
        showOverlay = false
    }

    AnimatedVisibility(
        visible = showOverlay,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.6f)
                        ),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                ),
            contentAlignment = Alignment.BottomStart
        ) {
            Box(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = video.formattedDate,
                    color = Color.White,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                video.location?.let { location ->
                    Text(
                        text = location,
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 20.dp)
                    )
                }
            }
        }
    }
}
