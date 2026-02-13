package com.example.memoreels.ui.components;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.material3.ExperimentalMaterial3Api;
import androidx.compose.material3.SliderDefaults;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.media3.exoplayer.ExoPlayer;
import java.util.Locale;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000&\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0000\u001a\"\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u0007\u001a\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002\u00a8\u0006\f"}, d2 = {"VideoSeekBar", "", "exoPlayer", "Landroidx/media3/exoplayer/ExoPlayer;", "visible", "", "modifier", "Landroidx/compose/ui/Modifier;", "formatDuration", "", "ms", "", "app_debug"})
public final class VideoSeekBarKt {
    
    /**
     * Elegant seek bar that appears when the video is paused.
     * Shows elapsed / total time with a smooth slider.
     */
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void VideoSeekBar(@org.jetbrains.annotations.NotNull()
    androidx.media3.exoplayer.ExoPlayer exoPlayer, boolean visible, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    /**
     * Formats milliseconds as mm:ss.
     */
    private static final java.lang.String formatDuration(long ms) {
        return null;
    }
}