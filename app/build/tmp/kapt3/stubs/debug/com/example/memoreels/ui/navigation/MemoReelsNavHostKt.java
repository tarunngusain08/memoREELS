package com.example.memoreels.ui.navigation;

import androidx.compose.animation.EnterTransition;
import androidx.compose.animation.ExitTransition;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.SnackbarHostState;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.navigation.NavHostController;
import com.example.memoreels.data.preferences.FeedMode;
import com.example.memoreels.data.preferences.UserPreferences;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00006\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\u001a\u001e\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u000e2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0007\u001a\u0014\u0010\u0011\u001a\u00020\f*\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002\u001a\u0014\u0010\u0014\u001a\u00020\f*\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002\u001a\u0014\u0010\u0015\u001a\u00020\f*\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\t\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\n\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"DETAIL_ANIM_DURATION", "", "TAB_ANIM_DURATION", "slideInFromLeft", "Landroidx/compose/animation/EnterTransition;", "slideInFromRight", "slideOutToLeft", "Landroidx/compose/animation/ExitTransition;", "slideOutToRight", "tabEnter", "tabExit", "MemoReelsNavHost", "", "navController", "Landroidx/navigation/NavHostController;", "userPreferences", "Lcom/example/memoreels/data/preferences/UserPreferences;", "navigateToMedia", "uri", "", "navigateToPhotoViewer", "navigateToPlayer", "app_debug"})
public final class MemoReelsNavHostKt {
    private static final int TAB_ANIM_DURATION = 250;
    private static final int DETAIL_ANIM_DURATION = 300;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.animation.EnterTransition tabEnter = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.animation.ExitTransition tabExit = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.animation.EnterTransition slideInFromRight = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.animation.ExitTransition slideOutToRight = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.animation.EnterTransition slideInFromLeft = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.animation.ExitTransition slideOutToLeft = null;
    
    /**
     * Helper to navigate to the video player with a URI string.
     */
    private static final void navigateToPlayer(androidx.navigation.NavHostController $this$navigateToPlayer, java.lang.String uri) {
    }
    
    /**
     * Helper to navigate to the photo viewer with a URI string.
     */
    private static final void navigateToPhotoViewer(androidx.navigation.NavHostController $this$navigateToPhotoViewer, java.lang.String uri) {
    }
    
    /**
     * Smart media navigator: detects whether the URI is a video and routes
     * to the appropriate viewer screen. Defaults to photo viewer for
     * unrecognized URIs (safer than showing a blank video player).
     */
    private static final void navigateToMedia(androidx.navigation.NavHostController $this$navigateToMedia, java.lang.String uri) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void MemoReelsNavHost(@org.jetbrains.annotations.NotNull()
    androidx.navigation.NavHostController navController, @org.jetbrains.annotations.Nullable()
    com.example.memoreels.data.preferences.UserPreferences userPreferences) {
    }
}