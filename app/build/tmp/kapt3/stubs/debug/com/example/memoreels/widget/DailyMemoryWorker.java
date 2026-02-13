package com.example.memoreels.widget;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.glance.appwidget.GlanceAppWidgetManager;
import androidx.hilt.work.HiltWorker;
import androidx.work.CoroutineWorker;
import androidx.work.WorkerParameters;
import com.example.memoreels.data.local.VideoTagDao;
import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000 \f2\u00020\u0001:\u0001\fB#\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u000e\u0010\t\u001a\u00020\nH\u0096@\u00a2\u0006\u0002\u0010\u000bR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/example/memoreels/widget/DailyMemoryWorker;", "Landroidx/work/CoroutineWorker;", "context", "Landroid/content/Context;", "params", "Landroidx/work/WorkerParameters;", "videoTagDao", "Lcom/example/memoreels/data/local/VideoTagDao;", "(Landroid/content/Context;Landroidx/work/WorkerParameters;Lcom/example/memoreels/data/local/VideoTagDao;)V", "doWork", "Landroidx/work/ListenableWorker$Result;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "app_debug"})
@androidx.hilt.work.HiltWorker()
public final class DailyMemoryWorker extends androidx.work.CoroutineWorker {
    @org.jetbrains.annotations.NotNull()
    private final com.example.memoreels.data.local.VideoTagDao videoTagDao = null;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String WORK_NAME = "daily_memory_widget";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PREFS_NAME = "widget_prefs";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_URI = "memory_uri";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_IS_VIDEO = "memory_is_video";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_TAG = "memory_tag";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_DATE = "memory_date";
    @org.jetbrains.annotations.NotNull()
    public static final com.example.memoreels.widget.DailyMemoryWorker.Companion Companion = null;
    
    @dagger.assisted.AssistedInject()
    public DailyMemoryWorker(@dagger.assisted.Assisted()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context, @dagger.assisted.Assisted()
    @org.jetbrains.annotations.NotNull()
    androidx.work.WorkerParameters params, @org.jetbrains.annotations.NotNull()
    com.example.memoreels.data.local.VideoTagDao videoTagDao) {
        super(null, null);
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object doWork(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super androidx.work.ListenableWorker.Result> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/example/memoreels/widget/DailyMemoryWorker$Companion;", "", "()V", "KEY_DATE", "", "KEY_IS_VIDEO", "KEY_TAG", "KEY_URI", "PREFS_NAME", "WORK_NAME", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}