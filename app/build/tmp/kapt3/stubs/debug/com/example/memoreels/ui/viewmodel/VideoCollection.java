package com.example.memoreels.ui.viewmodel;

import android.util.Log;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagingData;
import com.example.memoreels.data.local.VideoTagDao;
import com.example.memoreels.data.ml.PhotoTagger;
import com.example.memoreels.data.ml.TaggingProgress;
import com.example.memoreels.data.ml.VideoTagger;
import com.example.memoreels.domain.model.Video;
import com.example.memoreels.domain.repository.VideoRepository;
import com.example.memoreels.ui.components.MemoryOfMoment;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.ExperimentalCoroutinesApi;
import kotlinx.coroutines.FlowPreview;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

/**
 * A single AI-generated collection (tag with metadata).
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u000e\u001a\u00020\u0005H\u00c6\u0003J\u000b\u0010\u000f\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J)\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0014\u001a\u00020\u0005H\u00d6\u0001J\t\u0010\u0015\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u0016"}, d2 = {"Lcom/example/memoreels/ui/viewmodel/VideoCollection;", "", "tag", "", "videoCount", "", "thumbnailUri", "(Ljava/lang/String;ILjava/lang/String;)V", "getTag", "()Ljava/lang/String;", "getThumbnailUri", "getVideoCount", "()I", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class VideoCollection {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String tag = null;
    private final int videoCount = 0;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String thumbnailUri = null;
    
    public VideoCollection(@org.jetbrains.annotations.NotNull()
    java.lang.String tag, int videoCount, @org.jetbrains.annotations.Nullable()
    java.lang.String thumbnailUri) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTag() {
        return null;
    }
    
    public final int getVideoCount() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getThumbnailUri() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    public final int component2() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.memoreels.ui.viewmodel.VideoCollection copy(@org.jetbrains.annotations.NotNull()
    java.lang.String tag, int videoCount, @org.jetbrains.annotations.Nullable()
    java.lang.String thumbnailUri) {
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