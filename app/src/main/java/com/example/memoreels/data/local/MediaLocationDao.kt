package com.example.memoreels.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.memoreels.data.model.MediaLocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MediaLocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(locations: List<MediaLocationEntity>)

    @Query("SELECT * FROM media_locations")
    fun getAll(): Flow<List<MediaLocationEntity>>

    @Query("SELECT * FROM media_locations WHERE latitude BETWEEN :minLat AND :maxLat AND longitude BETWEEN :minLng AND :maxLng")
    fun getInBounds(minLat: Double, maxLat: Double, minLng: Double, maxLng: Double): Flow<List<MediaLocationEntity>>

    @Query("SELECT COUNT(*) FROM media_locations")
    suspend fun count(): Int
}
