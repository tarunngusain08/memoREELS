package com.example.memoreels.ui.components;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.ExperimentalMaterial3Api;
import androidx.compose.material3.SwitchDefaults;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import com.example.memoreels.data.preferences.FeedMode;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\u001a\u0094\u0001\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\u0014\b\u0002\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u000b\u001a\u00020\t2\u0014\b\u0002\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00010\u00052\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\u000e2\u0014\b\u0002\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a&\u0010\u0011\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u00102\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00010\u000eH\u0003\u001a4\u0010\u0015\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\t2\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u00a8\u0006\u0018"}, d2 = {"SettingsButton", "", "currentMode", "Lcom/example/memoreels/data/preferences/FeedMode;", "onModeChanged", "Lkotlin/Function1;", "modifier", "Landroidx/compose/ui/Modifier;", "autoMute", "", "onAutoMuteChanged", "loopVideos", "onLoopVideosChanged", "onRescanMedia", "Lkotlin/Function0;", "onNavigate", "", "SettingsNavButton", "title", "description", "onClick", "SettingsToggleRow", "checked", "onCheckedChange", "app_debug"})
public final class SettingsButtonKt {
    
    /**
     * Small semi-transparent settings gear icon that opens a bottom sheet
     * with feed mode, auto-mute, loop, and rescan options.
     */
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void SettingsButton(@org.jetbrains.annotations.NotNull()
    com.example.memoreels.data.preferences.FeedMode currentMode, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.example.memoreels.data.preferences.FeedMode, kotlin.Unit> onModeChanged, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, boolean autoMute, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onAutoMuteChanged, boolean loopVideos, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onLoopVideosChanged, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onRescanMedia, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavigate) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SettingsNavButton(java.lang.String title, java.lang.String description, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SettingsToggleRow(java.lang.String title, java.lang.String description, boolean checked, kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onCheckedChange) {
    }
}