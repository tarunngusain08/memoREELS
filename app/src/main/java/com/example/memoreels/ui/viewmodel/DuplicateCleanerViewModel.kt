package com.example.memoreels.ui.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color as AndroidColor
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memoreels.data.datasource.PhotoDataSource
import com.example.memoreels.data.local.MediaHashDao
import com.example.memoreels.data.model.MediaHashEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/** A group of near-duplicate photos. */
data class DuplicateGroup(
    val uris: List<String>,
    val suggestedKeep: String
)

@HiltViewModel
class DuplicateCleanerViewModel @Inject constructor(
    private val mediaHashDao: MediaHashDao,
    private val photoDataSource: PhotoDataSource,
    @ApplicationContext private val context: Context
) : ViewModel() {

    companion object {
        private const val TAG = "DuplicateCleanerVM"
        private const val HASH_SIZE = 8
        private const val HAMMING_THRESHOLD = 10
    }

    private val _groups = MutableStateFlow<List<DuplicateGroup>>(emptyList())
    val groups: StateFlow<List<DuplicateGroup>> = _groups.asStateFlow()

    private val _isScanning = MutableStateFlow(false)
    val isScanning: StateFlow<Boolean> = _isScanning.asStateFlow()

    private val _progress = MutableStateFlow("")
    val progress: StateFlow<String> = _progress.asStateFlow()

    fun scanForDuplicates() {
        viewModelScope.launch {
            try {
                _isScanning.value = true
                _progress.value = "Loading photos..."

                val photos = withContext(Dispatchers.IO) {
                    photoDataSource.loadPhotos(limit = 2000)
                }

                // Compute pHash for each photo
                _progress.value = "Computing hashes (0/${photos.size})..."
                val hashes = mutableListOf<MediaHashEntity>()
                photos.forEachIndexed { index, photo ->
                    if (index % 50 == 0) {
                        _progress.value = "Computing hashes ($index/${photos.size})..."
                    }
                    val hash = withContext(Dispatchers.IO) { computePHash(photo.uri) }
                    if (hash != null) {
                        hashes.add(MediaHashEntity(mediaUri = photo.uri, pHash = hash))
                    }
                }

                withContext(Dispatchers.IO) { mediaHashDao.insertAll(hashes) }

                // Find duplicates using Hamming distance
                _progress.value = "Finding duplicates..."
                val duplicateGroups = withContext(Dispatchers.IO) { findDuplicates(hashes) }
                _groups.value = duplicateGroups
                _progress.value = "${duplicateGroups.size} duplicate groups found"
                _isScanning.value = false
            } catch (e: Exception) {
                Log.e(TAG, "Duplicate scan failed", e)
                _isScanning.value = false
                _progress.value = "Scan failed"
            }
        }
    }

    /** Compute a perceptual hash (average hash) for an image URI. */
    private fun computePHash(uri: String): String? {
        return try {
            val inputStream = context.contentResolver.openInputStream(Uri.parse(uri)) ?: return null
            val options = BitmapFactory.Options().apply { inSampleSize = 8 }
            val bitmap = BitmapFactory.decodeStream(inputStream, null, options)
            inputStream.close()
            if (bitmap == null) return null

            val scaled = Bitmap.createScaledBitmap(bitmap, HASH_SIZE, HASH_SIZE, true)
            bitmap.recycle()

            // Convert to grayscale and compute average
            val pixels = IntArray(HASH_SIZE * HASH_SIZE)
            scaled.getPixels(pixels, 0, HASH_SIZE, 0, 0, HASH_SIZE, HASH_SIZE)
            scaled.recycle()

            val grays = pixels.map { px ->
                (AndroidColor.red(px) * 0.299 + AndroidColor.green(px) * 0.587 + AndroidColor.blue(px) * 0.114).toInt()
            }
            val avg = grays.average()

            // Build hash: 1 if pixel > average, else 0
            val sb = StringBuilder()
            grays.forEach { g -> sb.append(if (g > avg) "1" else "0") }
            sb.toString()
        } catch (e: Exception) {
            null
        }
    }

    /** Group hashes by Hamming distance. */
    private fun findDuplicates(hashes: List<MediaHashEntity>): List<DuplicateGroup> {
        val used = mutableSetOf<String>()
        val groups = mutableListOf<DuplicateGroup>()

        for (i in hashes.indices) {
            if (hashes[i].mediaUri in used) continue
            val group = mutableListOf(hashes[i].mediaUri)
            for (j in i + 1 until hashes.size) {
                if (hashes[j].mediaUri in used) continue
                val dist = hammingDistance(hashes[i].pHash, hashes[j].pHash)
                if (dist <= HAMMING_THRESHOLD) {
                    group.add(hashes[j].mediaUri)
                    used.add(hashes[j].mediaUri)
                }
            }
            if (group.size > 1) {
                used.add(hashes[i].mediaUri)
                groups.add(DuplicateGroup(uris = group, suggestedKeep = group.first()))
            }
        }
        return groups
    }

    private fun hammingDistance(a: String, b: String): Int {
        if (a.length != b.length) return Int.MAX_VALUE
        return a.zip(b).count { (c1, c2) -> c1 != c2 }
    }
}
