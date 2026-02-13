package com.example.memoreels.ui.screen;

import android.content.Intent;
import android.net.Uri;
import androidx.compose.foundation.ExperimentalFoundationApi;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.pager.PagerDefaults;
import androidx.compose.material.icons.Icons;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.style.TextAlign;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.media3.common.AudioAttributes;
import androidx.media3.common.C;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.paging.LoadState;
import com.example.memoreels.data.preferences.FeedMode;
import com.example.memoreels.domain.model.FeedItem;
import com.example.memoreels.ui.components.MemoryOfMoment;
import com.example.memoreels.ui.viewmodel.VideoFeedViewModel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000V\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\t\u001a\u0098\u0001\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u0011\u001a\u00020\t2\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\t2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00010\u00162\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00010\u00182\u0006\u0010\u0019\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\tH\u0003\u001aZ\u0010\u001b\u001a\u00020\u00012\u0006\u0010\u001c\u001a\u00020\u00102\u0006\u0010\u001d\u001a\u00020\t2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010 \u001a\u00020\t2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00010\u00162\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00010\u00162\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00010\u0016H\u0003\u001a(\u0010$\u001a\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u0014\b\u0002\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00010\u0018H\u0007\u001a\u0084\u0001\u0010%\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u0011\u001a\u00020\t2\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\t2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00010\u00162\u0006\u0010\u0019\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\tH\u0003\u001a\u0018\u0010&\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\'\u001a\u00020\rH\u0002\u00a8\u0006("}, d2 = {"UnifiedFeedContent", "", "viewModel", "Lcom/example/memoreels/ui/viewmodel/VideoFeedViewModel;", "context", "Landroid/content/Context;", "lifecycleOwner", "Landroidx/lifecycle/LifecycleOwner;", "isPlaying", "", "isMuted", "favoriteUris", "", "", "thisDayVideos", "", "Lcom/example/memoreels/domain/model/Video;", "thisDayDismissed", "memoryOfMoment", "Lcom/example/memoreels/ui/components/MemoryOfMoment;", "memoryDismissed", "onDismissMemory", "Lkotlin/Function0;", "onPhotoClick", "Lkotlin/Function1;", "autoMute", "loopVideos", "VideoFeedPage", "video", "isCurrent", "exoPlayer", "Landroidx/media3/exoplayer/ExoPlayer;", "isFavorite", "onTogglePlayPause", "onToggleFavorite", "onShare", "VideoFeedScreen", "VideoOnlyFeedContent", "shareVideo", "videoUri", "app_debug"})
public final class VideoFeedScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.foundation.ExperimentalFoundationApi.class})
    @androidx.compose.runtime.Composable()
    public static final void VideoFeedScreen(@org.jetbrains.annotations.NotNull()
    com.example.memoreels.ui.viewmodel.VideoFeedViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onPhotoClick) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.foundation.ExperimentalFoundationApi.class})
    @androidx.compose.runtime.Composable()
    private static final void UnifiedFeedContent(com.example.memoreels.ui.viewmodel.VideoFeedViewModel viewModel, android.content.Context context, androidx.lifecycle.LifecycleOwner lifecycleOwner, boolean isPlaying, boolean isMuted, java.util.Set<java.lang.String> favoriteUris, java.util.List<com.example.memoreels.domain.model.Video> thisDayVideos, boolean thisDayDismissed, com.example.memoreels.ui.components.MemoryOfMoment memoryOfMoment, boolean memoryDismissed, kotlin.jvm.functions.Function0<kotlin.Unit> onDismissMemory, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onPhotoClick, boolean autoMute, boolean loopVideos) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.foundation.ExperimentalFoundationApi.class})
    @androidx.compose.runtime.Composable()
    private static final void VideoOnlyFeedContent(com.example.memoreels.ui.viewmodel.VideoFeedViewModel viewModel, android.content.Context context, androidx.lifecycle.LifecycleOwner lifecycleOwner, boolean isPlaying, boolean isMuted, java.util.Set<java.lang.String> favoriteUris, java.util.List<com.example.memoreels.domain.model.Video> thisDayVideos, boolean thisDayDismissed, com.example.memoreels.ui.components.MemoryOfMoment memoryOfMoment, boolean memoryDismissed, kotlin.jvm.functions.Function0<kotlin.Unit> onDismissMemory, boolean autoMute, boolean loopVideos) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void VideoFeedPage(com.example.memoreels.domain.model.Video video, boolean isCurrent, androidx.media3.exoplayer.ExoPlayer exoPlayer, boolean isPlaying, boolean isFavorite, kotlin.jvm.functions.Function0<kotlin.Unit> onTogglePlayPause, kotlin.jvm.functions.Function0<kotlin.Unit> onToggleFavorite, kotlin.jvm.functions.Function0<kotlin.Unit> onShare) {
    }
    
    /**
     * Share a video via Android share sheet.
     */
    private static final void shareVideo(android.content.Context context, java.lang.String videoUri) {
    }
}