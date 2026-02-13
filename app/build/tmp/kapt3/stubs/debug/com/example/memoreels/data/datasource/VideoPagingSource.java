package com.example.memoreels.data.datasource;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import androidx.paging.PagingSource;
import androidx.paging.PagingState;
import com.example.memoreels.data.model.VideoEntity;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u0000 +2\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0001+B/\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\b\b\u0002\u0010\t\u001a\u00020\b\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\fJ\b\u0010\u000f\u001a\u00020\bH\u0002J\u0013\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\b0\u0011H\u0002\u00a2\u0006\u0002\u0010\u0012J\u0012\u0010\u0013\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J#\u0010\u0016\u001a\u0004\u0018\u00010\u00022\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0018H\u0016\u00a2\u0006\u0002\u0010\u0019J(\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u001b2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00020\u001dH\u0096@\u00a2\u0006\u0002\u0010\u001eJ1\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00072\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\b2\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\b0\u0011H\u0002\u00a2\u0006\u0002\u0010$JA\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00030\u00072\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\b2\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\b0\u00112\u0006\u0010&\u001a\u00020\u00022\u0006\u0010\'\u001a\u00020\u0002H\u0002\u00a2\u0006\u0002\u0010(J,\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00030\u00072\f\u0010*\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00072\u0006\u0010&\u001a\u00020\u00022\u0006\u0010\'\u001a\u00020\u0002H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006,"}, d2 = {"Lcom/example/memoreels/data/datasource/VideoPagingSource;", "Landroidx/paging/PagingSource;", "", "Lcom/example/memoreels/data/model/VideoEntity;", "context", "Landroid/content/Context;", "exclusions", "", "", "query", "shuffle", "", "(Landroid/content/Context;Ljava/util/List;Ljava/lang/String;Z)V", "shuffledIds", "", "buildSelection", "buildSelectionArgs", "", "()[Ljava/lang/String;", "cursorToVideoEntity", "cursor", "Landroid/database/Cursor;", "getRefreshKey", "state", "Landroidx/paging/PagingState;", "(Landroidx/paging/PagingState;)Ljava/lang/Integer;", "load", "Landroidx/paging/PagingSource$LoadResult;", "params", "Landroidx/paging/PagingSource$LoadParams;", "(Landroidx/paging/PagingSource$LoadParams;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "loadAndShuffleIds", "contentUri", "Landroid/net/Uri;", "selection", "selectionArgs", "(Landroid/net/Uri;Ljava/lang/String;[Ljava/lang/String;)Ljava/util/List;", "loadOrderedPage", "page", "pageSize", "(Landroid/net/Uri;Ljava/lang/String;[Ljava/lang/String;II)Ljava/util/List;", "loadShuffledPage", "ids", "Companion", "app_debug"})
public final class VideoPagingSource extends androidx.paging.PagingSource<java.lang.Integer, com.example.memoreels.data.model.VideoEntity> {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> exclusions = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String query = null;
    private final boolean shuffle = false;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String[] PROJECTION = {"_id", "_data", "_display_name", "date_added", "duration", "mime_type"};
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<java.lang.String> DEFAULT_EXCLUDED_FOLDERS = null;
    @org.jetbrains.annotations.Nullable()
    private java.util.List<java.lang.Long> shuffledIds;
    @org.jetbrains.annotations.NotNull()
    public static final com.example.memoreels.data.datasource.VideoPagingSource.Companion Companion = null;
    
    public VideoPagingSource(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> exclusions, @org.jetbrains.annotations.NotNull()
    java.lang.String query, boolean shuffle) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object load(@org.jetbrains.annotations.NotNull()
    androidx.paging.PagingSource.LoadParams<java.lang.Integer> params, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super androidx.paging.PagingSource.LoadResult<java.lang.Integer, com.example.memoreels.data.model.VideoEntity>> $completion) {
        return null;
    }
    
    private final java.util.List<java.lang.Long> loadAndShuffleIds(android.net.Uri contentUri, java.lang.String selection, java.lang.String[] selectionArgs) {
        return null;
    }
    
    private final java.util.List<com.example.memoreels.data.model.VideoEntity> loadShuffledPage(java.util.List<java.lang.Long> ids, int page, int pageSize) {
        return null;
    }
    
    private final java.util.List<com.example.memoreels.data.model.VideoEntity> loadOrderedPage(android.net.Uri contentUri, java.lang.String selection, java.lang.String[] selectionArgs, int page, int pageSize) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Integer getRefreshKey(@org.jetbrains.annotations.NotNull()
    androidx.paging.PagingState<java.lang.Integer, com.example.memoreels.data.model.VideoEntity> state) {
        return null;
    }
    
    private final java.lang.String buildSelection() {
        return null;
    }
    
    private final java.lang.String[] buildSelectionArgs() {
        return null;
    }
    
    private final com.example.memoreels.data.model.VideoEntity cursorToVideoEntity(android.database.Cursor cursor) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\b\u00a8\u0006\t"}, d2 = {"Lcom/example/memoreels/data/datasource/VideoPagingSource$Companion;", "", "()V", "DEFAULT_EXCLUDED_FOLDERS", "", "", "PROJECTION", "", "[Ljava/lang/String;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}