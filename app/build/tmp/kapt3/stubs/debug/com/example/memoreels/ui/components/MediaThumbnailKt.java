package com.example.memoreels.ui.components;

import android.net.Uri;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.ContentScale;
import androidx.compose.ui.unit.Dp;
import coil.decode.VideoFrameDecoder;
import coil.request.ImageRequest;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a&\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003H\u0007\u00a8\u0006\u0007"}, d2 = {"MediaThumbnail", "", "contentUri", "", "modifier", "Landroidx/compose/ui/Modifier;", "contentDescription", "app_debug"})
public final class MediaThumbnailKt {
    
    /**
     * Unified thumbnail that automatically handles both video and photo URIs.
     * - Videos: extracts first frame via Coil's VideoFrameDecoder
     * - Photos: loads the image directly via Coil's standard decoder
     */
    @androidx.compose.runtime.Composable()
    public static final void MediaThumbnail(@org.jetbrains.annotations.NotNull()
    java.lang.String contentUri, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, @org.jetbrains.annotations.Nullable()
    java.lang.String contentDescription) {
    }
}