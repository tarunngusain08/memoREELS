package com.example.memoreels.data.model

import androidx.room.Entity

/** Stores an ML Kit image label for a video. Composite PK = (videoUri, tag). */
@Entity(tableName = "video_tags", primaryKeys = ["videoUri", "tag"])
data class VideoTagEntity(
    val videoUri: String,
    val tag: String,
    val confidence: Float
)
