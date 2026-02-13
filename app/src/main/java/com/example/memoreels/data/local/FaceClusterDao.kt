package com.example.memoreels.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.memoreels.data.model.FaceClusterEntity
import com.example.memoreels.data.model.FaceMediaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FaceClusterDao {

    @Insert
    suspend fun insertCluster(cluster: FaceClusterEntity): Long

    @Update
    suspend fun updateCluster(cluster: FaceClusterEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFaceMedia(entries: List<FaceMediaEntity>)

    @Query("SELECT * FROM face_clusters ORDER BY mediaCount DESC")
    fun getAllClusters(): Flow<List<FaceClusterEntity>>

    @Query("SELECT mediaUri FROM face_media WHERE clusterId = :clusterId")
    fun getMediaForCluster(clusterId: Long): Flow<List<String>>

    @Query("SELECT COUNT(*) FROM face_media WHERE clusterId = :clusterId")
    suspend fun countMediaForCluster(clusterId: Long): Int

    @Query("UPDATE face_clusters SET name = :name WHERE id = :id")
    suspend fun renameCluster(id: Long, name: String)
}
