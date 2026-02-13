package com.example.memoreels.di;

import android.content.Context;
import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.PreferenceDataStoreFactory;
import androidx.datastore.preferences.core.Preferences;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.example.memoreels.data.datasource.VideoPagingSourceFactory;
import com.example.memoreels.data.local.AppDatabase;
import com.example.memoreels.data.local.FaceClusterDao;
import com.example.memoreels.data.local.FavoritesDao;
import com.example.memoreels.data.local.JournalDao;
import com.example.memoreels.data.local.MediaHashDao;
import com.example.memoreels.data.local.MediaLocationDao;
import com.example.memoreels.data.local.TimeCapsuleDao;
import com.example.memoreels.data.local.VideoTagDao;
import com.example.memoreels.data.local.VoiceNoteDao;
import com.example.memoreels.data.repository.VideoRepositoryImpl;
import com.example.memoreels.domain.repository.VideoRepository;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0007\u001a\u00020\b2\b\b\u0001\u0010\t\u001a\u00020\nH\u0007J\u0018\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\b\b\u0001\u0010\t\u001a\u00020\nH\u0007J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\bH\u0007J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0010\u001a\u00020\bH\u0007J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0010\u001a\u00020\bH\u0007J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0010\u001a\u00020\bH\u0007J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0010\u001a\u00020\bH\u0007J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0010\u001a\u00020\bH\u0007J\u0012\u0010\u001b\u001a\u00020\u001c2\b\b\u0001\u0010\t\u001a\u00020\nH\u0007J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 H\u0007J\u0010\u0010!\u001a\u00020\"2\u0006\u0010\u0010\u001a\u00020\bH\u0007J\u0010\u0010#\u001a\u00020$2\u0006\u0010\u0010\u001a\u00020\bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006%"}, d2 = {"Lcom/example/memoreels/di/AppModule;", "", "()V", "MIGRATION_1_2", "Landroidx/room/migration/Migration;", "MIGRATION_2_3", "MIGRATION_3_4", "provideAppDatabase", "Lcom/example/memoreels/data/local/AppDatabase;", "context", "Landroid/content/Context;", "provideDataStore", "Landroidx/datastore/core/DataStore;", "Landroidx/datastore/preferences/core/Preferences;", "provideFaceClusterDao", "Lcom/example/memoreels/data/local/FaceClusterDao;", "db", "provideFavoritesDao", "Lcom/example/memoreels/data/local/FavoritesDao;", "provideJournalDao", "Lcom/example/memoreels/data/local/JournalDao;", "provideMediaHashDao", "Lcom/example/memoreels/data/local/MediaHashDao;", "provideMediaLocationDao", "Lcom/example/memoreels/data/local/MediaLocationDao;", "provideTimeCapsuleDao", "Lcom/example/memoreels/data/local/TimeCapsuleDao;", "provideVideoPagingSourceFactory", "Lcom/example/memoreels/data/datasource/VideoPagingSourceFactory;", "provideVideoRepository", "Lcom/example/memoreels/domain/repository/VideoRepository;", "impl", "Lcom/example/memoreels/data/repository/VideoRepositoryImpl;", "provideVideoTagDao", "Lcom/example/memoreels/data/local/VideoTagDao;", "provideVoiceNoteDao", "Lcom/example/memoreels/data/local/VoiceNoteDao;", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public final class AppModule {
    @org.jetbrains.annotations.NotNull()
    private static final androidx.room.migration.Migration MIGRATION_1_2 = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.room.migration.Migration MIGRATION_2_3 = null;
    
    /**
     * Adds tables for time capsules, voice notes, journal, locations, faces, hashes.
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.room.migration.Migration MIGRATION_3_4 = null;
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
    com.example.memoreels.data.local.AppDatabase db) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.example.memoreels.data.local.VideoTagDao provideVideoTagDao(@org.jetbrains.annotations.NotNull()
    com.example.memoreels.data.local.AppDatabase db) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.example.memoreels.data.local.TimeCapsuleDao provideTimeCapsuleDao(@org.jetbrains.annotations.NotNull()
    com.example.memoreels.data.local.AppDatabase db) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.example.memoreels.data.local.VoiceNoteDao provideVoiceNoteDao(@org.jetbrains.annotations.NotNull()
    com.example.memoreels.data.local.AppDatabase db) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.example.memoreels.data.local.JournalDao provideJournalDao(@org.jetbrains.annotations.NotNull()
    com.example.memoreels.data.local.AppDatabase db) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.example.memoreels.data.local.MediaLocationDao provideMediaLocationDao(@org.jetbrains.annotations.NotNull()
    com.example.memoreels.data.local.AppDatabase db) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.example.memoreels.data.local.FaceClusterDao provideFaceClusterDao(@org.jetbrains.annotations.NotNull()
    com.example.memoreels.data.local.AppDatabase db) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.example.memoreels.data.local.MediaHashDao provideMediaHashDao(@org.jetbrains.annotations.NotNull()
    com.example.memoreels.data.local.AppDatabase db) {
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
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences> provideDataStore(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
}