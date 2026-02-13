package com.example.memoreels.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.memoreels.data.model.VideoTagEntity;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0006\bg\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0005J\u0014\u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00030\u0007H\'J\u0018\u0010\t\u001a\u0004\u0018\u00010\b2\u0006\u0010\n\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\u000bJ\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\b0\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0005J\u0014\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u00030\u0007H\'J\u001c\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0010\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\u000bJ\u001c\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00030\u00072\u0006\u0010\n\u001a\u00020\bH\'J\u001c\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0015J\u001c\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00030\u00072\u0006\u0010\u0017\u001a\u00020\bH\'J\u001c\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\b0\u00032\u0006\u0010\u0017\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\u000b\u00a8\u0006\u0019"}, d2 = {"Lcom/example/memoreels/data/local/VideoTagDao;", "", "getAllTags", "", "Lcom/example/memoreels/data/model/VideoTagEntity;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getDistinctTags", "Lkotlinx/coroutines/flow/Flow;", "", "getFirstVideoForTag", "tag", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getProcessedUris", "getTagCounts", "Lcom/example/memoreels/data/local/TagCount;", "getTagsForVideo", "uri", "getVideosForTag", "insertTags", "", "tags", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchByTag", "query", "searchByTagDirect", "app_debug"})
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
     * All tags with their associated URIs for tag-based grouping.
     */
    @androidx.room.Query(value = "SELECT * FROM video_tags ORDER BY confidence DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllTags(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.memoreels.data.model.VideoTagEntity>> $completion);
    
    /**
     * Synchronous tag search for mood filtering.
     */
    @androidx.room.Query(value = "SELECT DISTINCT videoUri FROM video_tags WHERE LOWER(tag) LIKE :query")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object searchByTagDirect(@org.jetbrains.annotations.NotNull()
    java.lang.String query, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.String>> $completion);
}