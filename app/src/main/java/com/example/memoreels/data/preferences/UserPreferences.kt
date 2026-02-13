package com.example.memoreels.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/** Feed display mode: unified (photos+videos mixed) or separate (own tabs). */
enum class FeedMode {
    UNIFIED,
    SEPARATE
}

/**
 * Manages user preferences via DataStore.
 * Stores feed mode, auto-mute, and loop video settings.
 */
@Singleton
class UserPreferences @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    companion object {
        private val FEED_MODE_KEY = stringPreferencesKey("feed_mode")
        private val AUTO_MUTE_KEY = booleanPreferencesKey("auto_mute")
        private val LOOP_VIDEOS_KEY = booleanPreferencesKey("loop_videos")
    }

    /** Observe the current feed mode. Defaults to UNIFIED. */
    val feedMode: Flow<FeedMode> = dataStore.data.map { preferences ->
        when (preferences[FEED_MODE_KEY]) {
            "SEPARATE" -> FeedMode.SEPARATE
            else -> FeedMode.UNIFIED
        }
    }

    /** Whether videos start muted. Defaults to false. */
    val autoMute: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[AUTO_MUTE_KEY] ?: false
    }

    /** Whether videos loop. Defaults to true. */
    val loopVideos: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[LOOP_VIDEOS_KEY] ?: true
    }

    /** Persist the chosen feed mode. */
    suspend fun setFeedMode(mode: FeedMode) {
        dataStore.edit { preferences ->
            preferences[FEED_MODE_KEY] = mode.name
        }
    }

    /** Persist auto-mute preference. */
    suspend fun setAutoMute(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[AUTO_MUTE_KEY] = enabled
        }
    }

    /** Persist loop videos preference. */
    suspend fun setLoopVideos(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[LOOP_VIDEOS_KEY] = enabled
        }
    }
}
