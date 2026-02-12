package com.example.memoreels.di;

import android.content.Context;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.example.memoreels.data.datasource.VideoPagingSourceFactory;
import com.example.memoreels.data.local.AppDatabase;
import com.example.memoreels.data.local.FavoritesDao;
import com.example.memoreels.data.local.VideoTagDao;
import com.example.memoreels.data.repository.VideoRepositoryImpl;
import com.example.memoreels.domain.repository.VideoRepository;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u0007\u001a\u00020\bH\u0007J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0006H\u0007J\u0012\u0010\f\u001a\u00020\r2\b\b\u0001\u0010\u0007\u001a\u00020\bH\u0007J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0007J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u000b\u001a\u00020\u0006H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/example/memoreels/di/AppModule;", "", "()V", "MIGRATION_1_2", "Landroidx/room/migration/Migration;", "provideAppDatabase", "Lcom/example/memoreels/data/local/AppDatabase;", "context", "Landroid/content/Context;", "provideFavoritesDao", "Lcom/example/memoreels/data/local/FavoritesDao;", "database", "provideVideoPagingSourceFactory", "Lcom/example/memoreels/data/datasource/VideoPagingSourceFactory;", "provideVideoRepository", "Lcom/example/memoreels/domain/repository/VideoRepository;", "impl", "Lcom/example/memoreels/data/repository/VideoRepositoryImpl;", "provideVideoTagDao", "Lcom/example/memoreels/data/local/VideoTagDao;", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public final class AppModule {
    
    /**
     * Adds the video_tags table without touching favorites.
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.room.migration.Migration MIGRATION_1_2 = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.example.memoreels.di.AppModule INSTANCE = null;
    
    private AppModule() {
        super();
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.example.memoreels.data.local.AppDatabase provideAppDatabase(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.example.memoreels.data.local.FavoritesDao provideFavoritesDao(@org.jetbrains.annotations.NotNull()
    com.example.memoreels.data.local.AppDatabase database) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.example.memoreels.data.local.VideoTagDao provideVideoTagDao(@org.jetbrains.annotations.NotNull()
    com.example.memoreels.data.local.AppDatabase database) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.example.memoreels.domain.repository.VideoRepository provideVideoRepository(@org.jetbrains.annotations.NotNull()
    com.example.memoreels.data.repository.VideoRepositoryImpl impl) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.example.memoreels.data.datasource.VideoPagingSourceFactory provideVideoPagingSourceFactory(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
}