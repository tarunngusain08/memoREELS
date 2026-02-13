package com.example.memoreels.ui.viewmodel;

import android.util.Log;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagingData;
import com.example.memoreels.data.local.VideoTagDao;
import com.example.memoreels.data.ml.PhotoTagger;
import com.example.memoreels.data.ml.TaggingProgress;
import com.example.memoreels.data.ml.VideoTagger;
import com.example.memoreels.domain.model.Video;
import com.example.memoreels.domain.repository.VideoRepository;
import com.example.memoreels.ui.components.MemoryOfMoment;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.ExperimentalCoroutinesApi;
import kotlinx.coroutines.FlowPreview;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\b\u0007\u0018\u0000 /2\u00020\u0001:\u0001/B\'\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u0006\u0010)\u001a\u00020*J\u001a\u0010+\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00140\u001f2\u0006\u0010,\u001a\u00020\u0011J\u000e\u0010-\u001a\u00020*2\u0006\u0010.\u001a\u00020\u0011R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\u00140\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0017\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\r0\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0017R\u0019\u0010\u001a\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0017R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00110\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0017R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u001e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020!0 0\u001f\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u001d\u0010$\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00140\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u0017R\u0019\u0010&\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\'0\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010\u0017R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00060"}, d2 = {"Lcom/example/memoreels/ui/viewmodel/ExploreViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/example/memoreels/domain/repository/VideoRepository;", "videoTagDao", "Lcom/example/memoreels/data/local/VideoTagDao;", "videoTagger", "Lcom/example/memoreels/data/ml/VideoTagger;", "photoTagger", "Lcom/example/memoreels/data/ml/PhotoTagger;", "(Lcom/example/memoreels/domain/repository/VideoRepository;Lcom/example/memoreels/data/local/VideoTagDao;Lcom/example/memoreels/data/ml/VideoTagger;Lcom/example/memoreels/data/ml/PhotoTagger;)V", "_memoryDismissed", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_memoryOfMoment", "Lcom/example/memoreels/ui/components/MemoryOfMoment;", "_query", "", "collections", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/example/memoreels/ui/viewmodel/VideoCollection;", "getCollections", "()Lkotlinx/coroutines/flow/StateFlow;", "memoryDismissed", "getMemoryDismissed", "memoryOfMoment", "getMemoryOfMoment", "query", "getQuery", "searchResults", "Lkotlinx/coroutines/flow/Flow;", "Landroidx/paging/PagingData;", "Lcom/example/memoreels/domain/model/Video;", "getSearchResults", "()Lkotlinx/coroutines/flow/Flow;", "tagSearchResults", "getTagSearchResults", "taggingProgress", "Lcom/example/memoreels/data/ml/TaggingProgress;", "getTaggingProgress", "dismissMemory", "", "getVideosForTag", "tag", "onQueryChange", "newQuery", "Companion", "app_debug"})
@kotlin.OptIn(markerClass = {kotlinx.coroutines.FlowPreview.class, kotlinx.coroutines.ExperimentalCoroutinesApi.class})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class ExploreViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.memoreels.domain.repository.VideoRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.memoreels.data.local.VideoTagDao videoTagDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.memoreels.data.ml.VideoTagger videoTagger = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.memoreels.data.ml.PhotoTagger photoTagger = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "ExploreViewModel";
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.memoreels.ui.viewmodel.VideoCollection>> collections = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.memoreels.ui.components.MemoryOfMoment> _memoryOfMoment = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.memoreels.ui.components.MemoryOfMoment> memoryOfMoment = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _memoryDismissed = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> memoryDismissed = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.memoreels.data.ml.TaggingProgress> taggingProgress = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _query = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> query = null;
    
    /**
     * Filename-based search via MediaStore.
     */
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<androidx.paging.PagingData<com.example.memoreels.domain.model.Video>> searchResults = null;
    
    /**
     * AI-tag-based search results (separate from PagingData to avoid combine crash).
     */
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<java.lang.String>> tagSearchResults = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.example.memoreels.ui.viewmodel.ExploreViewModel.Companion Companion = null;
    
    @javax.inject.Inject()
    public ExploreViewModel(@org.jetbrains.annotations.NotNull()
    com.example.memoreels.domain.repository.VideoRepository repository, @org.jetbrains.annotations.NotNull()
    com.example.memoreels.data.local.VideoTagDao videoTagDao, @org.jetbrains.annotations.NotNull()
    com.example.memoreels.data.ml.VideoTagger videoTagger, @org.jetbrains.annotations.NotNull()
    com.example.memoreels.data.ml.PhotoTagger photoTagger) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.memoreels.ui.viewmodel.VideoCollection>> getCollections() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.memoreels.ui.components.MemoryOfMoment> getMemoryOfMoment() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getMemoryDismissed() {
        return null;
    }
    
    public final void dismissMemory() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.memoreels.data.ml.TaggingProgress> getTaggingProgress() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getQuery() {
        return null;
    }
    
    /**
     * Filename-based search via MediaStore.
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<androidx.paging.PagingData<com.example.memoreels.domain.model.Video>> getSearchResults() {
        return null;
    }
    
    /**
     * AI-tag-based search results (separate from PagingData to avoid combine crash).
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<java.lang.String>> getTagSearchResults() {
        return null;
    }
    
    /**
     * URIs for a specific tag.
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<java.lang.String>> getVideosForTag(@org.jetbrains.annotations.NotNull()
    java.lang.String tag) {
        return null;
    }
    
    public final void onQueryChange(@org.jetbrains.annotations.NotNull()
    java.lang.String newQuery) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/example/memoreels/ui/viewmodel/ExploreViewModel$Companion;", "", "()V", "TAG", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}