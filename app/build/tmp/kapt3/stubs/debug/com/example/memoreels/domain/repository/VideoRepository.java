package com.example.memoreels.domain.repository;

import androidx.paging.PagingData;
import com.example.memoreels.domain.model.Favorite;
import com.example.memoreels.domain.model.Video;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\bf\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003H&J\u0018\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\tH\u00a6@\u00a2\u0006\u0002\u0010\nJ$\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\f0\u00032\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\t0\u0004H&J\u0016\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\b\u001a\u00020\tH\u00a6@\u00a2\u0006\u0002\u0010\nJ,\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\f0\u00032\u0006\u0010\u0011\u001a\u00020\t2\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\t0\u0004H&J\u0016\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u0007H\u00a6@\u00a2\u0006\u0002\u0010\u0014\u00a8\u0006\u0015"}, d2 = {"Lcom/example/memoreels/domain/repository/VideoRepository;", "", "getFavorites", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/example/memoreels/domain/model/Favorite;", "getVideoByUri", "Lcom/example/memoreels/domain/model/Video;", "uri", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getVideos", "Landroidx/paging/PagingData;", "exclusions", "isFavorite", "", "searchVideos", "query", "toggleFavorite", "video", "(Lcom/example/memoreels/domain/model/Video;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface VideoRepository {
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<androidx.paging.PagingData<com.example.memoreels.domain.model.Video>> getVideos(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> exclusions);
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<androidx.paging.PagingData<com.example.memoreels.domain.model.Video>> searchVideos(@org.jetbrains.annotations.NotNull()
    java.lang.String query, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> exclusions);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getVideoByUri(@org.jetbrains.annotations.NotNull()
    java.lang.String uri, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.memoreels.domain.model.Video> $completion);
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.memoreels.domain.model.Favorite>> getFavorites();
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object toggleFavorite(@org.jetbrains.annotations.NotNull()
    com.example.memoreels.domain.model.Video video, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object isFavorite(@org.jetbrains.annotations.NotNull()
    java.lang.String uri, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}