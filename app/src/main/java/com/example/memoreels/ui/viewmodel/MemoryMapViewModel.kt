package com.example.memoreels.ui.viewmodel

import android.content.Context
import android.location.Geocoder
import android.util.Log
import androidx.exifinterface.media.ExifInterface
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memoreels.data.datasource.PhotoDataSource
import com.example.memoreels.data.local.MediaLocationDao
import com.example.memoreels.data.model.MediaLocationEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MemoryMapViewModel @Inject constructor(
    private val mediaLocationDao: MediaLocationDao,
    private val photoDataSource: PhotoDataSource,
    @ApplicationContext private val context: Context
) : ViewModel() {

    companion object {
        private const val TAG = "MemoryMapVM"
        private const val GEOCODE_BATCH_SIZE = 50
        private const val GEOCODE_DELAY_MS = 100L
    }

    private val _locations = MutableStateFlow<List<MediaLocationEntity>>(emptyList())
    val locations: StateFlow<List<MediaLocationEntity>> = _locations.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        extractLocations()
    }

    private fun extractLocations() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val existingCount = withContext(Dispatchers.IO) { mediaLocationDao.count() }
                if (existingCount == 0) {
                    // Extract GPS from EXIF
                    val photos = withContext(Dispatchers.IO) {
                        photoDataSource.loadPhotos(limit = 1000)
                    }
                    val locations = mutableListOf<MediaLocationEntity>()
                    withContext(Dispatchers.IO) {
                        for (photo in photos) {
                            try {
                                val inputStream = context.contentResolver.openInputStream(
                                    android.net.Uri.parse(photo.uri)
                                ) ?: continue
                                val exif = ExifInterface(inputStream)
                                val latLong = exif.latLong
                                inputStream.close()
                                if (latLong != null) {
                                    locations.add(
                                        MediaLocationEntity(
                                            mediaUri = photo.uri,
                                            latitude = latLong[0],
                                            longitude = latLong[1]
                                        )
                                    )
                                }
                            } catch (_: Exception) { }
                        }
                        if (locations.isNotEmpty()) {
                            mediaLocationDao.insertAll(locations)
                        }
                    }
                }

                // Reverse-geocode locations that don't have a human-readable name yet
                reverseGeocodeLocations()

                // Load from DB
                mediaLocationDao.getAll().collect { locs ->
                    _locations.value = locs
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to extract locations", e)
                _isLoading.value = false
            }
        }
    }

    /**
     * Uses Android's Geocoder to reverse-geocode stored lat/lng into
     * human-readable place names. Batched and throttled to avoid rate limits.
     */
    private suspend fun reverseGeocodeLocations() {
        withContext(Dispatchers.IO) {
            try {
                if (!Geocoder.isPresent()) return@withContext

                val geocoder = Geocoder(context, Locale.getDefault())
                // Only geocode locations that don't have a name yet
                val allLocations = mediaLocationDao.getAllSync()
                val toGeocode = allLocations
                    .filter { it.locationName.isNullOrBlank() }
                    .take(GEOCODE_BATCH_SIZE)

                for (loc in toGeocode) {
                    try {
                        @Suppress("DEPRECATION")
                        val addresses = geocoder.getFromLocation(
                            loc.latitude, loc.longitude, 1
                        )
                        if (!addresses.isNullOrEmpty()) {
                            val addr = addresses[0]
                            val name = listOfNotNull(
                                addr.subLocality,
                                addr.locality,
                                addr.adminArea
                            ).joinToString(", ")
                            if (name.isNotBlank()) {
                                mediaLocationDao.updateLocationName(loc.mediaUri, name)
                            }
                        }
                        // Throttle to avoid Geocoder rate limits
                        delay(GEOCODE_DELAY_MS)
                    } catch (e: Exception) {
                        Log.w(TAG, "Geocode failed for ${loc.mediaUri}", e)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Reverse geocoding batch failed", e)
            }
        }
    }
}
