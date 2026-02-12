package com.example.memoreels.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey
    val videoUri: String,
    val note: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)
