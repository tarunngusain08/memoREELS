package com.example.memoreels.ui.screen

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.memoreels.ui.components.PhotoDisplay
import com.example.memoreels.ui.viewmodel.PhotoFeedViewModel

/**
 * Photo-only feed for "Separate" mode.
 * Shows photo groups in a vertical pager with the same smooth transitions
 * as the main video feed.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhotoFeedScreen(
    viewModel: PhotoFeedViewModel = hiltViewModel(),
    onPhotoClick: (String) -> Unit = {}
) {
    val photoGroups by viewModel.photoGroups.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    when {
        isLoading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        }
        photoGroups.isEmpty() -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No photos found. Capture some memories!",
                    color = Color.White,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(32.dp)
                )
            }
        }
        else -> {
            val pagerState = rememberPagerState(
                pageCount = { photoGroups.size },
                initialPage = 0
            )

            VerticalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                beyondBoundsPageCount = 1,
                flingBehavior = PagerDefaults.flingBehavior(
                    state = pagerState,
                    snapAnimationSpec = tween(300, easing = FastOutSlowInEasing)
                )
            ) { page ->
                val group = photoGroups.getOrNull(page)
                if (group == null) {
                    Box(Modifier.fillMaxSize().background(Color.Black))
                    return@VerticalPager
                }
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
                    PhotoDisplay(
                        group = group,
                        modifier = Modifier.fillMaxSize(),
                        onPhotoClick = onPhotoClick
                    )
                }
            }
        }
    }
}
