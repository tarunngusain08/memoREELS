package com.example.memoreels.ui.viewmodel;

import android.content.ContentUris;
import android.content.Context;
import android.provider.MediaStore;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagingData;
import com.example.memoreels.data.datasource.FeedItemFactory;
import com.example.memoreels.data.datasource.PhotoDataSource;
import com.example.memoreels.data.local.MediaLocationDao;
import com.example.memoreels.data.local.VideoTagDao;
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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u009c\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u000f\b\u0007\u0018\u0000 Q2\u00020\u0001:\u0001QBY\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\b\b\u0001\u0010\u0014\u001a\u00020\u0015\u00a2\u0006\u0002\u0010\u0016J\u0006\u0010B\u001a\u00020CJ\u0006\u0010D\u001a\u00020CJ\u0014\u0010E\u001a\b\u0012\u0004\u0012\u0002090#H\u0082@\u00a2\u0006\u0002\u0010FJ\b\u0010G\u001a\u00020CH\u0002J\u000e\u0010H\u001a\u00020C2\u0006\u0010I\u001a\u000209J\u000e\u0010J\u001a\u00020C2\u0006\u0010K\u001a\u00020\u001cJ\u000e\u0010L\u001a\u00020C2\u0006\u0010M\u001a\u00020,J\u000e\u0010N\u001a\u00020C2\u0006\u0010K\u001a\u00020\u001cJ\u0006\u0010O\u001a\u00020CJ\u0006\u0010P\u001a\u00020CR\u001a\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u00190\u0018X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0018X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0018X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0018X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u001f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010 0\u0018X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010!\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0018X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\"\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020$0#0\u0018X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010%\u001a\b\u0012\u0004\u0012\u00020\u001c0&\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010(R\u001d\u0010)\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u00190&\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010(R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010+\u001a\b\u0012\u0004\u0012\u00020,0&\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010(R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010.\u001a\b\u0012\u0004\u0012\u00020\u001c0&\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010(R\u0017\u0010/\u001a\b\u0012\u0004\u0012\u00020\u001c0&\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u0010(R\u0017\u00100\u001a\b\u0012\u0004\u0012\u00020\u001c0&\u00a2\u0006\b\n\u0000\u001a\u0004\b1\u0010(R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u00102\u001a\b\u0012\u0004\u0012\u00020\u001c0&\u00a2\u0006\b\n\u0000\u001a\u0004\b3\u0010(R\u0019\u00104\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010 0&\u00a2\u0006\b\n\u0000\u001a\u0004\b5\u0010(R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u00106\u001a\b\u0012\u0004\u0012\u00020\u001c0&\u00a2\u0006\b\n\u0000\u001a\u0004\b7\u0010(R\u001d\u00108\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002090#0&\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010(R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010:\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020$0#0&\u00a2\u0006\b\n\u0000\u001a\u0004\b;\u0010(R\u0011\u0010\u0012\u001a\u00020\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b<\u0010=R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010>\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002090@0?\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010A\u00a8\u0006R"}, d2 = {"Lcom/example/memoreels/ui/viewmodel/VideoFeedViewModel;", "Landroidx/lifecycle/ViewModel;", "getVideos", "Lcom/example/memoreels/domain/usecase/GetVideosUseCase;", "toggleFavorite", "Lcom/example/memoreels/domain/usecase/ToggleFavoriteUseCase;", "repository", "Lcom/example/memoreels/domain/repository/VideoRepository;", "getThisDayVideos", "Lcom/example/memoreels/domain/usecase/GetThisDayVideosUseCase;", "photoDataSource", "Lcom/example/memoreels/data/datasource/PhotoDataSource;", "feedItemFactory", "Lcom/example/memoreels/data/datasource/FeedItemFactory;", "videoTagDao", "Lcom/example/memoreels/data/local/VideoTagDao;", "mediaLocationDao", "Lcom/example/memoreels/data/local/MediaLocationDao;", "userPreferences", "Lcom/example/memoreels/data/preferences/UserPreferences;", "appContext", "Landroid/content/Context;", "(Lcom/example/memoreels/domain/usecase/GetVideosUseCase;Lcom/example/memoreels/domain/usecase/ToggleFavoriteUseCase;Lcom/example/memoreels/domain/repository/VideoRepository;Lcom/example/memoreels/domain/usecase/GetThisDayVideosUseCase;Lcom/example/memoreels/data/datasource/PhotoDataSource;Lcom/example/memoreels/data/datasource/FeedItemFactory;Lcom/example/memoreels/data/local/VideoTagDao;Lcom/example/memoreels/data/local/MediaLocationDao;Lcom/example/memoreels/data/preferences/UserPreferences;Landroid/content/Context;)V", "_favoriteUris", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "", "_isMuted", "", "_isPlaying", "_memoryDismissed", "_memoryOfMoment", "Lcom/example/memoreels/ui/components/MemoryOfMoment;", "_thisDayDismissed", "_unifiedFeed", "", "Lcom/example/memoreels/domain/model/FeedItem;", "autoMute", "Lkotlinx/coroutines/flow/StateFlow;", "getAutoMute", "()Lkotlinx/coroutines/flow/StateFlow;", "favoriteUris", "getFavoriteUris", "feedMode", "Lcom/example/memoreels/data/preferences/FeedMode;", "getFeedMode", "isMuted", "isPlaying", "loopVideos", "getLoopVideos", "memoryDismissed", "getMemoryDismissed", "memoryOfMoment", "getMemoryOfMoment", "thisDayDismissed", "getThisDayDismissed", "thisDayVideos", "Lcom/example/memoreels/domain/model/Video;", "unifiedFeed", "getUnifiedFeed", "getUserPreferences", "()Lcom/example/memoreels/data/preferences/UserPreferences;", "videos", "Lkotlinx/coroutines/flow/Flow;", "Landroidx/paging/PagingData;", "()Lkotlinx/coroutines/flow/Flow;", "dismissMemory", "", "dismissThisDay", "loadAllVideos", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "loadUnifiedFeed", "onToggleFavorite", "video", "setAutoMute", "enabled", "setFeedMode", "mode", "setLoopVideos", "toggleMute", "togglePlayPause", "Companion", "app_debug"})
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
    private final com.example.memoreels.data.datasource.PhotoDataSource photoDataSource = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.memoreels.data.datasource.FeedItemFactory feedItemFactory = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.memoreels.data.local.VideoTagDao videoTagDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.memoreels.data.local.MediaLocationDao mediaLocationDao = null;
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
    
    /**
     * Auto-mute preference from DataStore.
     */
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> autoMute = null;
    
    /**
     * Loop videos preference from DataStore.
     */
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> loopVideos = null;
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
    com.example.memoreels.data.datasource.PhotoDataSource photoDataSource, @org.jetbrains.annotations.NotNull()
    com.example.memoreels.data.datasource.FeedItemFactory feedItemFactory, @org.jetbrains.annotations.NotNull()
    com.example.memoreels.data.local.VideoTagDao videoTagDao, @org.jetbrains.annotations.NotNull()
    com.example.memoreels.data.local.MediaLocationDao mediaLocationDao, @org.jetbrains.annotations.NotNull()
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
    
    /**
     * Auto-mute preference from DataStore.
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getAutoMute() {
        return null;
    }
    
    /**
     * Loop videos preference from DataStore.
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getLoopVideos() {
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
     * Loads photos + videos and builds the unified feed with tag-based grouping.
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
    
    /**
     * Persist auto-mute preference.
     */
    public final void setAutoMute(boolean enabled) {
    }
    
    /**
     * Persist loop videos preference.
     */
    public final void setLoopVideos(boolean enabled) {
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