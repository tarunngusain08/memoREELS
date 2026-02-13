package com.example.memoreels.data.model

import androidx.room.Entity
import androidx.room.Index

/** Stores an ML Kit image label for a video/photo. Composite PK = (videoUri, tag). */
@Entity(
    tableName = "video_tags",
    primaryKeys = ["videoUri", "tag"],
    indices = [
        Index(value = ["tag"]),
        Index(value = ["tag", "confidence"])
    ]
)
data class VideoTagEntity(
    val videoUri: String,
    val tag: String,
    val confidence: Float
)
