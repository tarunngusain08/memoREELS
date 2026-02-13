package com.example.memoreels.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.example.memoreels.ui.components.MediaThumbnail
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
    val memoryOfMoment by viewModel.memoryOfMoment.collectAsState()
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
                    text = "Analyzing memories... ${progress.processed}/${progress.total}",
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

        // Instagram-style circular category tiles
        if (collections.isNotEmpty() || memoryOfMoment != null) {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Memory of the Moment as special first bubble
                memoryOfMoment?.let { memory ->
                    item {
                        CategoryBubble(
                            label = "Memory",
                            thumbnailUri = memory.uri,
                            isSpecial = true,
                            onClick = { onVideoUriClick(memory.uri) }
                        )
                    }
                }
                // Category bubbles for collections
                items(collections) { collection ->
                    CategoryBubble(
                        label = collection.tag,
                        thumbnailUri = collection.thumbnailUri,
                        isSpecial = false,
                        onClick = { onCollectionClick(collection.tag) }
                    )
                }
            }
        } else if (taggingProgress != null) {
            // Show placeholder when tags are still being generated
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    color = Color(0xFFE53935),
                    strokeWidth = 2.dp
                )
                Text(
                    text = "Scanning your media for categories...",
                    color = Color.White.copy(alpha = 0.6f),
                    fontSize = 13.sp
                )
            }
        }

        // Search field
        OutlinedTextField(
            value = query,
            onValueChange = { viewModel.onQueryChange(it) },
            placeholder = { Text("Search memories by tag...", color = Color.Gray) },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.Gray)
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
                .padding(horizontal = 16.dp, vertical = 4.dp)
        )

        // Search results / all content
        when {
            pagingItems.loadState.refresh is LoadState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            }
            pagingItems.loadState.refresh is LoadState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Something went wrong",
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 16.sp,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        Button(
                            onClick = { pagingItems.retry() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFE53935)
                            )
                        ) {
                            Icon(
                                Icons.Default.Refresh,
                                contentDescription = "Retry",
                                tint = Color.White
                            )
                            Text("  Retry", color = Color.White)
                        }
                    }
                }
            }
            pagingItems.itemCount == 0 && tagSearchResults.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (query.isNotBlank()) "No results for \"$query\""
                        else "Search your memories by tags like mountain, sunset, river...",
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
                                mediaUri = tagSearchResults[index],
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

// --- Instagram-style circular category bubble ---

@Composable
private fun CategoryBubble(
    label: String,
    thumbnailUri: String?,
    isSpecial: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(72.dp)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val borderBrush = if (isSpecial) {
            Brush.linearGradient(
                colors = listOf(
                    Color(0xFFFFD54F),
                    Color(0xFFFFA726),
                    Color(0xFFE53935)
                )
            )
        } else {
            Brush.linearGradient(
                colors = listOf(
                    Color(0xFFE53935),
                    Color(0xFFAD1457),
                    Color(0xFF6A1B9A)
                )
            )
        }

        Box(
            modifier = Modifier
                .size(64.dp)
                .border(width = 2.dp, brush = borderBrush, shape = CircleShape)
                .padding(3.dp)
                .clip(CircleShape)
                .background(Color.DarkGray),
            contentAlignment = Alignment.Center
        ) {
            if (thumbnailUri != null) {
                MediaThumbnail(
                    contentUri = thumbnailUri,
                    contentDescription = label,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
            } else {
                Icon(
                    Icons.Default.Star,
                    contentDescription = label,
                    tint = Color.White.copy(alpha = 0.5f),
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Text(
            text = label,
            color = Color.White.copy(alpha = 0.85f),
            fontSize = 11.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

// --- Tag search result item (now uses MediaThumbnail) ---

@Composable
private fun TagSearchResultItem(
    mediaUri: String,
    onClick: () -> Unit
) {
    val isVideo = mediaUri.contains("/video/")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(9f / 16f)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.DarkGray)
            .clickable(onClick = onClick)
    ) {
        MediaThumbnail(
            contentUri = mediaUri,
            contentDescription = if (isVideo) "Video thumbnail" else "Photo thumbnail",
            modifier = Modifier.fillMaxSize()
        )
        if (isVideo) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play video",
                    tint = Color.White
                )
            }
        }
    }
}

// --- Filename search result item (now uses MediaThumbnail) ---

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
        MediaThumbnail(
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
                contentDescription = "Play video",
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
