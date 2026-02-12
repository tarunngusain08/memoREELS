package com.example.memoreels.data.datasource

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideoPagingSourceFactory @Inject constructor(
    private val context: android.content.Context
) {

    fun create(
        exclusions: List<String> = emptyList(),
        query: String = "",
        shuffle: Boolean = true
    ): VideoPagingSource {
        return VideoPagingSource(context, exclusions, query, shuffle)
    }
}
