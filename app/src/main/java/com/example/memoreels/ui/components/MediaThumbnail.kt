package com.example.memoreels.ui.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import coil.compose.SubcomposeAsyncImage
import coil.decode.VideoFrameDecoder
import coil.request.ImageRequest
import coil.request.videoFrameMillis

/**
 * Unified thumbnail that automatically handles both video and photo URIs.
 * - Videos: extracts first frame via Coil's VideoFrameDecoder
 * - Photos: loads the image directly via Coil's standard decoder
 */
@Composable
fun MediaThumbnail(
    contentUri: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null
) {
    val context = LocalContext.current
    val isVideo = contentUri.contains("/video/")
    val effectiveDescription = contentDescription ?: if (isVideo) "Video thumbnail" else "Photo thumbnail"

    val request = if (isVideo) {
        ImageRequest.Builder(context)
            .data(Uri.parse(contentUri))
            .decoderFactory(VideoFrameDecoder.Factory())
            .videoFrameMillis(0)
            .crossfade(true)
            .build()
    } else {
        ImageRequest.Builder(context)
            .data(Uri.parse(contentUri))
            .crossfade(true)
            .build()
    }

    SubcomposeAsyncImage(
        model = request,
        contentDescription = effectiveDescription,
        contentScale = ContentScale.Crop,
        modifier = modifier.fillMaxSize(),
        loading = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.DarkGray),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color.White,
                    strokeWidth = Dp(2f)
                )
            }
        },
        error = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.DarkGray)
            )
        }
    )
}
