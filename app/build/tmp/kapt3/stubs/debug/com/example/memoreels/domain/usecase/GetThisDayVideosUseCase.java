package com.example.memoreels.domain.usecase;

import android.content.ContentUris;
import android.content.Context;
import android.provider.MediaStore;
import com.example.memoreels.domain.model.Video;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import java.util.Calendar;
import javax.inject.Inject;

/**
 * Returns videos whose DATE_ADDED falls on the same month+day as today
 * but in a previous year (i.e. "This Day in History" / "Memories").
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0015\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006H\u0086\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/example/memoreels/domain/usecase/GetThisDayVideosUseCase;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "invoke", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/example/memoreels/domain/model/Video;", "app_debug"})
public final class GetThisDayVideosUseCase {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    
    @javax.inject.Inject()
    public GetThisDayVideosUseCase(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.example.memoreels.domain.model.Video>> invoke() {
        return null;
    }
}