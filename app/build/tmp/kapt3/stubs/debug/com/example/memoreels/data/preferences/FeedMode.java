package com.example.memoreels.data.preferences;

import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
import kotlinx.coroutines.flow.Flow;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Feed display mode: unified (photos+videos mixed) or separate (own tabs).
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/example/memoreels/data/preferences/FeedMode;", "", "(Ljava/lang/String;I)V", "UNIFIED", "SEPARATE", "app_debug"})
public enum FeedMode {
    /*public static final*/ UNIFIED /* = new UNIFIED() */,
    /*public static final*/ SEPARATE /* = new SEPARATE() */;
    
    FeedMode() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.example.memoreels.data.preferences.FeedMode> getEntries() {
        return null;
    }
}