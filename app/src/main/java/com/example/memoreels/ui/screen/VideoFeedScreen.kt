package com.example.memoreels.ui.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.memoreels.data.preferences.FeedMode
import com.example.memoreels.domain.model.FeedItem
import com.example.memoreels.ui.components.FavoriteButton
import com.example.memoreels.ui.components.MemoryOfMoment
import com.example.memoreels.ui.components.MemoryOfMomentCard
import com.example.memoreels.ui.components.MetadataOverlay
import com.example.memoreels.ui.components.PhotoDisplay
import com.example.memoreels.ui.components.ThisDayCard
import com.example.memoreels.ui.components.VideoPlayer
import com.example.memoreels.ui.components.VideoSeekBar
import com.example.memoreels.ui.components.MediaThumbnail
import com.example.memoreels.ui.viewmodel.VideoFeedViewModel
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VideoFeedScreen(
    viewModel: VideoFeedViewModel = hiltViewModel(),
    onPhotoClick: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val feedMode by viewModel.feedMode.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()
    val isMuted by viewModel.isMuted.collectAsState()
    val favoriteUris by viewModel.favoriteUris.collectAsState()
    val thisDayVideos by viewModel.thisDayVideos.collectAsState()
    val thisDayDismissed by viewModel.thisDayDismissed.collectAsState()
    val memoryOfMoment by viewModel.memoryOfMoment.collectAsState()
    val memoryDismissed by viewModel.memoryDismissed.collectAsState()
    val autoMute by viewModel.autoMute.collectAsState()
    val loopVideos by viewModel.loopVideos.collectAsState()

    when (feedMode) {
        FeedMode.UNIFIED -> UnifiedFeedContent(
            viewModel = viewModel,
            context = context,
            lifecycleOwner = lifecycleOwner,
            isPlaying = isPlaying,
            isMuted = isMuted,
            favoriteUris = favoriteUris,
            thisDayVideos = thisDayVideos,
            thisDayDismissed = thisDayDismissed,
            memoryOfMoment = memoryOfMoment,
            memoryDismissed = memoryDismissed,
            onDismissMemory = { viewModel.dismissMemory() },
            onPhotoClick = onPhotoClick,
            autoMute = autoMute,
            loopVideos = loopVideos
        )
        FeedMode.SEPARATE -> VideoOnlyFeedContent(
            viewModel = viewModel,
            context = context,
            lifecycleOwner = lifecycleOwner,
            isPlaying = isPlaying,
            isMuted = isMuted,
            favoriteUris = favoriteUris,
            thisDayVideos = thisDayVideos,
            thisDayDismissed = thisDayDismissed,
            memoryOfMoment = memoryOfMoment,
            memoryDismissed = memoryDismissed,
            onDismissMemory = { viewModel.dismissMemory() },
            autoMute = autoMute,
            loopVideos = loopVideos
        )
    }
}

// ───────────────────────────────────────────────────
// UNIFIED: Videos + Photos mixed
// ───────────────────────────────────────────────────

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun UnifiedFeedContent(
    viewModel: VideoFeedViewModel,
    context: android.content.Context,
    lifecycleOwner: androidx.lifecycle.LifecycleOwner,
    isPlaying: Boolean,
    isMuted: Boolean,
    favoriteUris: Set<String>,
    thisDayVideos: List<com.example.memoreels.domain.model.Video>,
    thisDayDismissed: Boolean,
    memoryOfMoment: MemoryOfMoment?,
    memoryDismissed: Boolean,
    onDismissMemory: () -> Unit,
    onPhotoClick: (String) -> Unit,
    autoMute: Boolean,
    loopVideos: Boolean
) {
    val feedItems by viewModel.unifiedFeed.collectAsState()

    // Auto-dismiss Memory of Moment after 8 seconds
    LaunchedEffect(memoryOfMoment) {
        if (memoryOfMoment != null) {
            delay(8000)
            onDismissMemory()
        }
    }

    // Single shared ExoPlayer with lifecycle safety
    val isPlayerReleased = remember { mutableStateOf(false) }
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(C.USAGE_MEDIA)
                    .setContentType(C.AUDIO_CONTENT_TYPE_MOVIE)
                    .build(),
                true
            )
            repeatMode = if (loopVideos) Player.REPEAT_MODE_ONE
                else Player.REPEAT_MODE_OFF
            playWhenReady = true
            volume = if (autoMute) 0f else 1f
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            isPlayerReleased.value = true
            exoPlayer.release()
        }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (isPlayerReleased.value) return@LifecycleEventObserver
            try {
                when (event) {
                    Lifecycle.Event.ON_STOP -> exoPlayer.playWhenReady = false
                    Lifecycle.Event.ON_START -> exoPlayer.playWhenReady = isPlaying
                    else -> {}
                }
            } catch (_: Exception) { }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    LaunchedEffect(isPlaying) {
        if (!isPlayerReleased.value) {
            try { exoPlayer.playWhenReady = isPlaying } catch (_: Exception) { }
        }
    }
    LaunchedEffect(isMuted) {
        if (!isPlayerReleased.value) {
            try { exoPlayer.volume = if (isMuted) 0f else 1f } catch (_: Exception) { }
        }
    }
    // React to loopVideos preference changes
    LaunchedEffect(loopVideos) {
        if (!isPlayerReleased.value) {
            try {
                exoPlayer.repeatMode = if (loopVideos) Player.REPEAT_MODE_ONE
                    else Player.REPEAT_MODE_OFF
            } catch (_: Exception) { }
        }
    }
    // React to autoMute preference changes
    LaunchedEffect(autoMute) {
        if (!isPlayerReleased.value) {
            try { exoPlayer.volume = if (autoMute) 0f else 1f } catch (_: Exception) { }
        }
    }

    if (feedItems.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize().background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color.White)
        }
        return
    }

    val pagerState = rememberPagerState(
        pageCount = { feedItems.size },
        initialPage = 0
    )

    // Swap media when settled page changes to a video item
    LaunchedEffect(pagerState, feedItems) {
        snapshotFlow { pagerState.settledPage }
            .distinctUntilChanged()
            .collect { page ->
                if (isPlayerReleased.value) return@collect
                val item = feedItems.getOrNull(page) ?: return@collect
                try {
                    if (item is FeedItem.VideoItem) {
                        exoPlayer.setMediaItem(
                            MediaItem.fromUri(Uri.parse(item.video.uri))
                        )
                        exoPlayer.prepare()
                        exoPlayer.playWhenReady = isPlaying
                        exoPlayer.volume = if (isMuted) 0f else 1f
                    } else {
                        exoPlayer.playWhenReady = false
                    }
                } catch (_: Exception) { }
            }
    }

    // Auto-advance to next video when loop is OFF and video ends
    val autoAdvanceScope = rememberCoroutineScope()
    LaunchedEffect(exoPlayer, loopVideos) {
        if (loopVideos || isPlayerReleased.value) return@LaunchedEffect
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == Player.STATE_ENDED) {
                    val nextPage = pagerState.currentPage + 1
                    if (nextPage < pagerState.pageCount) {
                        autoAdvanceScope.launch {
                            pagerState.animateScrollToPage(nextPage)
                        }
                    }
                }
            }
        }
        exoPlayer.addListener(listener)
        try { awaitCancellation() } finally { exoPlayer.removeListener(listener) }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        VerticalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            beyondBoundsPageCount = 1,
            flingBehavior = PagerDefaults.flingBehavior(
                state = pagerState,
                snapAnimationSpec = tween(300, easing = FastOutSlowInEasing)
            )
        ) { page ->
            val item = feedItems.getOrNull(page)
            if (item == null) {
                Box(Modifier.fillMaxSize().background(Color.Black))
                return@VerticalPager
            }
            val isCurrent = pagerState.settledPage == page
            val pageOffset = (pagerState.currentPage - page) +
                pagerState.currentPageOffsetFraction
            val absOffset = kotlin.math.abs(pageOffset).coerceIn(0f, 1f)

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        alpha = lerp(0.5f, 1f, 1f - absOffset)
                        scaleX = lerp(0.92f, 1f, 1f - absOffset)
                        scaleY = lerp(0.92f, 1f, 1f - absOffset)
                    }
            ) {
                when (item) {
                    is FeedItem.VideoItem -> {
                        VideoFeedPage(
                            video = item.video,
                            isCurrent = isCurrent,
                            exoPlayer = exoPlayer,
                            isPlaying = isPlaying,
                            isFavorite = favoriteUris.contains(item.video.uri),
                            onTogglePlayPause = { viewModel.togglePlayPause() },
                            onToggleFavorite = { viewModel.onToggleFavorite(item.video) },
                            onShare = { shareVideo(context, item.video.uri) }
                        )
                    }
                    is FeedItem.PhotoGroup -> {
                        PhotoDisplay(
                            group = item,
                            modifier = Modifier.fillMaxSize(),
                            onPhotoClick = onPhotoClick
                        )
                    }
                }
            }
        }

        // Overlays at top
        Column(modifier = Modifier.align(Alignment.TopCenter)) {
            // This Day overlay
            ThisDayCard(
                videos = thisDayVideos,
                visible = !thisDayDismissed,
                onDismiss = { viewModel.dismissThisDay() },
                onVideoClick = { }
            )

            // Memory of the Moment overlay
            if (memoryOfMoment != null) {
                MemoryOfMomentCard(
                    memory = memoryOfMoment,
                    visible = !memoryDismissed,
                    onDismiss = onDismissMemory,
                    onClick = { /* Navigate to full-screen view */ }
                )
            }
        }

    }
}

// ───────────────────────────────────────────────────
// SEPARATE: Video-only feed (original behavior)
// ───────────────────────────────────────────────────

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun VideoOnlyFeedContent(
    viewModel: VideoFeedViewModel,
    context: android.content.Context,
    lifecycleOwner: androidx.lifecycle.LifecycleOwner,
    isPlaying: Boolean,
    isMuted: Boolean,
    favoriteUris: Set<String>,
    thisDayVideos: List<com.example.memoreels.domain.model.Video>,
    thisDayDismissed: Boolean,
    memoryOfMoment: MemoryOfMoment?,
    memoryDismissed: Boolean,
    onDismissMemory: () -> Unit,
    autoMute: Boolean,
    loopVideos: Boolean
) {
    val pagingItems = viewModel.videos.collectAsLazyPagingItems()

    // Auto-dismiss Memory of Moment after 8 seconds
    LaunchedEffect(memoryOfMoment) {
        if (memoryOfMoment != null) {
            delay(8000)
            onDismissMemory()
        }
    }

    val isPlayerReleased = remember { mutableStateOf(false) }
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(C.USAGE_MEDIA)
                    .setContentType(C.AUDIO_CONTENT_TYPE_MOVIE)
                    .build(),
                true
            )
            repeatMode = if (loopVideos) Player.REPEAT_MODE_ONE
                else Player.REPEAT_MODE_OFF
            playWhenReady = true
            volume = if (autoMute) 0f else 1f
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            isPlayerReleased.value = true
            exoPlayer.release()
        }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (isPlayerReleased.value) return@LifecycleEventObserver
            try {
                when (event) {
                    Lifecycle.Event.ON_STOP -> exoPlayer.playWhenReady = false
                    Lifecycle.Event.ON_START -> exoPlayer.playWhenReady = isPlaying
                    else -> {}
                }
            } catch (_: Exception) { }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    LaunchedEffect(isPlaying) {
        if (!isPlayerReleased.value) {
            try { exoPlayer.playWhenReady = isPlaying } catch (_: Exception) { }
        }
    }
    LaunchedEffect(isMuted) {
        if (!isPlayerReleased.value) {
            try { exoPlayer.volume = if (isMuted) 0f else 1f } catch (_: Exception) { }
        }
    }
    // React to loopVideos preference changes
    LaunchedEffect(loopVideos) {
        if (!isPlayerReleased.value) {
            try {
                exoPlayer.repeatMode = if (loopVideos) Player.REPEAT_MODE_ONE
                    else Player.REPEAT_MODE_OFF
            } catch (_: Exception) { }
        }
    }
    // React to autoMute preference changes
    LaunchedEffect(autoMute) {
        if (!isPlayerReleased.value) {
            try { exoPlayer.volume = if (autoMute) 0f else 1f } catch (_: Exception) { }
        }
    }

    val pagerState = rememberPagerState(
        pageCount = { pagingItems.itemCount.coerceAtLeast(1) },
        initialPage = 0
    )

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.settledPage }
            .distinctUntilChanged()
            .collect { page ->
                if (isPlayerReleased.value) return@collect
                val video = if (page < pagingItems.itemCount) pagingItems.peek(page) else null
                if (video != null) {
                    try {
                        exoPlayer.setMediaItem(MediaItem.fromUri(Uri.parse(video.uri)))
                        exoPlayer.prepare()
                        exoPlayer.playWhenReady = isPlaying
                        exoPlayer.volume = if (isMuted) 0f else 1f
                    } catch (_: Exception) { }
                }
            }
    }

    LaunchedEffect(pagingItems.itemCount) {
        if (isPlayerReleased.value) return@LaunchedEffect
        if (pagingItems.itemCount > 0 && pagerState.currentPage == 0) {
            val video = pagingItems.peek(0)
            if (video != null && exoPlayer.mediaItemCount == 0) {
                try {
                    exoPlayer.setMediaItem(MediaItem.fromUri(Uri.parse(video.uri)))
                    exoPlayer.prepare()
                    exoPlayer.playWhenReady = isPlaying
                } catch (_: Exception) { }
            }
        }
    }

    // Auto-advance to next video when loop is OFF and video ends
    val videoAutoAdvanceScope = rememberCoroutineScope()
    LaunchedEffect(exoPlayer, loopVideos) {
        if (loopVideos || isPlayerReleased.value) return@LaunchedEffect
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == Player.STATE_ENDED) {
                    val nextPage = pagerState.currentPage + 1
                    if (nextPage < pagerState.pageCount) {
                        videoAutoAdvanceScope.launch {
                            pagerState.animateScrollToPage(nextPage)
                        }
                    }
                }
            }
        }
        exoPlayer.addListener(listener)
        try { awaitCancellation() } finally { exoPlayer.removeListener(listener) }
    }

    if (pagingItems.itemCount == 0 && pagingItems.loadState.refresh is LoadState.Loading) {
        Box(
            modifier = Modifier.fillMaxSize().background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color.White)
        }
    } else if (pagingItems.itemCount == 0) {
        Box(
            modifier = Modifier.fillMaxSize().background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No videos found. Add some memories!",
                color = Color.White, fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(32.dp)
            )
        }
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            VerticalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
                beyondBoundsPageCount = 1,
                flingBehavior = PagerDefaults.flingBehavior(
                    state = pagerState,
                    snapAnimationSpec = tween(300, easing = FastOutSlowInEasing)
                )
            ) { page ->
                val video = pagingItems[page]
                if (video != null) {
                    val isCurrent = pagerState.settledPage == page
                    val pageOffset = (pagerState.currentPage - page) +
                        pagerState.currentPageOffsetFraction
                    val absOffset = kotlin.math.abs(pageOffset).coerceIn(0f, 1f)

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer {
                                alpha = lerp(0.5f, 1f, 1f - absOffset)
                                scaleX = lerp(0.92f, 1f, 1f - absOffset)
                                scaleY = lerp(0.92f, 1f, 1f - absOffset)
                            }
                    ) {
                        VideoFeedPage(
                            video = video,
                            isCurrent = isCurrent,
                            exoPlayer = exoPlayer,
                            isPlaying = isPlaying,
                            isFavorite = favoriteUris.contains(video.uri),
                            onTogglePlayPause = { viewModel.togglePlayPause() },
                            onToggleFavorite = { viewModel.onToggleFavorite(video) },
                            onShare = { shareVideo(context, video.uri) }
                        )
                    }
                }
            }

            // Overlays at top
            Column(modifier = Modifier.align(Alignment.TopCenter)) {
                ThisDayCard(
                    videos = thisDayVideos,
                    visible = !thisDayDismissed,
                    onDismiss = { viewModel.dismissThisDay() },
                    onVideoClick = { }
                )

                if (memoryOfMoment != null) {
                    MemoryOfMomentCard(
                        memory = memoryOfMoment,
                        visible = !memoryDismissed,
                        onDismiss = onDismissMemory,
                        onClick = { }
                    )
                }
            }

        }
    }
}

// ───────────────────────────────────────────────────
// Shared: Single video page with seek bar
// ───────────────────────────────────────────────────

@Composable
private fun VideoFeedPage(
    video: com.example.memoreels.domain.model.Video,
    isCurrent: Boolean,
    exoPlayer: ExoPlayer,
    isPlaying: Boolean,
    isFavorite: Boolean,
    onTogglePlayPause: () -> Unit,
    onToggleFavorite: () -> Unit,
    onShare: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (isCurrent) {
            VideoPlayer(
                exoPlayer = exoPlayer,
                onTap = { onTogglePlayPause() },
                onDoubleTap = { onToggleFavorite() }
            )
        } else {
            MediaThumbnail(
                contentUri = video.uri,
                contentDescription = video.displayName,
                modifier = Modifier.fillMaxSize()
            )
        }

        MetadataOverlay(video = video, visible = isCurrent)

        // Right-side action buttons (above seek bar area)
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 12.dp, bottom = 96.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FavoriteButton(
                isFavorite = isFavorite,
                onToggle = { onToggleFavorite() }
            )
            IconButton(onClick = onShare) {
                Icon(
                    Icons.Default.Share,
                    contentDescription = "Share",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        // Seek bar: visible when paused, with end padding to clear action buttons
        if (isCurrent) {
            VideoSeekBar(
                exoPlayer = exoPlayer,
                visible = !isPlaying,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp, end = 56.dp)
            )
        }
    }
}

/** Share a video via Android share sheet. */
private fun shareVideo(context: android.content.Context, videoUri: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "video/*"
        putExtra(Intent.EXTRA_STREAM, Uri.parse(videoUri))
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    context.startActivity(Intent.createChooser(shareIntent, "Share video"))
}
