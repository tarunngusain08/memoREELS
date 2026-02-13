package com.example.memoreels.ui.screen;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.lazy.grid.GridCells;
import androidx.compose.material.icons.Icons;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.style.TextAlign;
import com.example.memoreels.domain.model.Favorite;
import com.example.memoreels.domain.model.Video;
import com.example.memoreels.ui.viewmodel.FavoritesViewModel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000(\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a(\u0010\u0006\u001a\u00020\u00012\b\b\u0002\u0010\u0007\u001a\u00020\b2\u0014\b\u0002\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00010\nH\u0007\u001a\f\u0010\f\u001a\u00020\u000b*\u00020\u0003H\u0002\u00a8\u0006\r"}, d2 = {"FavoriteMediaItem", "", "favorite", "Lcom/example/memoreels/domain/model/Favorite;", "onClick", "Lkotlin/Function0;", "FavoritesScreen", "viewModel", "Lcom/example/memoreels/ui/viewmodel/FavoritesViewModel;", "onVideoClick", "Lkotlin/Function1;", "Lcom/example/memoreels/domain/model/Video;", "toVideo", "app_debug"})
public final class FavoritesScreenKt {
    
    @androidx.compose.runtime.Composable()
    public static final void FavoritesScreen(@org.jetbrains.annotations.NotNull()
    com.example.memoreels.ui.viewmodel.FavoritesViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.example.memoreels.domain.model.Video, kotlin.Unit> onVideoClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void FavoriteMediaItem(com.example.memoreels.domain.model.Favorite favorite, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    private static final com.example.memoreels.domain.model.Video toVideo(com.example.memoreels.domain.model.Favorite $this$toVideo) {
        return null;
    }
}