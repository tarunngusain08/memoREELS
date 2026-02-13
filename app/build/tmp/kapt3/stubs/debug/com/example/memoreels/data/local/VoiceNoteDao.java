package com.example.memoreels.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.memoreels.data.model.VoiceNoteEntity;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0005\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u000bJ\u0014\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u000e0\rH\'J\u0016\u0010\u000f\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\u0011J\u0018\u0010\u0012\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\r2\u0006\u0010\t\u001a\u00020\nH\'\u00a8\u0006\u0013"}, d2 = {"Lcom/example/memoreels/data/local/VoiceNoteDao;", "", "delete", "", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getForMedia", "Lcom/example/memoreels/data/model/VoiceNoteEntity;", "mediaUri", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMediaUrisWithNotes", "Lkotlinx/coroutines/flow/Flow;", "", "insert", "note", "(Lcom/example/memoreels/data/model/VoiceNoteEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "observeForMedia", "app_debug"})
@androidx.room.Dao()
public abstract interface VoiceNoteDao {
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.example.memoreels.data.model.VoiceNoteEntity note, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM voice_notes WHERE mediaUri = :mediaUri ORDER BY createdAt DESC LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getForMedia(@org.jetbrains.annotations.NotNull()
    java.lang.String mediaUri, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.memoreels.data.model.VoiceNoteEntity> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM voice_notes WHERE mediaUri = :mediaUri ORDER BY createdAt DESC LIMIT 1")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.example.memoreels.data.model.VoiceNoteEntity> observeForMedia(@org.jetbrains.annotations.NotNull()
    java.lang.String mediaUri);
    
    @androidx.room.Query(value = "SELECT DISTINCT mediaUri FROM voice_notes")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<java.lang.String>> getMediaUrisWithNotes();
    
    @androidx.room.Query(value = "DELETE FROM voice_notes WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object delete(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}