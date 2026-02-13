package com.example.memoreels.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.memoreels.domain.model.Favorite
import com.example.memoreels.domain.model.Video
import com.example.memoreels.ui.components.MediaThumbnail
import com.example.memoreels.ui.viewmodel.FavoritesViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = hiltViewModel(),
    onVideoClick: (Video) -> Unit = {}
) {
    val favorites by viewModel.favorites.collectAsState(initial = emptyList())

    if (favorites.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No favorites yet. Tap the heart on videos to add them!",
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(32.dp)
            )
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(120.dp),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            items(favorites) { favorite ->
                FavoriteMediaItem(
                    favorite = favorite,
                    onClick = { onVideoClick(favorite.toVideo()) }
                )
            }
        }
    }
}

@Composable
private fun FavoriteMediaItem(
    favorite: Favorite,
    onClick: () -> Unit
) {
    val isVideo = favorite.videoUri.contains("/video/")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(9f / 16f)
            .padding(4.dp)
            .clickable(onClick = onClick)
    ) {
        MediaThumbnail(
            contentUri = favorite.videoUri,
            contentDescription = if (isVideo) "Video thumbnail" else "Photo thumbnail",
            modifier = Modifier.fillMaxSize()
        )
        // Only show play icon overlay for videos
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
        Text(
            text = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
                .format(Date(favorite.createdAt)),
            color = Color.White,
            fontSize = 10.sp,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(4.dp)
        )
    }
}

private fun Favorite.toVideo(): Video = Video(
    id = 0,
    uri = videoUri,
    path = null,
    displayName = null,
    dateAdded = createdAt,
    duration = 0,
    location = null,
    thumbnailUri = videoUri,
    mimeType = null
)
