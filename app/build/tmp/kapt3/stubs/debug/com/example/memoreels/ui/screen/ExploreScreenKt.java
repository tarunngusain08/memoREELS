package com.example.memoreels.ui.screen;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.lazy.grid.GridCells;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.ButtonDefaults;
import androidx.compose.material3.OutlinedTextFieldDefaults;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextOverflow;
import androidx.paging.LoadState;
import com.example.memoreels.domain.model.Video;
import com.example.memoreels.ui.viewmodel.ExploreViewModel;
import com.example.memoreels.ui.viewmodel.VideoCollection;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u001a0\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0003\u001aT\u0010\t\u001a\u00020\u00012\b\b\u0002\u0010\n\u001a\u00020\u000b2\u0014\b\u0002\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00010\r2\u0014\b\u0002\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\r2\u0014\b\u0002\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\rH\u0007\u001a\u001e\u0010\u0011\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\u000e2\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0003\u001a\u001e\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u00032\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0003\u00a8\u0006\u0015"}, d2 = {"CategoryBubble", "", "label", "", "thumbnailUri", "isSpecial", "", "onClick", "Lkotlin/Function0;", "ExploreScreen", "viewModel", "Lcom/example/memoreels/ui/viewmodel/ExploreViewModel;", "onVideoClick", "Lkotlin/Function1;", "Lcom/example/memoreels/domain/model/Video;", "onVideoUriClick", "onCollectionClick", "SearchVideoItem", "video", "TagSearchResultItem", "mediaUri", "app_debug"})
public final class ExploreScreenKt {
    
    @androidx.compose.runtime.Composable()
    public static final void ExploreScreen(@org.jetbrains.annotations.NotNull()
    com.example.memoreels.ui.viewmodel.ExploreViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.example.memoreels.domain.model.Video, kotlin.Unit> onVideoClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onVideoUriClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onCollectionClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void CategoryBubble(java.lang.String label, java.lang.String thumbnailUri, boolean isSpecial, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void TagSearchResultItem(java.lang.String mediaUri, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SearchVideoItem(com.example.memoreels.domain.model.Video video, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
}