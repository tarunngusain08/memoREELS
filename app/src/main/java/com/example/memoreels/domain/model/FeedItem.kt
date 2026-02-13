package com.example.memoreels.domain.model

/** Display mode assigned to a group of photos in the feed. */
enum class PhotoDisplayMode {
    /** Full-screen single image with Ken Burns zoom/pan effect. */
    SINGLE,
    /** 2-4 overlapping images with slight rotation offsets; tap to navigate. */
    STACKED,
    /** Grid layout (2x2, 1+2, 2+1) depending on photo count. */
    COLLAGE,
    /** Polaroid / framed presentation with date text. */
    STYLIZED,
    /** Slow animated zoom/pan with parallax depth on scroll. */
    MOTION
}

/** A single page in the unified feed: either a video or a group of photos. */
sealed class FeedItem {
    data class VideoItem(val video: Video) : FeedItem()
    data class PhotoGroup(
        val photos: List<Photo>,
        val displayMode: PhotoDisplayMode
    ) : FeedItem()
}
