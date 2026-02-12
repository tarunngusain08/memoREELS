package com.example.memoreels.data.model

data class VideoEntity(
    val id: Long,
    val uri: String,
    val path: String?,
    val displayName: String?,
    val dateAdded: Long,
    val duration: Long,
    val location: String?,
    val thumbnailUri: String?,
    val mimeType: String?
)
