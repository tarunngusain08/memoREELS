package com.example.memoreels.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.memoreels.data.model.FavoriteEntity
import com.example.memoreels.data.model.VideoTagEntity

@Database(
    entities = [FavoriteEntity::class, VideoTagEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
    abstract fun videoTagDao(): VideoTagDao
}
