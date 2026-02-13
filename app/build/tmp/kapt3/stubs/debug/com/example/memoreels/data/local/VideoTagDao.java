package com.example.memoreels.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.memoreels.data.model.VideoTagEntity;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\bg\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003H\'J\u0018\u0010\u0006\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0007\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0014\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u00040\u0003H\'J\u001c\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00042\u0006\u0010\u000f\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u001c\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\u0006\u0010\u0007\u001a\u00020\u0005H\'J\u001c\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0004H\u00a7@\u00a2\u0006\u0002\u0010\u0014J\u001c\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\u0006\u0010\u0016\u001a\u00020\u0005H\'J\u001c\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0016\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\b\u00a8\u0006\u0018"}, d2 = {"Lcom/example/memoreels/data/local/VideoTagDao;", "", "getDistinctTags", "Lkotlinx/coroutines/flow/Flow;", "", "", "getFirstVideoForTag", "tag", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getProcessedUris", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getTagCounts", "Lcom/example/memoreels/data/local/TagCount;", "getTagsForVideo", "Lcom/example/memoreels/data/model/VideoTagEntity;", "uri", "getVideosForTag", "insertTags", "", "tags", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchByTag", "query", "searchByTagDirect", "app_debug"})
@androidx.room.Dao()
public abstract interface VideoTagDao {
    
    /**
     * Batch insert tags for a video.
     */
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertTags(@org.jetbrains.annotations.NotNull()
    java.util.List<com.example.memoreels.data.model.VideoTagEntity> tags, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * All tags for a specific video.
     */
    @androidx.room.Query(value = "SELECT * FROM video_tags WHERE videoUri = :uri")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getTagsForVideo(@org.jetbrains.annotations.NotNull()
    java.lang.String uri, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.memoreels.data.model.VideoTagEntity>> $completion);
    
    /**
     * All distinct tag names across all videos (reactive).
     */
    @androidx.room.Query(value = "SELECT DISTINCT tag FROM video_tags ORDER BY tag ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<java.lang.String>> getDistinctTags();
    
    /**
     * All video URIs that have a specific tag (reactive).
     */
    @androidx.room.Query(value = "SELECT videoUri FROM video_tags WHERE tag = :tag ORDER BY confidence DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<java.lang.String>> getVideosForTag(@org.jetbrains.annotations.NotNull()
    java.lang.String tag);
    
    /**
     * Video URIs whose tags match a LIKE query (reactive).
     */
    @androidx.room.Query(value = "SELECT DISTINCT videoUri FROM video_tags WHERE LOWER(tag) LIKE \'%\' || LOWER(:query) || \'%\'")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<java.lang.String>> searchByTag(@org.jetbrains.annotations.NotNull()
    java.lang.String query);
    
    /**
     * Count of videos for each tag (reactive).
     */
    @androidx.room.Query(value = "SELECT tag, COUNT(DISTINCT videoUri) as cnt FROM video_tags GROUP BY tag ORDER BY cnt DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.memoreels.data.local.TagCount>> getTagCounts();
    
    /**
     * First video URI for a given tag (for thumbnails).
     */
    @androidx.room.Query(value = "SELECT videoUri FROM video_tags WHERE tag = :tag ORDER BY confidence DESC LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getFirstVideoForTag(@org.jetbrains.annotations.NotNull()
    java.lang.String tag, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.String> $completion);
    
    /**
     * All URIs that have already been processed (at least one tag).
     */
    @androidx.room.Query(value = "SELECT DISTINCT videoUri FROM video_tags")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getProcessedUris(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.String>> $completion);
    
    /**
     * Synchronous tag search for mood filtering.
     */
    @androidx.room.Query(value = "SELECT DISTINCT videoUri FROM video_tags WHERE LOWER(tag) LIKE :query")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object searchByTagDirect(@org.jetbrains.annotations.NotNull()
    java.lang.String query, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.String>> $completion);
}