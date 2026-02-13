package com.example.memoreels.ui.components

import android.net.Uri
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.memoreels.domain.model.FeedItem
import com.example.memoreels.domain.model.Photo
import com.example.memoreels.domain.model.PhotoDisplayMode
import kotlin.math.roundToInt

/**
 * Dispatcher composable that renders a PhotoGroup in its assigned display mode.
 * @param onPhotoClick Callback when a photo should open full-screen, passing the photo URI.
 */
@Composable
fun PhotoDisplay(
    group: FeedItem.PhotoGroup,
    modifier: Modifier = Modifier,
    onPhotoClick: (String) -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        if (group.photos.isEmpty()) return

        when (group.displayMode) {
            PhotoDisplayMode.SINGLE -> SinglePhotoView(group.photos.first(), onPhotoClick)
            PhotoDisplayMode.STACKED -> StackedPhotosView(group.photos, onPhotoClick)
            PhotoDisplayMode.COLLAGE -> CollageView(group.photos, onPhotoClick)
            PhotoDisplayMode.STYLIZED -> StylizedPhotoView(group.photos.first(), onPhotoClick)
            PhotoDisplayMode.MOTION -> MotionPhotoView(group.photos.first(), onPhotoClick)
        }

        // Photo count indicator for multi-photo groups
        if (group.photos.size > 1 && group.displayMode != PhotoDisplayMode.STACKED) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .background(
                        Color.Black.copy(alpha = 0.5f),
                        RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "${group.photos.size} photos",
                    color = Color.White,
                    fontSize = 11.sp
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────
// SINGLE: Full-screen with Ken Burns zoom/pan
// ─────────────────────────────────────────────────────────────

@Composable
private fun SinglePhotoView(
    photo: Photo,
    onPhotoClick: (String) -> Unit = {}
) {
    val infiniteTransition = rememberInfiniteTransition(label = "kenBurns")

    val scale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.18f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 8000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )
    val translateX by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 30f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 10000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "translateX"
    )
    val translateY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -20f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 9000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "translateY"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(photo.uri) {
                detectTapGestures(
                    onDoubleTap = { onPhotoClick(photo.uri) }
                )
            }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(Uri.parse(photo.uri))
                .crossfade(true)
                .size(1080, 1920)
                .build(),
            contentDescription = photo.displayName,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    this.translationX = translateX
                    this.translationY = translateY
                }
        )

        // Date overlay at bottom
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.5f)),
                        startY = 0.7f
                    )
                ),
            contentAlignment = Alignment.BottomStart
        ) {
            Text(
                text = photo.formattedDate,
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 14.sp,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────
// STACKED: 2-4 overlapping images with tap navigation
// ─────────────────────────────────────────────────────────────

@Composable
fun StackedPhotosView(photos: List<Photo>, onPhotoClick: (String) -> Unit = {}) {
    if (photos.isEmpty()) return

    var currentIndex by remember { mutableIntStateOf(0) }
    var containerWidth by remember { mutableIntStateOf(0) }

    // Pre-defined rotation offsets for the stack (subtle angles)
    val rotations = remember { listOf(-3f, 2.5f, -2f, 4f) }
    val offsets = remember { listOf(0 to 0, 10 to -6, -8 to 5, 6 to -10) }

    // Spring spec for natural-feeling scale/position (shared across cards)
    val springSpec = remember {
        spring<Float>(dampingRatio = 0.7f, stiffness = 300f)
    }
    // Tween spec for alpha and z-index (smooth fade)
    val fadeSpec = remember {
        tween<Float>(durationMillis = 250, easing = FastOutSlowInEasing)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .onSizeChanged { containerWidth = it.width }
            .pointerInput(photos.size) {
                detectTapGestures(
                    onDoubleTap = {
                        // Double-tap opens the current photo full-screen
                        onPhotoClick(photos[currentIndex].uri)
                    },
                    onTap = { offset ->
                        val halfWidth = containerWidth / 2f
                        if (offset.x > halfWidth) {
                            currentIndex = (currentIndex + 1) % photos.size
                        } else {
                            currentIndex = (currentIndex - 1 + photos.size) % photos.size
                        }
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        // Render photos in stack order (current on top)
        photos.forEachIndexed { index, photo ->
            val distFromCurrent = ((index - currentIndex + photos.size) % photos.size)
            val isFront = distFromCurrent == 0

            // --- Alpha: smooth crossfade between front/back cards ---
            val targetAlpha = when (distFromCurrent) {
                0 -> 1f
                1 -> 0.88f
                2 -> 0.75f
                else -> 0.65f
            }
            val animatedAlpha by animateFloatAsState(
                targetValue = targetAlpha,
                animationSpec = fadeSpec,
                label = "stackAlpha$index"
            )

            // --- Scale: spring physics for natural bounce ---
            val targetScale = when (distFromCurrent) {
                0 -> 1f
                1 -> 0.92f
                2 -> 0.85f
                else -> 0.80f
            }
            val animatedScale by animateFloatAsState(
                targetValue = targetScale,
                animationSpec = springSpec,
                label = "stackScale$index"
            )

            // --- Rotation: spring physics ---
            val targetRotation = if (isFront) 0f
                else rotations[index % rotations.size]
            val animatedRotation by animateFloatAsState(
                targetValue = targetRotation,
                animationSpec = springSpec,
                label = "stackRot$index"
            )

            // --- Offset: spring physics ---
            val (offX, offY) = if (isFront) 0 to 0
                else offsets[index % offsets.size]
            val animatedOffX by animateFloatAsState(
                targetValue = offX.toFloat(),
                animationSpec = springSpec,
                label = "stackOffX$index"
            )
            val animatedOffY by animateFloatAsState(
                targetValue = offY.toFloat(),
                animationSpec = springSpec,
                label = "stackOffY$index"
            )

            // --- Z-index: animated via tween to prevent instant layer swap ---
            val animatedZ by animateFloatAsState(
                targetValue = if (isFront) 10f
                    else (photos.size - distFromCurrent).toFloat(),
                animationSpec = fadeSpec,
                label = "stackZ$index"
            )

            Box(
                modifier = Modifier
                    .fillMaxSize(0.82f)
                    .zIndex(animatedZ)
                    .offset { IntOffset(animatedOffX.roundToInt(), animatedOffY.roundToInt()) }
                    .graphicsLayer {
                        scaleX = animatedScale
                        scaleY = animatedScale
                        rotationZ = animatedRotation
                        alpha = animatedAlpha
                    }
                    .shadow(
                        elevation = if (isFront) 12.dp else 4.dp,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.DarkGray)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(Uri.parse(photo.uri))
                        .crossfade(false) // Disable Coil crossfade; our own alpha handles it
                        .size(1080, 1920)
                        .build(),
                    contentDescription = photo.displayName,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        // Page indicator dots at the bottom
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
                .background(
                    Color.Black.copy(alpha = 0.4f),
                    RoundedCornerShape(12.dp)
                )
                .padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            photos.forEachIndexed { index, _ ->
                Box(
                    modifier = Modifier
                        .size(if (index == currentIndex) 8.dp else 6.dp)
                        .background(
                            if (index == currentIndex) Color.White
                            else Color.White.copy(alpha = 0.4f),
                            RoundedCornerShape(50)
                        )
                )
            }
        }

        // Navigation hint arrows
        Text(
            text = "\u276E",
            color = Color.White.copy(alpha = 0.3f),
            fontSize = 28.sp,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 12.dp)
        )
        Text(
            text = "\u276F",
            color = Color.White.copy(alpha = 0.3f),
            fontSize = 28.sp,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 12.dp)
        )
    }
}

// ─────────────────────────────────────────────────────────────
// COLLAGE: Grid layout
// ─────────────────────────────────────────────────────────────

@Composable
private fun CollageView(photos: List<Photo>, onPhotoClick: (String) -> Unit = {}) {
    if (photos.isEmpty()) return
    val gap = 3.dp
    val cornerRadius = 8.dp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(photos.first().uri) {
                detectTapGestures(
                    onDoubleTap = { onPhotoClick(photos.first().uri) }
                )
            }
            .padding(gap),
        contentAlignment = Alignment.Center
    ) {
        when (photos.size) {
            1 -> {
                CollageImage(photos[0], Modifier.fillMaxSize(), cornerRadius)
            }
            2 -> {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(gap)
                ) {
                    CollageImage(photos[0], Modifier.weight(1f).fillMaxHeight(), cornerRadius)
                    CollageImage(photos[1], Modifier.weight(1f).fillMaxHeight(), cornerRadius)
                }
            }
            3 -> {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(gap)
                ) {
                    CollageImage(
                        photos[0],
                        Modifier.weight(1.2f).fillMaxHeight(),
                        cornerRadius
                    )
                    Column(
                        modifier = Modifier.weight(0.8f).fillMaxHeight(),
                        verticalArrangement = Arrangement.spacedBy(gap)
                    ) {
                        CollageImage(photos[1], Modifier.weight(1f).fillMaxWidth(), cornerRadius)
                        CollageImage(photos[2], Modifier.weight(1f).fillMaxWidth(), cornerRadius)
                    }
                }
            }
            else -> {
                // 4+ images: 2x2 grid
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(gap)
                ) {
                    Row(
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(gap)
                    ) {
                        CollageImage(photos[0], Modifier.weight(1f).fillMaxHeight(), cornerRadius)
                        CollageImage(photos[1], Modifier.weight(1f).fillMaxHeight(), cornerRadius)
                    }
                    Row(
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(gap)
                    ) {
                        CollageImage(photos[2], Modifier.weight(1f).fillMaxHeight(), cornerRadius)
                        if (photos.size > 3) {
                            CollageImage(
                                photos[3],
                                Modifier.weight(1f).fillMaxHeight(),
                                cornerRadius
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CollageImage(
    photo: Photo,
    modifier: Modifier,
    cornerRadius: androidx.compose.ui.unit.Dp
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(Uri.parse(photo.uri))
            .crossfade(true)
            .size(1080, 1920)
            .build(),
        contentDescription = photo.displayName,
        contentScale = ContentScale.Crop,
        modifier = modifier.clip(RoundedCornerShape(cornerRadius))
    )
}

// ─────────────────────────────────────────────────────────────
// STYLIZED: Polaroid frame with date
// ─────────────────────────────────────────────────────────────

@Composable
private fun StylizedPhotoView(photo: Photo, onPhotoClick: (String) -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A1A))
            .pointerInput(photo.uri) {
                detectTapGestures(
                    onDoubleTap = { onPhotoClick(photo.uri) }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        // Polaroid card
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .rotate(-2f)
                .shadow(16.dp, RoundedCornerShape(4.dp))
                .background(Color.White, RoundedCornerShape(4.dp))
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(Uri.parse(photo.uri))
                    .crossfade(true)
                    .size(1080, 1920)
                    .build(),
                contentDescription = photo.displayName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(4f / 5f)
                    .clip(RoundedCornerShape(2.dp))
            )

            // Date text at the bottom in a handwritten style
            Text(
                text = photo.formattedDate,
                color = Color(0xFF444444),
                fontSize = 14.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Light,
                modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────
// MOTION: Slow zoom/pan with depth
// ─────────────────────────────────────────────────────────────

@Composable
private fun MotionPhotoView(
    photo: Photo,
    onPhotoClick: (String) -> Unit = {}
) {
    val infiniteTransition = rememberInfiniteTransition(label = "motion")

    val scale by infiniteTransition.animateFloat(
        initialValue = 1.05f,
        targetValue = 1.22f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 12000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "motionScale"
    )
    val translateX by infiniteTransition.animateFloat(
        initialValue = -25f,
        targetValue = 25f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 14000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "motionTx"
    )
    val translateY by infiniteTransition.animateFloat(
        initialValue = 15f,
        targetValue = -15f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 11000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "motionTy"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(photo.uri) {
                detectTapGestures(
                    onDoubleTap = { onPhotoClick(photo.uri) }
                )
            }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(Uri.parse(photo.uri))
                .crossfade(true)
                .size(1080, 1920)
                .build(),
            contentDescription = photo.displayName,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    this.translationX = translateX
                    this.translationY = translateY
                }
        )

        // Subtle vignette overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.35f)
                        )
                    )
                )
        )

        // Date overlay
        Text(
            text = photo.formattedDate,
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 14.sp,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        )
    }
}
