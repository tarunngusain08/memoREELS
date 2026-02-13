package com.example.memoreels.data.datasource;

import android.content.ContentUris;
import android.content.Context;
import android.provider.MediaStore;
import com.example.memoreels.domain.model.Photo;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.Dispatchers;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Loads local images from MediaStore.Images with optional limit
 * to prevent OOM on devices with large galleries.
 */
@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0007\u0018\u0000 \r2\u00020\u0001:\u0001\rB\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J.\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\b\b\u0002\u0010\b\u001a\u00020\t2\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0006H\u0086@\u00a2\u0006\u0002\u0010\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2 = {"Lcom/example/memoreels/data/datasource/PhotoDataSource;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "loadPhotos", "", "Lcom/example/memoreels/domain/model/Photo;", "limit", "", "exclusions", "", "(ILjava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "app_debug"})
public final class PhotoDataSource {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    
    /**
     * Default max photos to load (prevents OOM on large galleries).
     */
    public static final int DEFAULT_LIMIT = 500;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String[] PROJECTION = {"_id", "_data", "_display_name", "date_added", "width", "height", "mime_type"};
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<java.lang.String> DEFAULT_EXCLUDED_FOLDERS = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.example.memoreels.data.datasource.PhotoDataSource.Companion Companion = null;
    
    @javax.inject.Inject()
    public PhotoDataSource(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    /**
     * Fetch photos with optional limit and folder exclusions.
     * @param limit Max number of photos to return. Pass [Int.MAX_VALUE] for all.
     * @param exclusions Folder patterns to exclude. Defaults to common non-personal folders.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object loadPhotos(int limit, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> exclusions, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.memoreels.domain.model.Photo>> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0086T\u00a2\u0006\u0002\n\u0000R\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\tX\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\n\u00a8\u0006\u000b"}, d2 = {"Lcom/example/memoreels/data/datasource/PhotoDataSource$Companion;", "", "()V", "DEFAULT_EXCLUDED_FOLDERS", "", "", "DEFAULT_LIMIT", "", "PROJECTION", "", "[Ljava/lang/String;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}