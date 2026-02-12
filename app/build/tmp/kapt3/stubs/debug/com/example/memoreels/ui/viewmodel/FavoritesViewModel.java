package com.example.memoreels.ui.viewmodel;

import androidx.lifecycle.ViewModel;
import com.example.memoreels.domain.model.Favorite;
import com.example.memoreels.domain.repository.VideoRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u001d\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/example/memoreels/ui/viewmodel/FavoritesViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/example/memoreels/domain/repository/VideoRepository;", "(Lcom/example/memoreels/domain/repository/VideoRepository;)V", "favorites", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/example/memoreels/domain/model/Favorite;", "getFavorites", "()Lkotlinx/coroutines/flow/StateFlow;", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class FavoritesViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.memoreels.domain.repository.VideoRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.memoreels.domain.model.Favorite>> favorites = null;
    
    @javax.inject.Inject()
    public FavoritesViewModel(@org.jetbrains.annotations.NotNull()
    com.example.memoreels.domain.repository.VideoRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.memoreels.domain.model.Favorite>> getFavorites() {
        return null;
    }
}