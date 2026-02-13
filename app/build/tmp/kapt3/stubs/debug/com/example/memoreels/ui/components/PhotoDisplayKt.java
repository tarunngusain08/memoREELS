package com.example.memoreels.ui.components;

import android.net.Uri;
import androidx.compose.animation.core.RepeatMode;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.layout.ContentScale;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontWeight;
import coil.request.ImageRequest;
import com.example.memoreels.domain.model.FeedItem;
import com.example.memoreels.domain.model.Photo;
import com.example.memoreels.domain.model.PhotoDisplayMode;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000,\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0003\u00f8\u0001\u0000\u00a2\u0006\u0004\b\b\u0010\t\u001a\u0016\u0010\n\u001a\u00020\u00012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\fH\u0003\u001a\u0010\u0010\r\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0003\u001a\u001a\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u0007\u001a\u0010\u0010\u0011\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0003\u001a\u0016\u0010\u0012\u001a\u00020\u00012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\fH\u0007\u001a\u0010\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0003\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u0014"}, d2 = {"CollageImage", "", "photo", "Lcom/example/memoreels/domain/model/Photo;", "modifier", "Landroidx/compose/ui/Modifier;", "cornerRadius", "Landroidx/compose/ui/unit/Dp;", "CollageImage-wH6b6FI", "(Lcom/example/memoreels/domain/model/Photo;Landroidx/compose/ui/Modifier;F)V", "CollageView", "photos", "", "MotionPhotoView", "PhotoDisplay", "group", "Lcom/example/memoreels/domain/model/FeedItem$PhotoGroup;", "SinglePhotoView", "StackedPhotosView", "StylizedPhotoView", "app_debug"})
public final class PhotoDisplayKt {
    
    /**
     * Dispatcher composable that renders a PhotoGroup in its assigned display mode.
     */
    @androidx.compose.runtime.Composable()
    public static final void PhotoDisplay(@org.jetbrains.annotations.NotNull()
    com.example.memoreels.domain.model.FeedItem.PhotoGroup group, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SinglePhotoView(com.example.memoreels.domain.model.Photo photo) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void StackedPhotosView(@org.jetbrains.annotations.NotNull()
    java.util.List<com.example.memoreels.domain.model.Photo> photos) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void CollageView(java.util.List<com.example.memoreels.domain.model.Photo> photos) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void StylizedPhotoView(com.example.memoreels.domain.model.Photo photo) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void MotionPhotoView(com.example.memoreels.domain.model.Photo photo) {
    }
}