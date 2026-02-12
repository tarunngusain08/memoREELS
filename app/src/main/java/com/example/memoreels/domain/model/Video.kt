package com.example.memoreels.domain.model

data class Video(
    val id: Long,
    val uri: String,
    val path: String?,
    val displayName: String?,
    val dateAdded: Long,
    val duration: Long,
    val location: String?,
    val thumbnailUri: String?,
    val mimeType: String?
) {
    val formattedDate: String
        get() {
            val sdf = java.text.SimpleDateFormat("MMM d, yyyy", java.util.Locale.getDefault())
            return sdf.format(java.util.Date(dateAdded))
        }
}
