package com.example.memoreels.data.datasource

import com.example.memoreels.domain.model.FeedItem
import com.example.memoreels.domain.model.Photo
import com.example.memoreels.domain.model.PhotoDisplayMode
import com.example.memoreels.domain.model.Video
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin
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

    /** Weighted display mode distribution (COLLAGE removed for quality). */
    private fun randomDisplayMode(photoCount: Int): PhotoDisplayMode {
        // Single images always get SINGLE or MOTION
        if (photoCount == 1) {
            return if (Random.nextFloat() < 0.6f) PhotoDisplayMode.SINGLE
            else PhotoDisplayMode.MOTION
        }
        // Multi-image groups: weighted random (no collage)
        val roll = Random.nextFloat()
        return when {
            roll < 0.55f -> PhotoDisplayMode.STACKED
            roll < 0.80f -> PhotoDisplayMode.STYLIZED
            else -> PhotoDisplayMode.STACKED
        }
    }

    private val dateKeyFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    /** Extracts a "YYYY-MM-DD" date key from epoch millis. */
    private fun dateKey(epochMillis: Long): String {
        return dateKeyFormat.format(Date(epochMillis))
    }

    /**
     * Computes approximate distance in meters between two GPS coordinates
     * using the Haversine formula.
     */
    private fun haversineDistance(
        lat1: Double, lon1: Double,
        lat2: Double, lon2: Double
    ): Double {
        val r = 6371000.0 // Earth radius in meters
        val lat1Rad = Math.toRadians(lat1)
        val lat2Rad = Math.toRadians(lat2)
        val lon1Rad = Math.toRadians(lon1)
        val lon2Rad = Math.toRadians(lon2)
        val cosD = sin(lat1Rad) * sin(lat2Rad) +
            cos(lat1Rad) * cos(lat2Rad) * cos(lon2Rad - lon1Rad)
        return r * acos(cosD.coerceIn(-1.0, 1.0))
    }

    /**
     * Groups photos by affinity scoring: same day, shared tags, geographic proximity.
     * Only photos that score >= 3 affinity are grouped together.
     *
     * @param photos All available photos
     * @param photoTags Map of photoUri -> list of tag names
     * @param photoLocations Map of photoUri -> (latitude, longitude)
     * @return Ordered list of photo groups (each 1-4 photos)
     */
    fun groupPhotosByRelevance(
        photos: List<Photo>,
        photoTags: Map<String, List<String>>,
        photoLocations: Map<String, Pair<Double, Double>> = emptyMap()
    ): List<List<Photo>> {
        if (photos.isEmpty()) return emptyList()

        val usedUris = mutableSetOf<String>()
        val groups = mutableListOf<List<Photo>>()

        // Primary: group by calendar day
        val byDate = photos.groupBy { dateKey(it.dateAdded) }

        for ((_, dayPhotos) in byDate) {
            // Small day groups: keep as-is (1-4 photos together)
            if (dayPhotos.size <= 4) {
                groups.add(dayPhotos)
                dayPhotos.forEach { usedUris.add(it.uri) }
                continue
            }

            // Larger day groups: sub-group by affinity scoring
            val remaining = dayPhotos.toMutableList()
            while (remaining.isNotEmpty()) {
                val anchor = remaining.removeFirst()
                usedUris.add(anchor.uri)
                val group = mutableListOf(anchor)
                val anchorTags = photoTags[anchor.uri]?.map { it.lowercase() } ?: emptyList()
                val anchorLoc = photoLocations[anchor.uri]

                val candidates = remaining.toList()
                for (candidate in candidates) {
                    if (group.size >= 4) break

                    var score = 3 // Same day already gives base score of 3

                    // Shared high-value tags (skip very generic ones)
                    val candidateTags = photoTags[candidate.uri]
                        ?.map { it.lowercase() } ?: emptyList()
                    val sharedTags = anchorTags.intersect(candidateTags.toSet())
                    score += sharedTags.size * 2

                    // Geographic proximity (<100m)
                    val candidateLoc = photoLocations[candidate.uri]
                    if (anchorLoc != null && candidateLoc != null) {
                        val dist = haversineDistance(
                            anchorLoc.first, anchorLoc.second,
                            candidateLoc.first, candidateLoc.second
                        )
                        if (dist < 100.0) score += 3
                    }

                    // Only group if affinity is high enough
                    if (score >= 5) { // same-day(3) + at least one shared tag(2)
                        group.add(candidate)
                        remaining.remove(candidate)
                        usedUris.add(candidate.uri)
                    }
                }
                groups.add(group)
            }
        }

        // Any photos not yet grouped (shouldn't happen, but safety net)
        val ungrouped = photos.filter { it.uri !in usedUris }
        if (ungrouped.isNotEmpty()) {
            ungrouped.chunked(1).forEach { groups.add(it) }
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
        photoTags: Map<String, List<String>> = emptyMap(),
        photoLocations: Map<String, Pair<Double, Double>> = emptyMap()
    ): List<FeedItem> {
        if (videos.isEmpty() && photos.isEmpty()) return emptyList()

        val shuffledVideos = videos.shuffled().toMutableList()

        // Group photos by relevance if tags are available, else fall back to random
        val photoGroups = if (photoTags.isNotEmpty()) {
            groupPhotosByRelevance(photos, photoTags, photoLocations).toMutableList()
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
        photoTags: Map<String, List<String>> = emptyMap(),
        photoLocations: Map<String, Pair<Double, Double>> = emptyMap()
    ): List<FeedItem.PhotoGroup> {
        if (photos.isEmpty()) return emptyList()

        val photoGroups = if (photoTags.isNotEmpty()) {
            groupPhotosByRelevance(photos, photoTags, photoLocations)
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
