package com.example.memoreels.ui.viewmodel

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memoreels.data.datasource.PhotoDataSource
import com.example.memoreels.data.local.FaceClusterDao
import com.example.memoreels.data.model.FaceClusterEntity
import com.example.memoreels.data.model.FaceMediaEntity
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PeopleAlbumsViewModel @Inject constructor(
    private val faceClusterDao: FaceClusterDao,
    private val photoDataSource: PhotoDataSource,
    @ApplicationContext private val context: Context
) : ViewModel() {

    companion object {
        private const val TAG = "PeopleAlbumsVM"
    }

    val clusters = faceClusterDao.getAllClusters()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _isScanning = MutableStateFlow(false)
    val isScanning: StateFlow<Boolean> = _isScanning.asStateFlow()

    private val _progress = MutableStateFlow("")
    val progress: StateFlow<String> = _progress.asStateFlow()

    fun scanFaces() {
        viewModelScope.launch {
            try {
                _isScanning.value = true
                val options = FaceDetectorOptions.Builder()
                    .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
                    .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_NONE)
                    .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
                    .setMinFaceSize(0.15f)
                    .build()
                val detector = FaceDetection.getClient(options)

                val photos = withContext(Dispatchers.IO) {
                    photoDataSource.loadPhotos(limit = 500)
                }

                _progress.value = "Scanning 0/${photos.size}..."

                // Track photos with faces for simple clustering
                val photosWithFaces = mutableListOf<Pair<String, Int>>() // (uri, faceCount)

                photos.forEachIndexed { index, photo ->
                    if (index % 20 == 0) {
                        _progress.value = "Scanning $index/${photos.size}..."
                    }
                    try {
                        val bitmap = withContext(Dispatchers.IO) {
                            context.contentResolver.openInputStream(Uri.parse(photo.uri))?.use { stream ->
                                val opts = BitmapFactory.Options().apply { inSampleSize = 4 }
                                BitmapFactory.decodeStream(stream, null, opts)
                            }
                        }
                        if (bitmap != null) {
                            val inputImage = InputImage.fromBitmap(bitmap, 0)
                            val faces = detector.process(inputImage).await()
                            if (faces.isNotEmpty()) {
                                photosWithFaces.add(photo.uri to faces.size)
                            }
                            bitmap.recycle()
                        }
                    } catch (e: Exception) {
                        Log.w(TAG, "Face detection failed for ${photo.uri}", e)
                    }
                }

                detector.close()

                // Simple clustering: group by face count as a heuristic
                // (Real implementation would use face embeddings + DBSCAN)
                _progress.value = "Creating albums..."
                withContext(Dispatchers.IO) {
                    if (photosWithFaces.isNotEmpty()) {
                        // Create a "People" cluster with all face-containing photos
                        val singleFace = photosWithFaces.filter { it.second == 1 }
                        val groupPhotos = photosWithFaces.filter { it.second >= 2 }

                        if (singleFace.isNotEmpty()) {
                            val clusterId = faceClusterDao.insertCluster(
                                FaceClusterEntity(
                                    name = "Portraits",
                                    avatarUri = singleFace.first().first,
                                    mediaCount = singleFace.size
                                )
                            )
                            faceClusterDao.insertFaceMedia(
                                singleFace.map { FaceMediaEntity(it.first, clusterId) }
                            )
                        }

                        if (groupPhotos.isNotEmpty()) {
                            val clusterId = faceClusterDao.insertCluster(
                                FaceClusterEntity(
                                    name = "Group Photos",
                                    avatarUri = groupPhotos.first().first,
                                    mediaCount = groupPhotos.size
                                )
                            )
                            faceClusterDao.insertFaceMedia(
                                groupPhotos.map { FaceMediaEntity(it.first, clusterId) }
                            )
                        }
                    }
                }

                _progress.value = "${photosWithFaces.size} photos with faces found"
                _isScanning.value = false
            } catch (e: Exception) {
                Log.e(TAG, "Face scanning failed", e)
                _progress.value = "Scan failed"
                _isScanning.value = false
            }
        }
    }

    fun renameCluster(id: Long, name: String) {
        viewModelScope.launch {
            try {
                faceClusterDao.renameCluster(id, name)
            } catch (e: Exception) {
                Log.e(TAG, "Failed to rename cluster", e)
            }
        }
    }
}
