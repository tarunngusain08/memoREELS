package com.example.memoreels.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a sealed time capsule containing a set of media URIs
 * that unlock on a specified future date.
 */
@Entity(tableName = "time_capsules")
data class TimeCapsuleEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    /** Comma-separated media URIs. */
    val mediaUris: String,
    /** Unix timestamp (ms) when the capsule unlocks. */
    val unlockDate: Long,
    val createdAt: Long = System.currentTimeMillis(),
    val isOpened: Boolean = false
)
