package com.example.memoreels.ui.screen

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.memoreels.ui.components.VideoPlayer
import com.example.memoreels.ui.viewmodel.ExploreViewModel
import kotlinx.coroutines.flow.distinctUntilChanged

/**
 * Full-screen vertical pager feed for videos in a specific collection (tag).
 * Mirrors the main feed pattern: single shared ExoPlayer, swipe to navigate.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CollectionFeedScreen(
    tag: String,
    startIndex: Int = 0,
    viewModel: ExploreViewModel = hiltViewModel(),
    onBack: () -> Unit = {}
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val videoUris by viewModel.getVideosForTag(tag).collectAsState(initial = emptyList())

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            repeatMode = Player.REPEAT_MODE_ONE
            playWhenReady = true
        }
    }

    DisposableEffect(Unit) {
        onDispose { exoPlayer.release() }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_STOP -> exoPlayer.playWhenReady = false
                Lifecycle.Event.ON_START -> exoPlayer.playWhenReady = true
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    if (videoUris.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Text("No videos", color = Color.White)
        }
        return
    }

    val pagerState = rememberPagerState(
        pageCount = { videoUris.size },
        initialPage = startIndex.coerceIn(0, (videoUris.size - 1).coerceAtLeast(0))
    )

    // Swap media when settled page changes
    LaunchedEffect(pagerState, videoUris) {
        snapshotFlow { pagerState.settledPage }
            .distinctUntilChanged()
            .collect { page ->
                if (page < videoUris.size) {
                    exoPlayer.setMediaItem(MediaItem.fromUri(Uri.parse(videoUris[page])))
                    exoPlayer.prepare()
                    exoPlayer.playWhenReady = true
                }
            }
    }

    // Load first video when URIs arrive
    LaunchedEffect(videoUris) {
        if (videoUris.isNotEmpty() && exoPlayer.mediaItemCount == 0) {
            val idx = startIndex.coerceIn(0, videoUris.size - 1)
            exoPlayer.setMediaItem(MediaItem.fromUri(Uri.parse(videoUris[idx])))
            exoPlayer.prepare()
            exoPlayer.playWhenReady = true
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        VerticalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val isCurrent = pagerState.settledPage == page
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                if (isCurrent) {
                    VideoPlayer(
                        exoPlayer = exoPlayer,
                        onTap = {
                            exoPlayer.playWhenReady = !exoPlayer.playWhenReady
                        },
                        onDoubleTap = { }
                    )
                }
            }
        }

        // Back button + tag label overlay
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(8.dp)
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }

        // Tag label
        Text(
            text = tag,
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 20.dp)
        )

        // Page indicator
        Text(
            text = "${pagerState.currentPage + 1} / ${videoUris.size}",
            color = Color.White.copy(alpha = 0.6f),
            fontSize = 12.sp,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 20.dp, end = 16.dp)
        )
    }
}
