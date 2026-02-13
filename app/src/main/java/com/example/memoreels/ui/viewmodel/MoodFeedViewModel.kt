package com.example.memoreels.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memoreels.data.local.VideoTagDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/** Available mood filters. */
enum class Mood(val label: String, val emoji: String, val tags: List<String>) {
    ALL("All", "🌈", emptyList()),
    HAPPY("Happy", "😊", listOf("Smile", "Fun", "Joy", "Party", "Celebration")),
    NATURE("Nature", "🌿", listOf("Plant", "Tree", "Mountain", "Sky", "Flower", "Landscape", "Nature")),
    TRAVEL("Travel", "✈️", listOf("Building", "Vehicle", "Travel", "Architecture", "City")),
    FOOD("Food", "🍕", listOf("Food", "Cuisine", "Dish", "Meal", "Fruit")),
    PETS("Pets", "🐾", listOf("Dog", "Cat", "Animal", "Pet", "Bird")),
    WATER("Water", "🌊", listOf("Water", "Sea", "Ocean", "Beach", "Lake", "River"))
}

@HiltViewModel
class MoodFeedViewModel @Inject constructor(
    private val videoTagDao: VideoTagDao
) : ViewModel() {

    companion object {
        private const val TAG = "MoodFeedVM"
    }

    private val _selectedMood = MutableStateFlow(Mood.ALL)
    val selectedMood: StateFlow<Mood> = _selectedMood.asStateFlow()

    private val _filteredUris = MutableStateFlow<List<String>>(emptyList())
    val filteredUris: StateFlow<List<String>> = _filteredUris.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadForMood(Mood.ALL)
    }

    fun selectMood(mood: Mood) {
        _selectedMood.value = mood
        loadForMood(mood)
    }

    private fun loadForMood(mood: Mood) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val uris = if (mood == Mood.ALL) {
                    videoTagDao.getProcessedUris()
                } else {
                    val matchedUris = mutableSetOf<String>()
                    for (tag in mood.tags) {
                        val results = videoTagDao.searchByTagDirect("%${tag.lowercase()}%")
                        matchedUris.addAll(results)
                    }
                    matchedUris.toList()
                }
                _filteredUris.value = uris.shuffled()
                _isLoading.value = false
            } catch (e: Exception) {
                Log.e(TAG, "Failed to filter by mood", e)
                _isLoading.value = false
            }
        }
    }
}
