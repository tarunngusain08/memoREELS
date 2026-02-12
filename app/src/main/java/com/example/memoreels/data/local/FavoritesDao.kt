package com.example.memoreels.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.memoreels.data.model.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {

    @Query("SELECT * FROM favorites ORDER BY createdAt DESC")
    fun getAllFavorites(): Flow<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(entity: FavoriteEntity)

    @Query("DELETE FROM favorites WHERE videoUri = :uri")
    suspend fun removeFavorite(uri: String)

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE videoUri = :uri)")
    suspend fun isFavorite(uri: String): Boolean

    @Query("SELECT * FROM favorites WHERE videoUri = :uri LIMIT 1")
    suspend fun getFavoriteByUri(uri: String): FavoriteEntity?
}
