package com.example.memoreels.ui.screen;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.material3.SwitchDefaults;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import com.example.memoreels.data.preferences.FeedMode;
import com.example.memoreels.ui.viewmodel.VideoFeedViewModel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000.\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\u001a&\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006H\u0003\u001a(\u0010\u0007\u001a\u00020\u00012\b\b\u0002\u0010\b\u001a\u00020\t2\u0014\b\u0002\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u000bH\u0007\u001a4\u0010\f\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000e2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00010\u000bH\u0003\u00a8\u0006\u0010"}, d2 = {"SettingsNavButton", "", "title", "", "description", "onClick", "Lkotlin/Function0;", "SettingsScreen", "viewModel", "Lcom/example/memoreels/ui/viewmodel/VideoFeedViewModel;", "onNavigate", "Lkotlin/Function1;", "SettingsToggleRow", "checked", "", "onCheckedChange", "app_debug"})
public final class SettingsScreenKt {
    
    /**
     * Full-screen Settings tab. Replaces the floating SettingsButton.
     * Contains feed mode, playback preferences, and feature navigation.
     */
    @androidx.compose.runtime.Composable()
    public static final void SettingsScreen(@org.jetbrains.annotations.NotNull()
    com.example.memoreels.ui.viewmodel.VideoFeedViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavigate) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SettingsNavButton(java.lang.String title, java.lang.String description, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SettingsToggleRow(java.lang.String title, java.lang.String description, boolean checked, kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onCheckedChange) {
    }
}