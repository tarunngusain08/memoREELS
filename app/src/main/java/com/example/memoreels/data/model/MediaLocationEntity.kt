package com.example.memoreels.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Stores GPS coordinates extracted from media EXIF data.
 */
@Entity(
    tableName = "media_locations",
    indices = [Index(value = ["latitude", "longitude"])]
)
data class MediaLocationEntity(
    @PrimaryKey val mediaUri: String,
    val latitude: Double,
    val longitude: Double,
    val locationName: String? = null
)
