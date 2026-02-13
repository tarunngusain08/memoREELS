package com.example.memoreels.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A voice note recorded by the user and attached to a specific media URI.
 */
@Entity(tableName = "voice_notes")
data class VoiceNoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    /** The photo or video URI this voice note is attached to. */
    val mediaUri: String,
    /** Path to the .m4a file in app-private storage. */
    val audioPath: String,
    val durationMs: Long,
    val createdAt: Long = System.currentTimeMillis()
)
