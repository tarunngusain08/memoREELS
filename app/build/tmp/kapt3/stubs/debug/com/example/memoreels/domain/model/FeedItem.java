package com.example.memoreels.domain.model;

/**
 * A single page in the unified feed: either a video or a group of photos.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\u0003\u0004B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0002\u0005\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/example/memoreels/domain/model/FeedItem;", "", "()V", "PhotoGroup", "VideoItem", "Lcom/example/memoreels/domain/model/FeedItem$PhotoGroup;", "Lcom/example/memoreels/domain/model/FeedItem$VideoItem;", "app_debug"})
public abstract class FeedItem {
    
    private FeedItem() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\u000f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\t\u0010\r\u001a\u00020\u0006H\u00c6\u0003J#\u0010\u000e\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006H\u00c6\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u00d6\u0003J\t\u0010\u0013\u001a\u00020\u0014H\u00d6\u0001J\t\u0010\u0015\u001a\u00020\u0016H\u00d6\u0001R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0017"}, d2 = {"Lcom/example/memoreels/domain/model/FeedItem$PhotoGroup;", "Lcom/example/memoreels/domain/model/FeedItem;", "photos", "", "Lcom/example/memoreels/domain/model/Photo;", "displayMode", "Lcom/example/memoreels/domain/model/PhotoDisplayMode;", "(Ljava/util/List;Lcom/example/memoreels/domain/model/PhotoDisplayMode;)V", "getDisplayMode", "()Lcom/example/memoreels/domain/model/PhotoDisplayMode;", "getPhotos", "()Ljava/util/List;", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "app_debug"})
    public static final class PhotoGroup extends com.example.memoreels.domain.model.FeedItem {
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.example.memoreels.domain.model.Photo> photos = null;
        @org.jetbrains.annotations.NotNull()
        private final com.example.memoreels.domain.model.PhotoDisplayMode displayMode = null;
        
        public PhotoGroup(@org.jetbrains.annotations.NotNull()
        java.util.List<com.example.memoreels.domain.model.Photo> photos, @org.jetbrains.annotations.NotNull()
        com.example.memoreels.domain.model.PhotoDisplayMode displayMode) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.example.memoreels.domain.model.Photo> getPhotos() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.example.memoreels.domain.model.PhotoDisplayMode getDisplayMode() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.example.memoreels.domain.model.Photo> component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.example.memoreels.domain.model.PhotoDisplayMode component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.example.memoreels.domain.model.FeedItem.PhotoGroup copy(@org.jetbrains.annotations.NotNull()
        java.util.List<com.example.memoreels.domain.model.Photo> photos, @org.jetbrains.annotations.NotNull()
        com.example.memoreels.domain.model.PhotoDisplayMode displayMode) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0011"}, d2 = {"Lcom/example/memoreels/domain/model/FeedItem$VideoItem;", "Lcom/example/memoreels/domain/model/FeedItem;", "video", "Lcom/example/memoreels/domain/model/Video;", "(Lcom/example/memoreels/domain/model/Video;)V", "getVideo", "()Lcom/example/memoreels/domain/model/Video;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "app_debug"})
    public static final class VideoItem extends com.example.memoreels.domain.model.FeedItem {
        @org.jetbrains.annotations.NotNull()
        private final com.example.memoreels.domain.model.Video video = null;
        
        public VideoItem(@org.jetbrains.annotations.NotNull()
        com.example.memoreels.domain.model.Video video) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.example.memoreels.domain.model.Video getVideo() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.example.memoreels.domain.model.Video component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.example.memoreels.domain.model.FeedItem.VideoItem copy(@org.jetbrains.annotations.NotNull()
        com.example.memoreels.domain.model.Video video) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
}