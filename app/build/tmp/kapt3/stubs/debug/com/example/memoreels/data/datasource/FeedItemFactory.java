package com.example.memoreels.data.datasource;

import com.example.memoreels.domain.model.FeedItem;
import com.example.memoreels.domain.model.Photo;
import com.example.memoreels.domain.model.PhotoDisplayMode;
import com.example.memoreels.domain.model.Video;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Builds a unified feed by interleaving videos and photo groups.
 *
 * The algorithm:
 * 1. Shuffles both lists independently.
 * 2. Picks from each list alternately, with a bias toward videos (~60-70%).
 * 3. Groups consecutive photo picks (1-4) into a PhotoGroup with a random display mode.
 * 4. Assigns display modes with weighted random distribution.
 */
@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004J(\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u00042\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002\u00a8\u0006\u0010"}, d2 = {"Lcom/example/memoreels/data/datasource/FeedItemFactory;", "", "()V", "buildPhotoFeed", "", "Lcom/example/memoreels/domain/model/FeedItem$PhotoGroup;", "photos", "Lcom/example/memoreels/domain/model/Photo;", "buildUnifiedFeed", "Lcom/example/memoreels/domain/model/FeedItem;", "videos", "Lcom/example/memoreels/domain/model/Video;", "randomDisplayMode", "Lcom/example/memoreels/domain/model/PhotoDisplayMode;", "photoCount", "", "app_debug"})
public final class FeedItemFactory {
    
    @javax.inject.Inject()
    public FeedItemFactory() {
        super();
    }
    
    /**
     * Weighted display mode distribution.
     */
    private final com.example.memoreels.domain.model.PhotoDisplayMode randomDisplayMode(int photoCount) {
        return null;
    }
    
    /**
     * Creates a unified feed from videos and photos.
     * Videos and photos are shuffled, then interleaved with groups of 1-4 photos
     * scattered between video items.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.example.memoreels.domain.model.FeedItem> buildUnifiedFeed(@org.jetbrains.annotations.NotNull()
    java.util.List<com.example.memoreels.domain.model.Video> videos, @org.jetbrains.annotations.NotNull()
    java.util.List<com.example.memoreels.domain.model.Photo> photos) {
        return null;
    }
    
    /**
     * Creates a photo-only feed (for separate mode).
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.example.memoreels.domain.model.FeedItem.PhotoGroup> buildPhotoFeed(@org.jetbrains.annotations.NotNull()
    java.util.List<com.example.memoreels.domain.model.Photo> photos) {
        return null;
    }
}