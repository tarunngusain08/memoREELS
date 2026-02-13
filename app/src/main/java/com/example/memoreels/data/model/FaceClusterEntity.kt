package com.example.memoreels.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * A face cluster representing a detected person across multiple media.
 */
@Entity(tableName = "face_clusters")
data class FaceClusterEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    /** User-assigned name for this person, e.g. "Mom". */
    val name: String? = null,
    /** URI of the best-quality face crop for this person (avatar). */
    val avatarUri: String? = null,
    val mediaCount: Int = 0
)

/**
 * Maps a media URI to a face cluster.
 */
@Entity(
    tableName = "face_media",
    primaryKeys = ["mediaUri", "clusterId"],
    indices = [Index(value = ["clusterId"])]
)
data class FaceMediaEntity(
    val mediaUri: String,
    val clusterId: Long
)

/**
 * Stores a perceptual hash for duplicate detection.
 */
@Entity(tableName = "media_hashes")
data class MediaHashEntity(
    @PrimaryKey val mediaUri: String,
    /** 64-bit perceptual hash stored as hex string. */
    val pHash: String
)
