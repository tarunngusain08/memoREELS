package com.example.memoreels.ui.viewmodel;

import android.util.Log;
import androidx.lifecycle.ViewModel;
import com.example.memoreels.data.local.VideoTagDao;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

/**
 * Available mood filters.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u000e\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B%\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006\u00a2\u0006\u0002\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013\u00a8\u0006\u0014"}, d2 = {"Lcom/example/memoreels/ui/viewmodel/Mood;", "", "label", "", "emoji", "tags", "", "(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/util/List;)V", "getEmoji", "()Ljava/lang/String;", "getLabel", "getTags", "()Ljava/util/List;", "ALL", "HAPPY", "NATURE", "TRAVEL", "FOOD", "PETS", "WATER", "app_debug"})
public enum Mood {
    /*public static final*/ ALL /* = new ALL(null, null, null) */,
    /*public static final*/ HAPPY /* = new HAPPY(null, null, null) */,
    /*public static final*/ NATURE /* = new NATURE(null, null, null) */,
    /*public static final*/ TRAVEL /* = new TRAVEL(null, null, null) */,
    /*public static final*/ FOOD /* = new FOOD(null, null, null) */,
    /*public static final*/ PETS /* = new PETS(null, null, null) */,
    /*public static final*/ WATER /* = new WATER(null, null, null) */;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String label = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String emoji = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> tags = null;
    
    Mood(java.lang.String label, java.lang.String emoji, java.util.List<java.lang.String> tags) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLabel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getEmoji() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getTags() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.example.memoreels.ui.viewmodel.Mood> getEntries() {
        return null;
    }
}