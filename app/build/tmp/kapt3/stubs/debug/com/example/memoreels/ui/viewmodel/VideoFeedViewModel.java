package com.example.memoreels.ui.viewmodel;

import android.content.ContentUris;
import android.content.Context;
import android.provider.MediaStore;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagingData;
import com.example.memoreels.data.datasource.FeedItemFactory;
import com.example.memoreels.data.datasource.PhotoDataSource;
import com.example.memoreels.data.local.VideoTagDao;
import com.example.memoreels.data.ml.PhotoTagger;
import com.example.memoreels.data.ml.VideoTagger;
import com.example.memoreels.data.preferences.FeedMode;
import com.example.memoreels.data.preferences.UserPreferences;
import com.example.memoreels.ui.components.MemoryOfMoment;
import com.example.memoreels.domain.model.FeedItem;
import com.example.memoreels.domain.model.Video;
import com.example.memoreels.domain.repository.VideoRepository;
import com.example.memoreels.domain.usecase.GetThisDayVideosUseCase;
import com.example.memoreels.domain.usecase.GetVideosUseCase;
import com.example.memoreels.domain.usecase.ToggleFavoriteUseCase;
import android.util.Log;
import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u00a2\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\f\b\u0007\u0018\u0000 L2\u00020\u0001:\u0001LBa\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\u0006\u0010\u0014\u001a\u00020\u0015\u0012\b\b\u0001\u0010\u0016\u001a\u00020\u0017\u00a2\u0006\u0002\u0010\u0018J\u0006\u0010@\u001a\u00020AJ\u0006\u0010B\u001a\u00020AJ\u0014\u0010C\u001a\b\u0012\u0004\u0012\u0002070%H\u0082@\u00a2\u0006\u0002\u0010DJ\b\u0010E\u001a\u00020AH\u0002J\u000e\u0010F\u001a\u00020A2\u0006\u0010G\u001a\u000207J\u000e\u0010H\u001a\u00020A2\u0006\u0010I\u001a\u00020,J\u0006\u0010J\u001a\u00020AJ\u0006\u0010K\u001a\u00020AR\u001a\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001c0\u001b0\u001aX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001aX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001aX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010 \u001a\b\u0012\u0004\u0012\u00020\u001e0\u001aX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010!\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\"0\u001aX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010#\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001aX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010$\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020&0%0\u001aX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\'\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001c0\u001b0(\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010*R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010+\u001a\b\u0012\u0004\u0012\u00020,0(\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010*R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010.\u001a\b\u0012\u0004\u0012\u00020\u001e0(\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010*R\u0017\u0010/\u001a\b\u0012\u0004\u0012\u00020\u001e0(\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u0010*R\u0017\u00100\u001a\b\u0012\u0004\u0012\u00020\u001e0(\u00a2\u0006\b\n\u0000\u001a\u0004\b1\u0010*R\u0019\u00102\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\"0(\u00a2\u0006\b\n\u0000\u001a\u0004\b3\u0010*R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u00104\u001a\b\u0012\u0004\u0012\u00020\u001e0(\u00a2\u0006\b\n\u0000\u001a\u0004\b5\u0010*R\u001d\u00106\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002070%0(\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010*R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u00108\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020&0%0(\u00a2\u0006\b\n\u0000\u001a\u0004\b9\u0010*R\u0011\u0010\u0014\u001a\u00020\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b:\u0010;R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010<\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002070>0=\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010?\u00a8\u0006M"}, d2 = {"Lcom/example/memoreels/ui/viewmodel/VideoFeedViewModel;", "Landroidx/lifecycle/ViewModel;", "getVideos", "Lcom/example/memoreels/domain/usecase/GetVideosUseCase;", "toggleFavorite", "Lcom/example/memoreels/domain/usecase/ToggleFavoriteUseCase;", "repository", "Lcom/example/memoreels/domain/repository/VideoRepository;", "getThisDayVideos", "Lcom/example/memoreels/domain/usecase/GetThisDayVideosUseCase;", "videoTagger", "Lcom/example/memoreels/data/ml/VideoTagger;", "photoTagger", "Lcom/example/memoreels/data/ml/PhotoTagger;", "photoDataSource", "Lcom/example/memoreels/data/datasource/PhotoDataSource;", "feedItemFactory", "Lcom/example/memoreels/data/datasource/FeedItemFactory;", "videoTagDao", "Lcom/example/memoreels/data/local/VideoTagDao;", "userPreferences", "Lcom/example/memoreels/data/preferences/UserPreferences;", "appContext", "Landroid/content/Context;", "(Lcom/example/memoreels/domain/usecase/GetVideosUseCase;Lcom/example/memoreels/domain/usecase/ToggleFavoriteUseCase;Lcom/example/memoreels/domain/repository/VideoRepository;Lcom/example/memoreels/domain/usecase/GetThisDayVideosUseCase;Lcom/example/memoreels/data/ml/VideoTagger;Lcom/example/memoreels/data/ml/PhotoTagger;Lcom/example/memoreels/data/datasource/PhotoDataSource;Lcom/example/memoreels/data/datasource/FeedItemFactory;Lcom/example/memoreels/data/local/VideoTagDao;Lcom/example/memoreels/data/preferences/UserPreferences;Landroid/content/Context;)V", "_favoriteUris", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "", "_isMuted", "", "_isPlaying", "_memoryDismissed", "_memoryOfMoment", "Lcom/example/memoreels/ui/components/MemoryOfMoment;", "_thisDayDismissed", "_unifiedFeed", "", "Lcom/example/memoreels/domain/model/FeedItem;", "favoriteUris", "Lkotlinx/coroutines/flow/StateFlow;", "getFavoriteUris", "()Lkotlinx/coroutines/flow/StateFlow;", "feedMode", "Lcom/example/memoreels/data/preferences/FeedMode;", "getFeedMode", "isMuted", "isPlaying", "memoryDismissed", "getMemoryDismissed", "memoryOfMoment", "getMemoryOfMoment", "thisDayDismissed", "getThisDayDismissed", "thisDayVideos", "Lcom/example/memoreels/domain/model/Video;", "unifiedFeed", "getUnifiedFeed", "getUserPreferences", "()Lcom/example/memoreels/data/preferences/UserPreferences;", "videos", "Lkotlinx/coroutines/flow/Flow;", "Landroidx/paging/PagingData;", "()Lkotlinx/coroutines/flow/Flow;", "dismissMemory", "", "dismissThisDay", "loadAllVideos", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "loadUnifiedFeed", "onToggleFavorite", "video", "setFeedMode", "mode", "toggleMute", "togglePlayPause", "Companion", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class VideoFeedViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.memoreels.domain.usecase.GetVideosUseCase getVideos = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.memoreels.domain.usecase.ToggleFavoriteUseCase toggleFavorite = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.memoreels.domain.repository.VideoRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.memoreels.domain.usecase.GetThisDayVideosUseCase getThisDayVideos = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.memoreels.data.ml.VideoTagger videoTagger = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.memoreels.data.ml.PhotoTagger photoTagger = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.memoreels.data.datasource.PhotoDataSource photoDataSource = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.memoreels.data.datasource.FeedItemFactory feedItemFactory = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.memoreels.data.local.VideoTagDao videoTagDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.memoreels.data.preferences.UserPreferences userPreferences = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context appContext = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "VideoFeedViewModel";
    
    /**
     * Video-only paged flow (for SEPARATE mode).
     */
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<androidx.paging.PagingData<com.example.memoreels.domain.model.Video>> videos = null;
    
    /**
     * Unified feed items (videos + photos mixed).
     */
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.example.memoreels.domain.model.FeedItem>> _unifiedFeed = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.memoreels.domain.model.FeedItem>> unifiedFeed = null;
    
    /**
     * Current feed mode.
     */
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.memoreels.data.preferences.FeedMode> feedMode = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isMuted = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isMuted = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isPlaying = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isPlaying = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.Set<java.lang.String>> _favoriteUris = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.Set<java.lang.String>> favoriteUris = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.memoreels.domain.model.Video>> thisDayVideos = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _thisDayDismissed = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> thisDayDismissed = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.memoreels.ui.components.MemoryOfMoment> _memoryOfMoment = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.memoreels.ui.components.MemoryOfMoment> memoryOfMoment = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _memoryDismissed = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> memoryDismissed = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.example.memoreels.ui.viewmodel.VideoFeedViewModel.Companion Companion = null;
    
    @javax.inject.Inject()
    public VideoFeedViewModel(@org.jetbrains.annotations.NotNull()
    com.example.memoreels.domain.usecase.GetVideosUseCase getVideos, @org.jetbrains.annotations.NotNull()
    com.example.memoreels.domain.usecase.ToggleFavoriteUseCase toggleFavorite, @org.jetbrains.annotations.NotNull()
    com.example.memoreels.domain.repository.VideoRepository repository, @org.jetbrains.annotations.NotNull()
    com.example.memoreels.domain.usecase.GetThisDayVideosUseCase getThisDayVideos, @org.jetbrains.annotations.NotNull()
    com.example.memoreels.data.ml.VideoTagger videoTagger, @org.jetbrains.annotations.NotNull()
    com.example.memoreels.data.ml.PhotoTagger photoTagger, @org.jetbrains.annotations.NotNull()
    com.example.memoreels.data.datasource.PhotoDataSource photoDataSource, @org.jetbrains.annotations.NotNull()
    com.example.memoreels.data.datasource.FeedItemFactory feedItemFactory, @org.jetbrains.annotations.NotNull()
    com.example.memoreels.data.local.VideoTagDao videoTagDao, @org.jetbrains.annotations.NotNull()
    com.example.memoreels.data.preferences.UserPreferences userPreferences, @dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context appContext) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.memoreels.data.preferences.UserPreferences getUserPreferences() {
        return null;
    }
    
    /**
     * Video-only paged flow (for SEPARATE mode).
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<androidx.paging.PagingData<com.example.memoreels.domain.model.Video>> getVideos() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.memoreels.domain.model.FeedItem>> getUnifiedFeed() {
        return null;
    }
    
    /**
     * Current feed mode.
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.memoreels.data.preferences.FeedMode> getFeedMode() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isMuted() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isPlaying() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.Set<java.lang.String>> getFavoriteUris() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.memoreels.domain.model.Video>> getThisDayVideos() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getThisDayDismissed() {
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
    
    /**
     * Loads photos + videos and builds the unified feed.
     */
    private final void loadUnifiedFeed() {
    }
    
    /**
     * Direct MediaStore query for all videos (non-paged, for unified feed).
     */
    private final java.lang.Object loadAllVideos(kotlin.coroutines.Continuation<? super java.util.List<com.example.memoreels.domain.model.Video>> $completion) {
        return null;
    }
    
    public final void setFeedMode(@org.jetbrains.annotations.NotNull()
    com.example.memoreels.data.preferences.FeedMode mode) {
    }
    
    public final void toggleMute() {
    }
    
    public final void togglePlayPause() {
    }
    
    public final void onToggleFavorite(@org.jetbrains.annotations.NotNull()
    com.example.memoreels.domain.model.Video video) {
    }
    
    /**
     * Dismiss the "This Day" card for this session.
     */
    public final void dismissThisDay() {
    }
    
    /**
     * Dismiss the "Memory of the Moment" card for this session.
     */
    public final void dismissMemory() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/example/memoreels/ui/viewmodel/VideoFeedViewModel$Companion;", "", "()V", "TAG", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}