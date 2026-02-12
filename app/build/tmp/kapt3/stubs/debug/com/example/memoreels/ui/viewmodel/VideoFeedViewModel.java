package com.example.memoreels.ui.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.paging.PagingData;
import com.example.memoreels.data.ml.VideoTagger;
import com.example.memoreels.domain.model.Video;
import com.example.memoreels.domain.usecase.GetThisDayVideosUseCase;
import com.example.memoreels.domain.usecase.GetVideosUseCase;
import com.example.memoreels.domain.usecase.ToggleFavoriteUseCase;
import com.example.memoreels.domain.repository.VideoRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B/\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\fJ\u0006\u0010$\u001a\u00020%J\u000e\u0010&\u001a\u00020%2\u0006\u0010'\u001a\u00020\u001fJ\u0006\u0010(\u001a\u00020%J\u0006\u0010)\u001a\u00020%R\u001a\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00120\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00120\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00120\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0018R\u0017\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00120\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0018R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00120\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0018R\u001d\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001f0\u001e0\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0018R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010 \u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001f0\"0!\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010#\u00a8\u0006*"}, d2 = {"Lcom/example/memoreels/ui/viewmodel/VideoFeedViewModel;", "Landroidx/lifecycle/ViewModel;", "getVideos", "Lcom/example/memoreels/domain/usecase/GetVideosUseCase;", "toggleFavorite", "Lcom/example/memoreels/domain/usecase/ToggleFavoriteUseCase;", "repository", "Lcom/example/memoreels/domain/repository/VideoRepository;", "getThisDayVideos", "Lcom/example/memoreels/domain/usecase/GetThisDayVideosUseCase;", "videoTagger", "Lcom/example/memoreels/data/ml/VideoTagger;", "(Lcom/example/memoreels/domain/usecase/GetVideosUseCase;Lcom/example/memoreels/domain/usecase/ToggleFavoriteUseCase;Lcom/example/memoreels/domain/repository/VideoRepository;Lcom/example/memoreels/domain/usecase/GetThisDayVideosUseCase;Lcom/example/memoreels/data/ml/VideoTagger;)V", "_favoriteUris", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "", "_isMuted", "", "_isPlaying", "_thisDayDismissed", "favoriteUris", "Lkotlinx/coroutines/flow/StateFlow;", "getFavoriteUris", "()Lkotlinx/coroutines/flow/StateFlow;", "isMuted", "isPlaying", "thisDayDismissed", "getThisDayDismissed", "thisDayVideos", "", "Lcom/example/memoreels/domain/model/Video;", "videos", "Lkotlinx/coroutines/flow/Flow;", "Landroidx/paging/PagingData;", "()Lkotlinx/coroutines/flow/Flow;", "dismissThisDay", "", "onToggleFavorite", "video", "toggleMute", "togglePlayPause", "app_debug"})
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
    private final kotlinx.coroutines.flow.Flow<androidx.paging.PagingData<com.example.memoreels.domain.model.Video>> videos = null;
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
    
    @javax.inject.Inject()
    public VideoFeedViewModel(@org.jetbrains.annotations.NotNull()
    com.example.memoreels.domain.usecase.GetVideosUseCase getVideos, @org.jetbrains.annotations.NotNull()
    com.example.memoreels.domain.usecase.ToggleFavoriteUseCase toggleFavorite, @org.jetbrains.annotations.NotNull()
    com.example.memoreels.domain.repository.VideoRepository repository, @org.jetbrains.annotations.NotNull()
    com.example.memoreels.domain.usecase.GetThisDayVideosUseCase getThisDayVideos, @org.jetbrains.annotations.NotNull()
    com.example.memoreels.data.ml.VideoTagger videoTagger) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<androidx.paging.PagingData<com.example.memoreels.domain.model.Video>> getVideos() {
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
}