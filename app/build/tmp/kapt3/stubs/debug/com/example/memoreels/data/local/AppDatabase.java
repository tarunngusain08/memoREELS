package com.example.memoreels.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.memoreels.data.model.FaceClusterEntity;
import com.example.memoreels.data.model.FaceMediaEntity;
import com.example.memoreels.data.model.FavoriteEntity;
import com.example.memoreels.data.model.JournalEntryEntity;
import com.example.memoreels.data.model.MediaHashEntity;
import com.example.memoreels.data.model.MediaLocationEntity;
import com.example.memoreels.data.model.TimeCapsuleEntity;
import com.example.memoreels.data.model.VideoTagEntity;
import com.example.memoreels.data.model.VoiceNoteEntity;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&J\b\u0010\t\u001a\u00020\nH&J\b\u0010\u000b\u001a\u00020\fH&J\b\u0010\r\u001a\u00020\u000eH&J\b\u0010\u000f\u001a\u00020\u0010H&J\b\u0010\u0011\u001a\u00020\u0012H&\u00a8\u0006\u0013"}, d2 = {"Lcom/example/memoreels/data/local/AppDatabase;", "Landroidx/room/RoomDatabase;", "()V", "faceClusterDao", "Lcom/example/memoreels/data/local/FaceClusterDao;", "favoritesDao", "Lcom/example/memoreels/data/local/FavoritesDao;", "journalDao", "Lcom/example/memoreels/data/local/JournalDao;", "mediaHashDao", "Lcom/example/memoreels/data/local/MediaHashDao;", "mediaLocationDao", "Lcom/example/memoreels/data/local/MediaLocationDao;", "timeCapsuleDao", "Lcom/example/memoreels/data/local/TimeCapsuleDao;", "videoTagDao", "Lcom/example/memoreels/data/local/VideoTagDao;", "voiceNoteDao", "Lcom/example/memoreels/data/local/VoiceNoteDao;", "app_debug"})
@androidx.room.Database(entities = {com.example.memoreels.data.model.FavoriteEntity.class, com.example.memoreels.data.model.VideoTagEntity.class, com.example.memoreels.data.model.TimeCapsuleEntity.class, com.example.memoreels.data.model.VoiceNoteEntity.class, com.example.memoreels.data.model.JournalEntryEntity.class, com.example.memoreels.data.model.MediaLocationEntity.class, com.example.memoreels.data.model.FaceClusterEntity.class, com.example.memoreels.data.model.FaceMediaEntity.class, com.example.memoreels.data.model.MediaHashEntity.class}, version = 4, exportSchema = false)
public abstract class AppDatabase extends androidx.room.RoomDatabase {
    
    public AppDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.memoreels.data.local.FavoritesDao favoritesDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.memoreels.data.local.VideoTagDao videoTagDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.memoreels.data.local.TimeCapsuleDao timeCapsuleDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.memoreels.data.local.VoiceNoteDao voiceNoteDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.memoreels.data.local.JournalDao journalDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.memoreels.data.local.MediaLocationDao mediaLocationDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.memoreels.data.local.FaceClusterDao faceClusterDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.memoreels.data.local.MediaHashDao mediaHashDao();
}