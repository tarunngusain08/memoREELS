package com.example.memoreels.ui.screen

import android.content.Intent
import android.net.Uri
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.memoreels.ui.components.FavoriteButton
import com.example.memoreels.ui.components.MetadataOverlay
import com.example.memoreels.ui.components.ThisDayCard
import com.example.memoreels.ui.components.VideoPlayer
import com.example.memoreels.ui.viewmodel.VideoFeedViewModel
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VideoFeedScreen(
    viewModel: VideoFeedViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val pagingItems = viewModel.videos.collectAsLazyPagingItems()
    val isPlaying by viewModel.isPlaying.collectAsState()
    val favoriteUris by viewModel.favoriteUris.collectAsState()
    val thisDayVideos by viewModel.thisDayVideos.collectAsState()
    val thisDayDismissed by viewModel.thisDayDismissed.collectAsState()

    // Single shared ExoPlayer for the entire feed
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            repeatMode = Player.REPEAT_MODE_ONE
            playWhenReady = true
        }
    }

    // Release the single player when screen is disposed
    DisposableEffect(Unit) {
        onDispose { exoPlayer.release() }
    }

    // Pause/resume on lifecycle (home button, task switch)
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_STOP -> exoPlayer.playWhenReady = false
                Lifecycle.Event.ON_START -> exoPlayer.playWhenReady = isPlaying
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    // Sync ViewModel play/pause state to the player
    LaunchedEffect(isPlaying) {
        exoPlayer.playWhenReady = isPlaying
    }

    val pagerState = rememberPagerState(
        pageCount = { pagingItems.itemCount.coerceAtLeast(1) },
        initialPage = 0
    )

    // When the settled page changes, swap the media item on the single player
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.settledPage }
            .distinctUntilChanged()
            .collect { page ->
                val video =
                    if (page < pagingItems.itemCount) pagingItems.peek(page) else null
                if (video != null) {
                    exoPlayer.setMediaItem(MediaItem.fromUri(Uri.parse(video.uri)))
                    exoPlayer.prepare()
                    exoPlayer.playWhenReady = isPlaying
                }
            }
    }

    // Also load the first video when paging data arrives
    LaunchedEffect(pagingItems.itemCount) {
        if (pagingItems.itemCount > 0 && pagerState.currentPage == 0) {
            val video = pagingItems.peek(0)
            if (video != null && exoPlayer.mediaItemCount == 0) {
                exoPlayer.setMediaItem(MediaItem.fromUri(Uri.parse(video.uri)))
                exoPlayer.prepare()
                exoPlayer.playWhenReady = isPlaying
            }
        }
    }

    // --- UI ---

    if (pagingItems.itemCount == 0 && pagingItems.loadState.refresh is LoadState.Loading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color.White)
        }
    } else if (pagingItems.itemCount == 0) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No videos found. Add some memories!",
                color = Color.White,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(32.dp)
            )
        }
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            VerticalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
                userScrollEnabled = true,
                flingBehavior = PagerDefaults.flingBehavior(
                    state = pagerState,
                    snapAnimationSpec = tween(durationMillis = 250)
                )
            ) { page ->
                val video = pagingItems[page]
                if (video != null) {
                    val isCurrent = pagerState.settledPage == page

                    Box(modifier = Modifier.fillMaxSize()) {
                        if (isCurrent) {
                            VideoPlayer(
                                exoPlayer = exoPlayer,
                                onTap = { viewModel.togglePlayPause() },
                                onDoubleTap = { viewModel.onToggleFavorite(video) }
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Black)
                            )
                        }

                        MetadataOverlay(video = video, visible = isCurrent)

                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(end = 12.dp, bottom = 24.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            FavoriteButton(
                                isFavorite = favoriteUris.contains(video.uri),
                                onToggle = { viewModel.onToggleFavorite(video) }
                            )
                            IconButton(
                                onClick = {
                                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                        type = "video/*"
                                        putExtra(
                                            Intent.EXTRA_STREAM,
                                            Uri.parse(video.uri)
                                        )
                                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                    }
                                    context.startActivity(
                                        Intent.createChooser(shareIntent, "Share video")
                                    )
                                }
                            ) {
                                Icon(
                                    Icons.Default.Share,
                                    contentDescription = "Share",
                                    tint = Color.White,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        }
                    }
                }
            }

            // "This Day in History" overlay card at the top
            ThisDayCard(
                videos = thisDayVideos,
                visible = !thisDayDismissed,
                onDismiss = { viewModel.dismissThisDay() },
                onVideoClick = { /* tapping a memory thumbnail is informational for now */ },
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}
