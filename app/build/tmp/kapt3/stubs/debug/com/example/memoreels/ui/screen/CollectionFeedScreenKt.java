package com.example.memoreels.ui.screen;

import android.net.Uri;
import androidx.compose.foundation.ExperimentalFoundationApi;
import androidx.compose.foundation.pager.PagerDefaults;
import androidx.compose.material.icons.Icons;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.media3.common.AudioAttributes;
import androidx.media3.common.C;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;
import com.example.memoreels.ui.viewmodel.ExploreViewModel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000 \n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a4\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\tH\u0007\u00a8\u0006\n"}, d2 = {"CollectionFeedScreen", "", "tag", "", "startIndex", "", "viewModel", "Lcom/example/memoreels/ui/viewmodel/ExploreViewModel;", "onBack", "Lkotlin/Function0;", "app_debug"})
public final class CollectionFeedScreenKt {
    
    /**
     * Full-screen vertical pager feed for videos in a specific collection (tag).
     * Mirrors the main feed pattern: single shared ExoPlayer, swipe to navigate.
     */
    @kotlin.OptIn(markerClass = {androidx.compose.foundation.ExperimentalFoundationApi.class})
    @androidx.compose.runtime.Composable()
    public static final void CollectionFeedScreen(@org.jetbrains.annotations.NotNull()
    java.lang.String tag, int startIndex, @org.jetbrains.annotations.NotNull()
    com.example.memoreels.ui.viewmodel.ExploreViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack) {
    }
}