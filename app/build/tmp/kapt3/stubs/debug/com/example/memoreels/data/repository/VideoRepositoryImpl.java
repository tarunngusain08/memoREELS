package com.example.memoreels.data.repository;

import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import com.example.memoreels.data.datasource.VideoPagingSourceFactory;
import com.example.memoreels.data.local.FavoritesDao;
import com.example.memoreels.data.model.FavoriteEntity;
import com.example.memoreels.data.model.VideoEntity;
import com.example.memoreels.domain.model.Favorite;
import com.example.memoreels.domain.model.Video;
import com.example.memoreels.domain.repository.VideoRepository;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B!\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0001\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0014\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\nH\u0016J\u0018\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0096@\u00a2\u0006\u0002\u0010\u0011J\"\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u00130\n2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00100\u000bH\u0016J\u0016\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u000f\u001a\u00020\u0010H\u0096@\u00a2\u0006\u0002\u0010\u0011J*\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u00130\n2\u0006\u0010\u0018\u001a\u00020\u00102\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00100\u000bH\u0016J\u0016\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u000eH\u0096@\u00a2\u0006\u0002\u0010\u001bR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2 = {"Lcom/example/memoreels/data/repository/VideoRepositoryImpl;", "Lcom/example/memoreels/domain/repository/VideoRepository;", "videoPagingSourceFactory", "Lcom/example/memoreels/data/datasource/VideoPagingSourceFactory;", "favoritesDao", "Lcom/example/memoreels/data/local/FavoritesDao;", "context", "Landroid/content/Context;", "(Lcom/example/memoreels/data/datasource/VideoPagingSourceFactory;Lcom/example/memoreels/data/local/FavoritesDao;Landroid/content/Context;)V", "getFavorites", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/example/memoreels/domain/model/Favorite;", "getVideoByUri", "Lcom/example/memoreels/domain/model/Video;", "uri", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getVideos", "Landroidx/paging/PagingData;", "exclusions", "isFavorite", "", "searchVideos", "query", "toggleFavorite", "video", "(Lcom/example/memoreels/domain/model/Video;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class VideoRepositoryImpl implements com.example.memoreels.domain.repository.VideoRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.example.memoreels.data.datasource.VideoPagingSourceFactory videoPagingSourceFactory = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.memoreels.data.local.FavoritesDao favoritesDao = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    
    @javax.inject.Inject()
    public VideoRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.example.memoreels.data.datasource.VideoPagingSourceFactory videoPagingSourceFactory, @org.jetbrains.annotations.NotNull()
    com.example.memoreels.data.local.FavoritesDao favoritesDao, @dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<androidx.paging.PagingData<com.example.memoreels.domain.model.Video>> getVideos(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> exclusions) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<androidx.paging.PagingData<com.example.memoreels.domain.model.Video>> searchVideos(@org.jetbrains.annotations.NotNull()
    java.lang.String query, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> exclusions) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getVideoByUri(@org.jetbrains.annotations.NotNull()
    java.lang.String uri, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.memoreels.domain.model.Video> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.example.memoreels.domain.model.Favorite>> getFavorites() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object toggleFavorite(@org.jetbrains.annotations.NotNull()
    com.example.memoreels.domain.model.Video video, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object isFavorite(@org.jetbrains.annotations.NotNull()
    java.lang.String uri, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
}