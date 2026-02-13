package com.example.memoreels.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.memoreels.data.model.MediaHashEntity

@Dao
interface MediaHashDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(hashes: List<MediaHashEntity>)

    @Query("SELECT * FROM media_hashes")
    suspend fun getAll(): List<MediaHashEntity>

    @Query("SELECT COUNT(*) FROM media_hashes")
    suspend fun count(): Int

    @Query("DELETE FROM media_hashes WHERE mediaUri = :uri")
    suspend fun delete(uri: String)
}
