package com.example.memoreels.domain.model

data class Favorite(
    val videoUri: String,
    val note: String?,
    val createdAt: Long
)
