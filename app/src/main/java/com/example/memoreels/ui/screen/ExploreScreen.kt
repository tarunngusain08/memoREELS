package com.example.memoreels.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.memoreels.domain.model.Video
import com.example.memoreels.ui.components.VideoThumbnail
import com.example.memoreels.ui.viewmodel.ExploreViewModel
import com.example.memoreels.ui.viewmodel.VideoCollection

@Composable
fun ExploreScreen(
    viewModel: ExploreViewModel = hiltViewModel(),
    onVideoClick: (Video) -> Unit = {},
    onVideoUriClick: (String) -> Unit = {},
    onCollectionClick: (String) -> Unit = {}
) {
    val query by viewModel.query.collectAsState()
    val pagingItems = viewModel.searchResults.collectAsLazyPagingItems()
    val collections by viewModel.collections.collectAsState()
    val taggingProgress by viewModel.taggingProgress.collectAsState()
    val videoOfTheDay by viewModel.videoOfTheDay.collectAsState()
    val tagSearchResults by viewModel.tagSearchResults.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Tagging progress bar
        taggingProgress?.let { progress ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Analyzing videos... ${progress.processed}/${progress.total}",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 12.sp
                )
                LinearProgressIndicator(
                    progress = {
                        progress.processed.toFloat() / progress.total.coerceAtLeast(1)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    color = Color(0xFFE53935),
                    trackColor = Color.DarkGray
                )
            }
        }

        // Collections row (with Video of the Day as first item)
        if (collections.isNotEmpty() || videoOfTheDay != null) {
            Text(
                text = "Collections",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, top = 12.dp, bottom = 8.dp)
            )
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Video of the Day card
                videoOfTheDay?.let { uri ->
                    item {
                        VideoOfTheDayCard(
                            videoUri = uri,
                            onClick = { onVideoUriClick(uri) }
                        )
                    }
                }
                // Collection cards
                items(collections) { collection ->
                    CollectionCard(
                        collection = collection,
                        onClick = { onCollectionClick(collection.tag) }
                    )
                }
            }
        }

        // Search field
        OutlinedTextField(
            value = query,
            onValueChange = { viewModel.onQueryChange(it) },
            placeholder = { Text("Search videos...", color = Color.Gray) },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray)
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color(0xFFE53935),
                focusedBorderColor = Color(0xFFE53935),
                unfocusedBorderColor = Color.DarkGray
            ),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        // Search results / all videos
        when {
            pagingItems.loadState.refresh is LoadState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            }
            pagingItems.itemCount == 0 && tagSearchResults.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (query.isNotBlank()) "No results for \"$query\""
                        else "Search your video memories",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(32.dp)
                    )
                }
            }
            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(120.dp),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // AI tag-matched results section
                    if (query.isNotBlank() && tagSearchResults.isNotEmpty()) {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            Text(
                                text = "Matched by AI tags",
                                color = Color(0xFFE53935),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(
                                    start = 8.dp, top = 8.dp, bottom = 4.dp
                                )
                            )
                        }
                        items(tagSearchResults.size) { index ->
                            TagSearchResultItem(
                                videoUri = tagSearchResults[index],
                                onClick = { onVideoUriClick(tagSearchResults[index]) }
                            )
                        }
                        // Separator
                        if (pagingItems.itemCount > 0) {
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                Text(
                                    text = "Matched by filename",
                                    color = Color.White.copy(alpha = 0.6f),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(
                                        start = 8.dp, top = 12.dp, bottom = 4.dp
                                    )
                                )
                            }
                        }
                    }

                    // Filename-based paging results
                    items(pagingItems.itemCount) { index ->
                        val video = pagingItems[index]
                        if (video != null) {
                            SearchVideoItem(
                                video = video,
                                onClick = { onVideoClick(video) }
                            )
                        }
                    }
                }
            }
        }
    }
}

// --- Video of the Day card ---

@Composable
private fun VideoOfTheDayCard(
    videoUri: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(143.dp)
            .clip(RoundedCornerShape(14.dp))
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .width(143.dp)
                .height(198.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(Color.DarkGray)
        ) {
            VideoThumbnail(
                contentUri = videoUri,
                contentDescription = "Video of the Day",
                modifier = Modifier.fillMaxSize()
            )
            // Golden gradient overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFFFD700).copy(alpha = 0.15f),
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.7f)
                            )
                        )
                    )
            )
            // Star badge at top-right
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(28.dp)
                    .background(Color(0xFFFFD700), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Star,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(16.dp)
                )
            }
            // Label at bottom
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(10.dp)
            ) {
                Text(
                    text = "Video of",
                    color = Color(0xFFFFD700),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 14.sp
                )
                Text(
                    text = "the Day",
                    color = Color(0xFFFFD700),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 20.sp
                )
            }
        }
    }
}

// --- Collection card (143x198dp, 10% larger) ---

@Composable
private fun CollectionCard(
    collection: VideoCollection,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(143.dp)
            .clip(RoundedCornerShape(14.dp))
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .width(143.dp)
                .height(198.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(Color.DarkGray)
        ) {
            collection.thumbnailUri?.let { uri ->
                VideoThumbnail(
                    contentUri = uri,
                    contentDescription = "${collection.tag} collection, ${collection.videoCount} videos",
                    modifier = Modifier.fillMaxSize()
                )
            }
            // Gradient overlay at the bottom for readability
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.3f),
                                Color.Black.copy(alpha = 0.75f)
                            ),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
            )
            // Text at the bottom
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(10.dp)
            ) {
                Text(
                    text = collection.tag,
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 18.sp
                )
                Text(
                    text = "${collection.videoCount} ${if (collection.videoCount == 1) "video" else "videos"}",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 13.sp,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }
    }
}

// --- Tag search result item ---

@Composable
private fun TagSearchResultItem(
    videoUri: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(9f / 16f)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.DarkGray)
            .clickable(onClick = onClick)
    ) {
        VideoThumbnail(
            contentUri = videoUri,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}

// --- Filename search result item ---

@Composable
private fun SearchVideoItem(
    video: Video,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(9f / 16f)
            .background(Color.DarkGray, RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
    ) {
        VideoThumbnail(
            contentUri = video.uri,
            contentDescription = video.displayName,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = null,
                tint = Color.White
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(6.dp)
        ) {
            video.displayName?.let {
                Text(
                    text = it,
                    color = Color.White,
                    fontSize = 10.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Text(
                text = video.formattedDate,
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 9.sp
            )
        }
    }
}
