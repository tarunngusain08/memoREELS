package com.example.memoreels.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.AdvertisingOptions
import com.google.android.gms.nearby.connection.ConnectionInfo
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback
import com.google.android.gms.nearby.connection.ConnectionResolution
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes
import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo
import com.google.android.gms.nearby.connection.DiscoveryOptions
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback
import com.google.android.gms.nearby.connection.PayloadCallback
import com.google.android.gms.nearby.connection.PayloadTransferUpdate
import com.google.android.gms.nearby.connection.Payload
import com.google.android.gms.nearby.connection.Strategy
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/** Represents a discovered nearby device. */
data class NearbyDevice(
    val endpointId: String,
    val name: String,
    val isConnected: Boolean = false
)

enum class SharingState {
    IDLE,
    ADVERTISING,
    DISCOVERING,
    CONNECTED,
    TRANSFERRING,
    COMPLETE,
    ERROR
}

@HiltViewModel
class NearbySharingViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    companion object {
        private const val TAG = "NearbySharingVM"
        private const val SERVICE_ID = "com.example.memoreels.nearby"
    }

    private val connectionsClient by lazy {
        Nearby.getConnectionsClient(context)
    }

    private val _state = MutableStateFlow(SharingState.IDLE)
    val state: StateFlow<SharingState> = _state.asStateFlow()

    private val _devices = MutableStateFlow<List<NearbyDevice>>(emptyList())
    val devices: StateFlow<List<NearbyDevice>> = _devices.asStateFlow()

    private val _statusMessage = MutableStateFlow("")
    val statusMessage: StateFlow<String> = _statusMessage.asStateFlow()

    private val connectionCallback = object : ConnectionLifecycleCallback() {
        override fun onConnectionInitiated(endpointId: String, info: ConnectionInfo) {
            _statusMessage.value = "Connection request from ${info.endpointName}"
            // Auto-accept for simplicity
            connectionsClient.acceptConnection(endpointId, payloadCallback)
        }

        override fun onConnectionResult(endpointId: String, result: ConnectionResolution) {
            when (result.status.statusCode) {
                ConnectionsStatusCodes.STATUS_OK -> {
                    _state.value = SharingState.CONNECTED
                    _statusMessage.value = "Connected!"
                    _devices.value = _devices.value.map {
                        if (it.endpointId == endpointId) it.copy(isConnected = true) else it
                    }
                }
                else -> {
                    _statusMessage.value = "Connection failed"
                }
            }
        }

        override fun onDisconnected(endpointId: String) {
            _statusMessage.value = "Disconnected"
            _state.value = SharingState.IDLE
            _devices.value = _devices.value.filter { it.endpointId != endpointId }
        }
    }

    private val discoveryCallback = object : EndpointDiscoveryCallback() {
        override fun onEndpointFound(endpointId: String, info: DiscoveredEndpointInfo) {
            _devices.value = _devices.value + NearbyDevice(
                endpointId = endpointId,
                name = info.endpointName
            )
            _statusMessage.value = "Found: ${info.endpointName}"
        }

        override fun onEndpointLost(endpointId: String) {
            _devices.value = _devices.value.filter { it.endpointId != endpointId }
        }
    }

    private val payloadCallback = object : PayloadCallback() {
        override fun onPayloadReceived(endpointId: String, payload: Payload) {
            _statusMessage.value = "Receiving data..."
        }

        override fun onPayloadTransferUpdate(endpointId: String, update: PayloadTransferUpdate) {
            if (update.status == PayloadTransferUpdate.Status.SUCCESS) {
                _statusMessage.value = "Transfer complete!"
                _state.value = SharingState.COMPLETE
            }
        }
    }

    fun startAdvertising() {
        _state.value = SharingState.ADVERTISING
        _statusMessage.value = "Waiting for nearby devices..."

        val options = AdvertisingOptions.Builder()
            .setStrategy(Strategy.P2P_STAR)
            .build()

        connectionsClient.startAdvertising(
            "memoREELS User",
            SERVICE_ID,
            connectionCallback,
            options
        ).addOnSuccessListener {
            _statusMessage.value = "Advertising... waiting for connections"
        }.addOnFailureListener { e ->
            Log.e(TAG, "Advertising failed", e)
            _statusMessage.value = "Failed to start advertising"
            _state.value = SharingState.ERROR
        }
    }

    fun startDiscovering() {
        _state.value = SharingState.DISCOVERING
        _statusMessage.value = "Searching for nearby devices..."

        val options = DiscoveryOptions.Builder()
            .setStrategy(Strategy.P2P_STAR)
            .build()

        connectionsClient.startDiscovery(
            SERVICE_ID,
            discoveryCallback,
            options
        ).addOnSuccessListener {
            _statusMessage.value = "Discovering... look for nearby devices"
        }.addOnFailureListener { e ->
            Log.e(TAG, "Discovery failed", e)
            _statusMessage.value = "Failed to start discovery"
            _state.value = SharingState.ERROR
        }
    }

    fun connectToDevice(endpointId: String) {
        connectionsClient.requestConnection(
            "memoREELS User",
            endpointId,
            connectionCallback
        ).addOnSuccessListener {
            _statusMessage.value = "Requesting connection..."
        }.addOnFailureListener { e ->
            Log.e(TAG, "Connection request failed", e)
            _statusMessage.value = "Connection request failed"
        }
    }

    fun stop() {
        connectionsClient.stopAllEndpoints()
        connectionsClient.stopAdvertising()
        connectionsClient.stopDiscovery()
        _state.value = SharingState.IDLE
        _devices.value = emptyList()
        _statusMessage.value = ""
    }

    override fun onCleared() {
        super.onCleared()
        stop()
    }
}
