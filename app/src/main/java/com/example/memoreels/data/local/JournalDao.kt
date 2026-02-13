package com.example.memoreels.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.memoreels.data.model.JournalEntryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JournalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entry: JournalEntryEntity)

    @Query("SELECT * FROM journal_entries WHERE date = :date")
    suspend fun getForDate(date: String): JournalEntryEntity?

    @Query("SELECT * FROM journal_entries ORDER BY date DESC")
    fun getAll(): Flow<List<JournalEntryEntity>>

    @Query("DELETE FROM journal_entries WHERE date = :date")
    suspend fun delete(date: String)
}
