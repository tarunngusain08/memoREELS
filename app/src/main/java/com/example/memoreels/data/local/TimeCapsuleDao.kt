package com.example.memoreels.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.memoreels.data.model.TimeCapsuleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TimeCapsuleDao {

    @Insert
    suspend fun insert(capsule: TimeCapsuleEntity): Long

    @Update
    suspend fun update(capsule: TimeCapsuleEntity)

    @Query("SELECT * FROM time_capsules ORDER BY unlockDate ASC")
    fun getAll(): Flow<List<TimeCapsuleEntity>>

    @Query("SELECT * FROM time_capsules WHERE id = :id")
    suspend fun getById(id: Long): TimeCapsuleEntity?

    @Query("SELECT * FROM time_capsules WHERE unlockDate <= :now AND isOpened = 0")
    suspend fun getUnlockedCapsules(now: Long = System.currentTimeMillis()): List<TimeCapsuleEntity>

    @Query("UPDATE time_capsules SET isOpened = 1 WHERE id = :id")
    suspend fun markOpened(id: Long)

    @Query("DELETE FROM time_capsules WHERE id = :id")
    suspend fun delete(id: Long)
}
