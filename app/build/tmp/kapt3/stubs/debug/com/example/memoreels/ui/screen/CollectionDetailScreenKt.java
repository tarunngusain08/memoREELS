package com.example.memoreels.ui.screen;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.lazy.grid.GridCells;
import androidx.compose.material.icons.Icons;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import com.example.memoreels.ui.viewmodel.ExploreViewModel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000&\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a@\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\u0014\b\u0002\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u00072\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\nH\u0007\u001a\u001e\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\u00032\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\nH\u0003\u00a8\u0006\u000e"}, d2 = {"CollectionDetailScreen", "", "tag", "", "viewModel", "Lcom/example/memoreels/ui/viewmodel/ExploreViewModel;", "onVideoClick", "Lkotlin/Function1;", "", "onBack", "Lkotlin/Function0;", "CollectionVideoItem", "videoUri", "onClick", "app_debug"})
public final class CollectionDetailScreenKt {
    
    /**
     * Shows all videos tagged with a specific AI label as a grid.
     * Tapping a video opens the collection feed at that video's index.
     */
    @androidx.compose.runtime.Composable()
    public static final void CollectionDetailScreen(@org.jetbrains.annotations.NotNull()
    java.lang.String tag, @org.jetbrains.annotations.NotNull()
    com.example.memoreels.ui.viewmodel.ExploreViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onVideoClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void CollectionVideoItem(java.lang.String videoUri, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
}