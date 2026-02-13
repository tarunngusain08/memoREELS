package com.example.memoreels.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.memoreels.data.model.VoiceNoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VoiceNoteDao {

    @Insert
    suspend fun insert(note: VoiceNoteEntity): Long

    @Query("SELECT * FROM voice_notes WHERE mediaUri = :mediaUri ORDER BY createdAt DESC LIMIT 1")
    suspend fun getForMedia(mediaUri: String): VoiceNoteEntity?

    @Query("SELECT * FROM voice_notes WHERE mediaUri = :mediaUri ORDER BY createdAt DESC LIMIT 1")
    fun observeForMedia(mediaUri: String): Flow<VoiceNoteEntity?>

    @Query("SELECT DISTINCT mediaUri FROM voice_notes")
    fun getMediaUrisWithNotes(): Flow<List<String>>

    @Query("DELETE FROM voice_notes WHERE id = :id")
    suspend fun delete(id: Long)
}
