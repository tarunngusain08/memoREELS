package com.example.memoreels.data.datasource

import com.example.memoreels.domain.model.FeedItem
import com.example.memoreels.domain.model.Photo
import com.example.memoreels.domain.model.PhotoDisplayMode
import com.example.memoreels.domain.model.Video
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

/**
 * Builds a unified feed by interleaving videos and photo groups.
 *
 * The algorithm:
 * 1. Groups photos by shared ML tags for relevance.
 * 2. Shuffles video order.
 * 3. Interleaves video items with tag-grouped photo batches.
 * 4. Assigns display modes with weighted random distribution.
 */
@Singleton
class FeedItemFactory @Inject constructor() {

    /** Weighted display mode distribution. */
    private fun randomDisplayMode(photoCount: Int): PhotoDisplayMode {
        // Single images always get SINGLE or MOTION
        if (photoCount == 1) {
            return if (Random.nextFloat() < 0.6f) PhotoDisplayMode.SINGLE
            else PhotoDisplayMode.MOTION
        }
        // Multi-image groups: weighted random
        val roll = Random.nextFloat()
        return when {
            roll < 0.30f -> PhotoDisplayMode.STACKED
            roll < 0.55f -> PhotoDisplayMode.COLLAGE
            roll < 0.75f -> PhotoDisplayMode.STYLIZED
            else -> PhotoDisplayMode.STACKED // fallback to stacked for multi
        }
    }

    /**
     * Groups photos by shared ML tags for relevance-based stacking.
     * Photos with common tags are grouped together, sorted by dateAdded within groups.
     * Untagged photos fall back to date-based grouping.
     *
     * @param photos All available photos
     * @param photoTags Map of photoUri -> list of tag names
     * @return Ordered list of photo groups (each 1-4 photos)
     */
    private fun groupPhotosByRelevance(
        photos: List<Photo>,
        photoTags: Map<String, List<String>>
    ): List<List<Photo>> {
        if (photos.isEmpty()) return emptyList()

        // Build reverse index: tag -> set of photo URIs
        val tagToUris = mutableMapOf<String, MutableSet<String>>()
        photoTags.forEach { (uri, tags) ->
            tags.forEach { tag ->
                tagToUris.getOrPut(tag) { mutableSetOf() }.add(uri)
            }
        }

        val photoByUri = photos.associateBy { it.uri }
        val usedUris = mutableSetOf<String>()
        val groups = mutableListOf<List<Photo>>()

        // Sort tags by frequency (most shared first) for better grouping
        val sortedTags = tagToUris.entries
            .filter { it.value.size > 1 } // only tags shared by multiple photos
            .sortedByDescending { it.value.size }

        // Group photos by shared tags
        for ((_, uris) in sortedTags) {
            val availablePhotos = uris
                .filter { it !in usedUris && photoByUri.containsKey(it) }
                .mapNotNull { photoByUri[it] }
                .sortedBy { it.dateAdded } // temporal coherence within group

            if (availablePhotos.size < 2) continue

            // Chunk into groups of 2-4
            availablePhotos.chunked(4).forEach { chunk ->
                if (chunk.isNotEmpty()) {
                    groups.add(chunk)
                    chunk.forEach { usedUris.add(it.uri) }
                }
            }
        }

        // Remaining untagged or single-tag photos: group by date proximity
        val remaining = photos.filter { it.uri !in usedUris }.sortedBy { it.dateAdded }
        remaining.chunked(Random.nextInt(1, 5).coerceAtLeast(1)).forEach { chunk ->
            if (chunk.isNotEmpty()) {
                groups.add(chunk)
            }
        }

        return groups.shuffled()
    }

    /**
     * Creates a unified feed from videos and photos.
     * Photos are grouped by shared ML tags for relevance-based stacking,
     * then interleaved with shuffled videos.
     *
     * @param photoTags Optional map of photoUri -> list of tag names for relevance grouping
     */
    fun buildUnifiedFeed(
        videos: List<Video>,
        photos: List<Photo>,
        photoTags: Map<String, List<String>> = emptyMap()
    ): List<FeedItem> {
        if (videos.isEmpty() && photos.isEmpty()) return emptyList()

        val shuffledVideos = videos.shuffled().toMutableList()

        // Group photos by relevance if tags are available, else fall back to random
        val photoGroups = if (photoTags.isNotEmpty()) {
            groupPhotosByRelevance(photos, photoTags).toMutableList()
        } else {
            // Fallback: random grouping
            val shuffled = photos.shuffled().toMutableList()
            val groups = mutableListOf<List<Photo>>()
            while (shuffled.isNotEmpty()) {
                val sz = Random.nextInt(1, 5).coerceAtMost(shuffled.size).coerceAtLeast(1)
                groups.add((1..sz).map { shuffled.removeFirst() })
            }
            groups.toMutableList()
        }

        val feed = mutableListOf<FeedItem>()
        val nextPhotoInterval = { Random.nextInt(2, 5) }
        var countdown = nextPhotoInterval()

        while (shuffledVideos.isNotEmpty() || photoGroups.isNotEmpty()) {
            if (shuffledVideos.isNotEmpty() && (countdown > 0 || photoGroups.isEmpty())) {
                feed.add(FeedItem.VideoItem(shuffledVideos.removeFirst()))
                countdown--
            }

            if (countdown <= 0 && photoGroups.isNotEmpty()) {
                val group = photoGroups.removeFirst()
                feed.add(
                    FeedItem.PhotoGroup(
                        photos = group,
                        displayMode = randomDisplayMode(group.size)
                    )
                )
                countdown = nextPhotoInterval()
            }

            if (shuffledVideos.isEmpty() && photoGroups.isNotEmpty()) {
                val group = photoGroups.removeFirst()
                feed.add(
                    FeedItem.PhotoGroup(
                        photos = group,
                        displayMode = randomDisplayMode(group.size)
                    )
                )
            }

            if (shuffledVideos.isEmpty() && photoGroups.isEmpty()) break
        }

        return feed
    }

    /** Creates a photo-only feed (for separate mode) with tag-based grouping. */
    fun buildPhotoFeed(
        photos: List<Photo>,
        photoTags: Map<String, List<String>> = emptyMap()
    ): List<FeedItem.PhotoGroup> {
        if (photos.isEmpty()) return emptyList()

        val photoGroups = if (photoTags.isNotEmpty()) {
            groupPhotosByRelevance(photos, photoTags)
        } else {
            val shuffled = photos.shuffled().toMutableList()
            val groups = mutableListOf<List<Photo>>()
            while (shuffled.isNotEmpty()) {
                val sz = Random.nextInt(1, 5).coerceAtMost(shuffled.size)
                groups.add((1..sz).map { shuffled.removeFirst() })
            }
            groups
        }

        return photoGroups.map { group ->
            FeedItem.PhotoGroup(
                photos = group,
                displayMode = randomDisplayMode(group.size)
            )
        }
    }
}
