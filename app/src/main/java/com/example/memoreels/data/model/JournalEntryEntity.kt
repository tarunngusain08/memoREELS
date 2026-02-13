package com.example.memoreels.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A text journal entry attached to a specific date (YYYY-MM-DD).
 */
@Entity(tableName = "journal_entries")
data class JournalEntryEntity(
    @PrimaryKey val date: String,
    val note: String,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
