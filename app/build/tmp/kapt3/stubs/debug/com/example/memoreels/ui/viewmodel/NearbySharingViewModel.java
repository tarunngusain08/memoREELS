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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0007\b\u0007\u0018\u0000 (2\u00020\u0001:\u0001(B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\fJ\b\u0010$\u001a\u00020\"H\u0014J\u0006\u0010%\u001a\u00020\"J\u0006\u0010&\u001a\u00020\"J\u0006\u0010\'\u001a\u00020\"R\u001a\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u000f\u001a\u00020\u00108BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\n0\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0018R\u0017\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\f0\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0018\u00a8\u0006)"}, d2 = {"Lcom/example/memoreels/ui/viewmodel/NearbySharingViewModel;", "Landroidx/lifecycle/ViewModel;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "_devices", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "Lcom/example/memoreels/ui/viewmodel/NearbyDevice;", "_state", "Lcom/example/memoreels/ui/viewmodel/SharingState;", "_statusMessage", "", "connectionCallback", "Lcom/google/android/gms/nearby/connection/ConnectionLifecycleCallback;", "connectionsClient", "Lcom/google/android/gms/nearby/connection/ConnectionsClient;", "getConnectionsClient", "()Lcom/google/android/gms/nearby/connection/ConnectionsClient;", "connectionsClient$delegate", "Lkotlin/Lazy;", "devices", "Lkotlinx/coroutines/flow/StateFlow;", "getDevices", "()Lkotlinx/coroutines/flow/StateFlow;", "discoveryCallback", "Lcom/google/android/gms/nearby/connection/EndpointDiscoveryCallback;", "payloadCallback", "Lcom/google/android/gms/nearby/connection/PayloadCallback;", "state", "getState", "statusMessage", "getStatusMessage", "connectToDevice", "", "endpointId", "onCleared", "startAdvertising", "startDiscovering", "stop", "Companion", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class NearbySharingViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "NearbySharingVM";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String SERVICE_ID = "com.example.memoreels.nearby";
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy connectionsClient$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.memoreels.ui.viewmodel.SharingState> _state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.memoreels.ui.viewmodel.SharingState> state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.example.memoreels.ui.viewmodel.NearbyDevice>> _devices = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.memoreels.ui.viewmodel.NearbyDevice>> devices = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _statusMessage = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> statusMessage = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.android.gms.nearby.connection.ConnectionLifecycleCallback connectionCallback = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.android.gms.nearby.connection.EndpointDiscoveryCallback discoveryCallback = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.android.gms.nearby.connection.PayloadCallback payloadCallback = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.example.memoreels.ui.viewmodel.NearbySharingViewModel.Companion Companion = null;
    
    @javax.inject.Inject()
    public NearbySharingViewModel(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    private final com.google.android.gms.nearby.connection.ConnectionsClient getConnectionsClient() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.memoreels.ui.viewmodel.SharingState> getState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.memoreels.ui.viewmodel.NearbyDevice>> getDevices() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getStatusMessage() {
        return null;
    }
    
    public final void startAdvertising() {
    }
    
    public final void startDiscovering() {
    }
    
    public final void connectToDevice(@org.jetbrains.annotations.NotNull()
    java.lang.String endpointId) {
    }
    
    public final void stop() {
    }
    
    @java.lang.Override()
    protected void onCleared() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/example/memoreels/ui/viewmodel/NearbySharingViewModel$Companion;", "", "()V", "SERVICE_ID", "", "TAG", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}