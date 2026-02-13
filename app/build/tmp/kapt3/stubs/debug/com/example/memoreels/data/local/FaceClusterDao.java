package com.example.memoreels.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.memoreels.data.model.FaceClusterEntity;
import com.example.memoreels.data.model.FaceMediaEntity;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0014\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\bH\'J\u001c\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\t0\b2\u0006\u0010\u0004\u001a\u00020\u0005H\'J\u0016\u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u000fJ\u001c\u0010\u0010\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\tH\u00a7@\u00a2\u0006\u0002\u0010\u0014J\u001e\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\fH\u00a7@\u00a2\u0006\u0002\u0010\u0018J\u0016\u0010\u0019\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u000f\u00a8\u0006\u001a"}, d2 = {"Lcom/example/memoreels/data/local/FaceClusterDao;", "", "countMediaForCluster", "", "clusterId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllClusters", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/example/memoreels/data/model/FaceClusterEntity;", "getMediaForCluster", "", "insertCluster", "cluster", "(Lcom/example/memoreels/data/model/FaceClusterEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertFaceMedia", "", "entries", "Lcom/example/memoreels/data/model/FaceMediaEntity;", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "renameCluster", "id", "name", "(JLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateCluster", "app_debug"})
@androidx.room.Dao()
public abstract interface FaceClusterDao {
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertCluster(@org.jetbrains.annotations.NotNull()
    com.example.memoreels.data.model.FaceClusterEntity cluster, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateCluster(@org.jetbrains.annotations.NotNull()
    com.example.memoreels.data.model.FaceClusterEntity cluster, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertFaceMedia(@org.jetbrains.annotations.NotNull()
    java.util.List<com.example.memoreels.data.model.FaceMediaEntity> entries, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM face_clusters ORDER BY mediaCount DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.memoreels.data.model.FaceClusterEntity>> getAllClusters();
    
    @androidx.room.Query(value = "SELECT mediaUri FROM face_media WHERE clusterId = :clusterId")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<java.lang.String>> getMediaForCluster(long clusterId);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM face_media WHERE clusterId = :clusterId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object countMediaForCluster(long clusterId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "UPDATE face_clusters SET name = :name WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object renameCluster(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}