package com.example.memoreels.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.memoreels.data.model.FaceClusterEntity
import com.example.memoreels.data.model.FaceMediaEntity
import com.example.memoreels.data.model.FavoriteEntity
import com.example.memoreels.data.model.JournalEntryEntity
import com.example.memoreels.data.model.MediaHashEntity
import com.example.memoreels.data.model.MediaLocationEntity
import com.example.memoreels.data.model.TimeCapsuleEntity
import com.example.memoreels.data.model.VideoTagEntity
import com.example.memoreels.data.model.VoiceNoteEntity

@Database(
    entities = [
        FavoriteEntity::class,
        VideoTagEntity::class,
        TimeCapsuleEntity::class,
        VoiceNoteEntity::class,
        JournalEntryEntity::class,
        MediaLocationEntity::class,
        FaceClusterEntity::class,
        FaceMediaEntity::class,
        MediaHashEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
    abstract fun videoTagDao(): VideoTagDao
    abstract fun timeCapsuleDao(): TimeCapsuleDao
    abstract fun voiceNoteDao(): VoiceNoteDao
    abstract fun journalDao(): JournalDao
    abstract fun mediaLocationDao(): MediaLocationDao
    abstract fun faceClusterDao(): FaceClusterDao
    abstract fun mediaHashDao(): MediaHashDao
}
