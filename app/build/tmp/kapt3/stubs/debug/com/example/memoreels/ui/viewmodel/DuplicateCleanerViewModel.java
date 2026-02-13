package com.example.memoreels.ui.viewmodel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import androidx.lifecycle.ViewModel;
import com.example.memoreels.data.datasource.PhotoDataSource;
import com.example.memoreels.data.local.MediaHashDao;
import com.example.memoreels.data.model.MediaHashEntity;
import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;
import android.graphics.Color;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 #2\u00020\u0001:\u0001#B!\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0001\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0012\u0010\u0018\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0019\u001a\u00020\u0010H\u0002J\u001c\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u000bH\u0002J\u0018\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u00102\u0006\u0010 \u001a\u00020\u0010H\u0002J\u0006\u0010!\u001a\u00020\"R\u001a\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0014R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00100\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0014\u00a8\u0006$"}, d2 = {"Lcom/example/memoreels/ui/viewmodel/DuplicateCleanerViewModel;", "Landroidx/lifecycle/ViewModel;", "mediaHashDao", "Lcom/example/memoreels/data/local/MediaHashDao;", "photoDataSource", "Lcom/example/memoreels/data/datasource/PhotoDataSource;", "context", "Landroid/content/Context;", "(Lcom/example/memoreels/data/local/MediaHashDao;Lcom/example/memoreels/data/datasource/PhotoDataSource;Landroid/content/Context;)V", "_groups", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "Lcom/example/memoreels/ui/viewmodel/DuplicateGroup;", "_isScanning", "", "_progress", "", "groups", "Lkotlinx/coroutines/flow/StateFlow;", "getGroups", "()Lkotlinx/coroutines/flow/StateFlow;", "isScanning", "progress", "getProgress", "computePHash", "uri", "findDuplicates", "hashes", "Lcom/example/memoreels/data/model/MediaHashEntity;", "hammingDistance", "", "a", "b", "scanForDuplicates", "", "Companion", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class DuplicateCleanerViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.memoreels.data.local.MediaHashDao mediaHashDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.memoreels.data.datasource.PhotoDataSource photoDataSource = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "DuplicateCleanerVM";
    private static final int HASH_SIZE = 8;
    private static final int HAMMING_THRESHOLD = 10;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.example.memoreels.ui.viewmodel.DuplicateGroup>> _groups = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.memoreels.ui.viewmodel.DuplicateGroup>> groups = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isScanning = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isScanning = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _progress = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> progress = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.example.memoreels.ui.viewmodel.DuplicateCleanerViewModel.Companion Companion = null;
    
    @javax.inject.Inject()
    public DuplicateCleanerViewModel(@org.jetbrains.annotations.NotNull()
    com.example.memoreels.data.local.MediaHashDao mediaHashDao, @org.jetbrains.annotations.NotNull()
    com.example.memoreels.data.datasource.PhotoDataSource photoDataSource, @dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.memoreels.ui.viewmodel.DuplicateGroup>> getGroups() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isScanning() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getProgress() {
        return null;
    }
    
    public final void scanForDuplicates() {
    }
    
    /**
     * Compute a perceptual hash (average hash) for an image URI.
     */
    private final java.lang.String computePHash(java.lang.String uri) {
        return null;
    }
    
    /**
     * Group hashes by Hamming distance.
     */
    private final java.util.List<com.example.memoreels.ui.viewmodel.DuplicateGroup> findDuplicates(java.util.List<com.example.memoreels.data.model.MediaHashEntity> hashes) {
        return null;
    }
    
    private final int hammingDistance(java.lang.String a, java.lang.String b) {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/example/memoreels/ui/viewmodel/DuplicateCleanerViewModel$Companion;", "", "()V", "HAMMING_THRESHOLD", "", "HASH_SIZE", "TAG", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}