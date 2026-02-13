package com.example.memoreels.widget;

import android.content.Context;
import androidx.compose.runtime.Composable;
import androidx.glance.GlanceId;
import androidx.glance.GlanceModifier;
import androidx.glance.appwidget.GlanceAppWidget;
import androidx.glance.appwidget.GlanceAppWidgetReceiver;
import androidx.glance.layout.Alignment;
import androidx.glance.text.FontWeight;
import androidx.glance.text.TextStyle;
import com.example.memoreels.ui.MainActivity;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u0006H\u0003J\u001e\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0096@\u00a2\u0006\u0002\u0010\u0010\u00a8\u0006\u0011"}, d2 = {"Lcom/example/memoreels/widget/DailyMemoryWidget;", "Landroidx/glance/appwidget/GlanceAppWidget;", "()V", "WidgetContent", "", "hasMemory", "", "tag", "", "date", "isVideo", "provideGlance", "context", "Landroid/content/Context;", "id", "Landroidx/glance/GlanceId;", "(Landroid/content/Context;Landroidx/glance/GlanceId;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class DailyMemoryWidget extends androidx.glance.appwidget.GlanceAppWidget {
    
    public DailyMemoryWidget() {
        super(0);
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object provideGlance(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    androidx.glance.GlanceId id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @androidx.compose.runtime.Composable()
    private final void WidgetContent(boolean hasMemory, java.lang.String tag, java.lang.String date, boolean isVideo) {
    }
}