package com.example.memoreels.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.memoreels.data.model.FavoriteEntity;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003H\'J\u0018\u0010\u0006\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0007\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\tJ\u0016\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\rJ\u0016\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\tJ\u0016\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\t\u00a8\u0006\u0011"}, d2 = {"Lcom/example/memoreels/data/local/FavoritesDao;", "", "getAllFavorites", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/example/memoreels/data/model/FavoriteEntity;", "getFavoriteByUri", "uri", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertFavorite", "", "entity", "(Lcom/example/memoreels/data/model/FavoriteEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "isFavorite", "", "removeFavorite", "app_debug"})
@androidx.room.Dao()
public abstract interface FavoritesDao {
    
    @androidx.room.Query(value = "SELECT * FROM favorites ORDER BY createdAt DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.memoreels.data.model.FavoriteEntity>> getAllFavorites();
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertFavorite(@org.jetbrains.annotations.NotNull()
    com.example.memoreels.data.model.FavoriteEntity entity, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM favorites WHERE videoUri = :uri")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object removeFavorite(@org.jetbrains.annotations.NotNull()
    java.lang.String uri, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT EXISTS(SELECT 1 FROM favorites WHERE videoUri = :uri)")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object isFavorite(@org.jetbrains.annotations.NotNull()
    java.lang.String uri, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM favorites WHERE videoUri = :uri LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getFavoriteByUri(@org.jetbrains.annotations.NotNull()
    java.lang.String uri, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.memoreels.data.model.FavoriteEntity> $completion);
}