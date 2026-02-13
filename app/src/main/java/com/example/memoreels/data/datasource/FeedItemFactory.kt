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
 * 1. Shuffles both lists independently.
 * 2. Picks from each list alternately, with a bias toward videos (~60-70%).
 * 3. Groups consecutive photo picks (1-4) into a PhotoGroup with a random display mode.
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
     * Creates a unified feed from videos and photos.
     * Videos and photos are shuffled, then interleaved with groups of 1-4 photos
     * scattered between video items.
     */
    fun buildUnifiedFeed(videos: List<Video>, photos: List<Photo>): List<FeedItem> {
        if (videos.isEmpty() && photos.isEmpty()) return emptyList()

        val shuffledVideos = videos.shuffled().toMutableList()
        val shuffledPhotos = photos.shuffled().toMutableList()
        val feed = mutableListOf<FeedItem>()

        // Interleave: after every 2-4 videos, insert a photo group
        var videoInsertCount = 0
        val nextPhotoInterval = { Random.nextInt(2, 5) } // insert photos every 2-4 videos
        var countdown = nextPhotoInterval()

        while (shuffledVideos.isNotEmpty() || shuffledPhotos.isNotEmpty()) {
            // Add a video if available and we haven't hit the photo interval
            if (shuffledVideos.isNotEmpty() && (countdown > 0 || shuffledPhotos.isEmpty())) {
                feed.add(FeedItem.VideoItem(shuffledVideos.removeFirst()))
                videoInsertCount++
                countdown--
            }

            // Time to insert a photo group
            if (countdown <= 0 && shuffledPhotos.isNotEmpty()) {
                val groupSize = Random.nextInt(1, 5)
                    .coerceAtMost(shuffledPhotos.size)
                    .coerceAtLeast(1)
                if (groupSize > 0 && shuffledPhotos.size >= groupSize) {
                    val photoGroup = (1..groupSize).map { shuffledPhotos.removeFirst() }
                    feed.add(
                        FeedItem.PhotoGroup(
                            photos = photoGroup,
                            displayMode = randomDisplayMode(groupSize)
                        )
                    )
                }
                countdown = nextPhotoInterval()
            }

            // If only photos remain, drain them in groups
            if (shuffledVideos.isEmpty() && shuffledPhotos.isNotEmpty()) {
                val groupSize = Random.nextInt(1, 5)
                    .coerceAtMost(shuffledPhotos.size)
                    .coerceAtLeast(1)
                if (shuffledPhotos.size >= groupSize) {
                    val photoGroup = (1..groupSize).map { shuffledPhotos.removeFirst() }
                    feed.add(
                        FeedItem.PhotoGroup(
                            photos = photoGroup,
                            displayMode = randomDisplayMode(groupSize)
                        )
                    )
                }
            }

            // Safety: break if neither list made progress to prevent infinite loop
            if (shuffledVideos.isEmpty() && shuffledPhotos.isEmpty()) break
        }

        return feed
    }

    /** Creates a photo-only feed (for separate mode). */
    fun buildPhotoFeed(photos: List<Photo>): List<FeedItem.PhotoGroup> {
        if (photos.isEmpty()) return emptyList()

        val shuffled = photos.shuffled().toMutableList()
        val groups = mutableListOf<FeedItem.PhotoGroup>()

        while (shuffled.isNotEmpty()) {
            val groupSize = Random.nextInt(1, 5).coerceAtMost(shuffled.size)
            val photoGroup = (1..groupSize).map { shuffled.removeFirst() }
            groups.add(
                FeedItem.PhotoGroup(
                    photos = photoGroup,
                    displayMode = randomDisplayMode(groupSize)
                )
            )
        }

        return groups
    }
}
