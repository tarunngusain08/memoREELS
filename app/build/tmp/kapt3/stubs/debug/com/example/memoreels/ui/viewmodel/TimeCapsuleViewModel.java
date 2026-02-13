package com.example.memoreels.ui.viewmodel;

import android.util.Log;
import androidx.lifecycle.ViewModel;
import com.example.memoreels.data.local.TimeCapsuleDao;
import com.example.memoreels.data.model.TimeCapsuleEntity;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0005\b\u0007\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J$\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00072\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u0011J\u000e\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u0011R\u001d\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/example/memoreels/ui/viewmodel/TimeCapsuleViewModel;", "Landroidx/lifecycle/ViewModel;", "timeCapsuleDao", "Lcom/example/memoreels/data/local/TimeCapsuleDao;", "(Lcom/example/memoreels/data/local/TimeCapsuleDao;)V", "capsules", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/example/memoreels/data/model/TimeCapsuleEntity;", "getCapsules", "()Lkotlinx/coroutines/flow/StateFlow;", "createCapsule", "", "title", "", "mediaUris", "unlockDate", "", "deleteCapsule", "id", "openCapsule", "Companion", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class TimeCapsuleViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.memoreels.data.local.TimeCapsuleDao timeCapsuleDao = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "TimeCapsuleVM";
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.memoreels.data.model.TimeCapsuleEntity>> capsules = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.example.memoreels.ui.viewmodel.TimeCapsuleViewModel.Companion Companion = null;
    
    @javax.inject.Inject()
    public TimeCapsuleViewModel(@org.jetbrains.annotations.NotNull()
    com.example.memoreels.data.local.TimeCapsuleDao timeCapsuleDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.memoreels.data.model.TimeCapsuleEntity>> getCapsules() {
        return null;
    }
    
    public final void createCapsule(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> mediaUris, long unlockDate) {
    }
    
    public final void openCapsule(long id) {
    }
    
    public final void deleteCapsule(long id) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/example/memoreels/ui/viewmodel/TimeCapsuleViewModel$Companion;", "", "()V", "TAG", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}