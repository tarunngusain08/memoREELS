package com.example.memoreels.ui.viewmodel;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.ViewModel;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.AdvertisingOptions;
import com.google.android.gms.nearby.connection.ConnectionInfo;
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;
import com.google.android.gms.nearby.connection.ConnectionResolution;
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes;
import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo;
import com.google.android.gms.nearby.connection.DiscoveryOptions;
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback;
import com.google.android.gms.nearby.connection.PayloadCallback;
import com.google.android.gms.nearby.connection.PayloadTransferUpdate;
import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.nearby.connection.Strategy;
import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\t\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t\u00a8\u0006\n"}, d2 = {"Lcom/example/memoreels/ui/viewmodel/SharingState;", "", "(Ljava/lang/String;I)V", "IDLE", "ADVERTISING", "DISCOVERING", "CONNECTED", "TRANSFERRING", "COMPLETE", "ERROR", "app_debug"})
public enum SharingState {
    /*public static final*/ IDLE /* = new IDLE() */,
    /*public static final*/ ADVERTISING /* = new ADVERTISING() */,
    /*public static final*/ DISCOVERING /* = new DISCOVERING() */,
    /*public static final*/ CONNECTED /* = new CONNECTED() */,
    /*public static final*/ TRANSFERRING /* = new TRANSFERRING() */,
    /*public static final*/ COMPLETE /* = new COMPLETE() */,
    /*public static final*/ ERROR /* = new ERROR() */;
    
    SharingState() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.example.memoreels.ui.viewmodel.SharingState> getEntries() {
        return null;
    }
}