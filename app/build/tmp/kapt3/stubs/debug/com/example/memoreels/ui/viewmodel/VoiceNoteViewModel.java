package com.example.memoreels.ui.viewmodel;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.util.Log;
import androidx.lifecycle.ViewModel;
import com.example.memoreels.data.local.VoiceNoteDao;
import com.example.memoreels.data.model.VoiceNoteEntity;
import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.StateFlow;
import java.io.File;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0002\b\t\b\u0007\u0018\u0000 $2\u00020\u0001:\u0001$B\u0019\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u001b\u001a\u00020\u001cJ\u000e\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u000eJ\b\u0010\u001f\u001a\u00020\u001cH\u0014J\u0006\u0010 \u001a\u00020\u001cJ\u000e\u0010!\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u000eJ\u0006\u0010\"\u001a\u00020\u001cJ\u000e\u0010#\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u000eR\u0016\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0012R\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0012R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006%"}, d2 = {"Lcom/example/memoreels/ui/viewmodel/VoiceNoteViewModel;", "Landroidx/lifecycle/ViewModel;", "voiceNoteDao", "Lcom/example/memoreels/data/local/VoiceNoteDao;", "context", "Landroid/content/Context;", "(Lcom/example/memoreels/data/local/VoiceNoteDao;Landroid/content/Context;)V", "_currentNote", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/example/memoreels/data/model/VoiceNoteEntity;", "_isPlaying", "", "_isRecording", "currentFilePath", "", "currentNote", "Lkotlinx/coroutines/flow/StateFlow;", "getCurrentNote", "()Lkotlinx/coroutines/flow/StateFlow;", "isPlaying", "isRecording", "player", "Landroid/media/MediaPlayer;", "recorder", "Landroid/media/MediaRecorder;", "recordingStartTime", "", "deleteNote", "", "loadNoteForMedia", "mediaUri", "onCleared", "playNote", "startRecording", "stopPlaying", "stopRecording", "Companion", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class VoiceNoteViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.memoreels.data.local.VoiceNoteDao voiceNoteDao = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "VoiceNoteVM";
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isRecording = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isRecording = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isPlaying = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isPlaying = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.memoreels.data.model.VoiceNoteEntity> _currentNote = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.memoreels.data.model.VoiceNoteEntity> currentNote = null;
    @org.jetbrains.annotations.Nullable()
    private android.media.MediaRecorder recorder;
    @org.jetbrains.annotations.Nullable()
    private android.media.MediaPlayer player;
    private long recordingStartTime = 0L;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String currentFilePath;
    @org.jetbrains.annotations.NotNull()
    public static final com.example.memoreels.ui.viewmodel.VoiceNoteViewModel.Companion Companion = null;
    
    @javax.inject.Inject()
    public VoiceNoteViewModel(@org.jetbrains.annotations.NotNull()
    com.example.memoreels.data.local.VoiceNoteDao voiceNoteDao, @dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isRecording() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isPlaying() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.memoreels.data.model.VoiceNoteEntity> getCurrentNote() {
        return null;
    }
    
    public final void loadNoteForMedia(@org.jetbrains.annotations.NotNull()
    java.lang.String mediaUri) {
    }
    
    public final void startRecording(@org.jetbrains.annotations.NotNull()
    java.lang.String mediaUri) {
    }
    
    public final void stopRecording(@org.jetbrains.annotations.NotNull()
    java.lang.String mediaUri) {
    }
    
    public final void playNote() {
    }
    
    public final void stopPlaying() {
    }
    
    public final void deleteNote() {
    }
    
    @java.lang.Override()
    protected void onCleared() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/example/memoreels/ui/viewmodel/VoiceNoteViewModel$Companion;", "", "()V", "TAG", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}