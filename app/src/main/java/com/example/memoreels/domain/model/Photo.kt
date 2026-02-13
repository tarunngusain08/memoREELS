package com.example.memoreels.domain.model

/** Represents a local image from the device gallery. */
data class Photo(
    val id: Long,
    val uri: String,
    val path: String?,
    val displayName: String?,
    val dateAdded: Long,
    val width: Int,
    val height: Int,
    val mimeType: String?
) {
    val formattedDate: String
        get() {
            val sdf = java.text.SimpleDateFormat("MMM d, yyyy", java.util.Locale.getDefault())
            return sdf.format(java.util.Date(dateAdded))
        }
}
