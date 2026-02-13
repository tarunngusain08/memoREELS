package com.example.memoreels.ui.viewmodel

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memoreels.data.local.VoiceNoteDao
import com.example.memoreels.data.model.VoiceNoteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

@HiltViewModel
class VoiceNoteViewModel @Inject constructor(
    private val voiceNoteDao: VoiceNoteDao,
    @ApplicationContext private val context: Context
) : ViewModel() {

    companion object {
        private const val TAG = "VoiceNoteVM"
    }

    private val _isRecording = MutableStateFlow(false)
    val isRecording: StateFlow<Boolean> = _isRecording.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _currentNote = MutableStateFlow<VoiceNoteEntity?>(null)
    val currentNote: StateFlow<VoiceNoteEntity?> = _currentNote.asStateFlow()

    private var recorder: MediaRecorder? = null
    private var player: MediaPlayer? = null
    private var recordingStartTime: Long = 0
    private var currentFilePath: String? = null

    fun loadNoteForMedia(mediaUri: String) {
        viewModelScope.launch {
            try {
                _currentNote.value = voiceNoteDao.getForMedia(mediaUri)
            } catch (e: Exception) {
                Log.e(TAG, "Failed to load voice note", e)
            }
        }
    }

    fun startRecording(mediaUri: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val dir = File(context.filesDir, "voice_notes")
                if (!dir.exists()) dir.mkdirs()
                val file = File(dir, "vn_${System.currentTimeMillis()}.m4a")
                currentFilePath = file.absolutePath

                recorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    MediaRecorder(context)
                } else {
                    @Suppress("DEPRECATION")
                    MediaRecorder()
                }.apply {
                    setAudioSource(MediaRecorder.AudioSource.MIC)
                    setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                    setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                    setAudioSamplingRate(44100)
                    setAudioEncodingBitRate(128000)
                    setOutputFile(file.absolutePath)
                    prepare()
                    start()
                }
                recordingStartTime = System.currentTimeMillis()
                _isRecording.value = true
            } catch (e: Exception) {
                Log.e(TAG, "Failed to start recording", e)
            }
        }
    }

    fun stopRecording(mediaUri: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                recorder?.apply {
                    stop()
                    release()
                }
                recorder = null
                _isRecording.value = false

                val duration = System.currentTimeMillis() - recordingStartTime
                val path = currentFilePath ?: return@launch

                val entity = VoiceNoteEntity(
                    mediaUri = mediaUri,
                    audioPath = path,
                    durationMs = duration
                )
                voiceNoteDao.insert(entity)
                _currentNote.value = entity
            } catch (e: Exception) {
                Log.e(TAG, "Failed to stop recording", e)
                _isRecording.value = false
            }
        }
    }

    fun playNote() {
        val note = _currentNote.value ?: return
        viewModelScope.launch(Dispatchers.IO) {
            try {
                player?.release()
                player = MediaPlayer().apply {
                    setDataSource(note.audioPath)
                    prepare()
                    setOnCompletionListener {
                        _isPlaying.value = false
                    }
                    start()
                }
                _isPlaying.value = true
            } catch (e: Exception) {
                Log.e(TAG, "Failed to play voice note", e)
            }
        }
    }

    fun stopPlaying() {
        try {
            player?.stop()
            player?.release()
            player = null
            _isPlaying.value = false
        } catch (e: Exception) {
            Log.e(TAG, "Failed to stop playback", e)
        }
    }

    fun deleteNote() {
        val note = _currentNote.value ?: return
        viewModelScope.launch {
            try {
                voiceNoteDao.delete(note.id)
                withContext(Dispatchers.IO) {
                    File(note.audioPath).delete()
                }
                _currentNote.value = null
            } catch (e: Exception) {
                Log.e(TAG, "Failed to delete note", e)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        recorder?.release()
        player?.release()
    }
}
