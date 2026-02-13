package com.example.memoreels.ui.viewmodel;

import androidx.lifecycle.ViewModel;
import com.example.memoreels.data.datasource.FeedItemFactory;
import com.example.memoreels.data.datasource.PhotoDataSource;
import com.example.memoreels.domain.model.FeedItem;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

/**
 * ViewModel for the photo-only feed tab (separate mode).
 * Loads all photos, groups them, and exposes as a list of PhotoGroups.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u0010\u0012\u001a\u00020\u0013H\u0002R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\t0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000f\u00a8\u0006\u0014"}, d2 = {"Lcom/example/memoreels/ui/viewmodel/PhotoFeedViewModel;", "Landroidx/lifecycle/ViewModel;", "photoDataSource", "Lcom/example/memoreels/data/datasource/PhotoDataSource;", "feedItemFactory", "Lcom/example/memoreels/data/datasource/FeedItemFactory;", "(Lcom/example/memoreels/data/datasource/PhotoDataSource;Lcom/example/memoreels/data/datasource/FeedItemFactory;)V", "_isLoading", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_photoGroups", "", "Lcom/example/memoreels/domain/model/FeedItem$PhotoGroup;", "isLoading", "Lkotlinx/coroutines/flow/StateFlow;", "()Lkotlinx/coroutines/flow/StateFlow;", "photoGroups", "getPhotoGroups", "loadPhotos", "", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class PhotoFeedViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.memoreels.data.datasource.PhotoDataSource photoDataSource = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.memoreels.data.datasource.FeedItemFactory feedItemFactory = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.example.memoreels.domain.model.FeedItem.PhotoGroup>> _photoGroups = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.memoreels.domain.model.FeedItem.PhotoGroup>> photoGroups = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isLoading = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isLoading = null;
    
    @javax.inject.Inject()
    public PhotoFeedViewModel(@org.jetbrains.annotations.NotNull()
    com.example.memoreels.data.datasource.PhotoDataSource photoDataSource, @org.jetbrains.annotations.NotNull()
    com.example.memoreels.data.datasource.FeedItemFactory feedItemFactory) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.memoreels.domain.model.FeedItem.PhotoGroup>> getPhotoGroups() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isLoading() {
        return null;
    }
    
    private final void loadPhotos() {
    }
}