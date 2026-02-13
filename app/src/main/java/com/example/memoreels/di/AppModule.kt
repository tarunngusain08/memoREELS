package com.example.memoreels.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.memoreels.data.datasource.VideoPagingSourceFactory
import com.example.memoreels.data.local.AppDatabase
import com.example.memoreels.data.local.FaceClusterDao
import com.example.memoreels.data.local.FavoritesDao
import com.example.memoreels.data.local.JournalDao
import com.example.memoreels.data.local.MediaHashDao
import com.example.memoreels.data.local.MediaLocationDao
import com.example.memoreels.data.local.TimeCapsuleDao
import com.example.memoreels.data.local.VideoTagDao
import com.example.memoreels.data.local.VoiceNoteDao
import com.example.memoreels.data.repository.VideoRepositoryImpl
import com.example.memoreels.domain.repository.VideoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                """CREATE TABLE IF NOT EXISTS `video_tags` (
                    `videoUri` TEXT NOT NULL,
                    `tag` TEXT NOT NULL,
                    `confidence` REAL NOT NULL,
                    PRIMARY KEY(`videoUri`, `tag`)
                )"""
            )
        }
    }

    private val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("CREATE INDEX IF NOT EXISTS `index_video_tags_tag` ON `video_tags` (`tag`)")
            db.execSQL("CREATE INDEX IF NOT EXISTS `index_video_tags_tag_confidence` ON `video_tags` (`tag`, `confidence`)")
        }
    }

    /** Adds tables for time capsules, voice notes, journal, locations, faces, hashes. */
    private val MIGRATION_3_4 = object : Migration(3, 4) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                """CREATE TABLE IF NOT EXISTS `time_capsules` (
                    `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    `title` TEXT NOT NULL,
                    `mediaUris` TEXT NOT NULL,
                    `unlockDate` INTEGER NOT NULL,
                    `createdAt` INTEGER NOT NULL,
                    `isOpened` INTEGER NOT NULL DEFAULT 0
                )"""
            )
            db.execSQL(
                """CREATE TABLE IF NOT EXISTS `voice_notes` (
                    `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    `mediaUri` TEXT NOT NULL,
                    `audioPath` TEXT NOT NULL,
                    `durationMs` INTEGER NOT NULL,
                    `createdAt` INTEGER NOT NULL
                )"""
            )
            db.execSQL(
                """CREATE TABLE IF NOT EXISTS `journal_entries` (
                    `date` TEXT NOT NULL PRIMARY KEY,
                    `note` TEXT NOT NULL,
                    `createdAt` INTEGER NOT NULL,
                    `updatedAt` INTEGER NOT NULL
                )"""
            )
            db.execSQL(
                """CREATE TABLE IF NOT EXISTS `media_locations` (
                    `mediaUri` TEXT NOT NULL PRIMARY KEY,
                    `latitude` REAL NOT NULL,
                    `longitude` REAL NOT NULL,
                    `locationName` TEXT
                )"""
            )
            db.execSQL("CREATE INDEX IF NOT EXISTS `index_media_locations_latitude_longitude` ON `media_locations` (`latitude`, `longitude`)")
            db.execSQL(
                """CREATE TABLE IF NOT EXISTS `face_clusters` (
                    `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    `name` TEXT,
                    `avatarUri` TEXT,
                    `mediaCount` INTEGER NOT NULL DEFAULT 0
                )"""
            )
            db.execSQL(
                """CREATE TABLE IF NOT EXISTS `face_media` (
                    `mediaUri` TEXT NOT NULL,
                    `clusterId` INTEGER NOT NULL,
                    PRIMARY KEY(`mediaUri`, `clusterId`)
                )"""
            )
            db.execSQL("CREATE INDEX IF NOT EXISTS `index_face_media_clusterId` ON `face_media` (`clusterId`)")
            db.execSQL(
                """CREATE TABLE IF NOT EXISTS `media_hashes` (
                    `mediaUri` TEXT NOT NULL PRIMARY KEY,
                    `pHash` TEXT NOT NULL
                )"""
            )
        }
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return androidx.room.Room.databaseBuilder(
            context, AppDatabase::class.java, "memoreels_db"
        ).addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4).build()
    }

    @Provides @Singleton
    fun provideFavoritesDao(db: AppDatabase): FavoritesDao = db.favoritesDao()

    @Provides @Singleton
    fun provideVideoTagDao(db: AppDatabase): VideoTagDao = db.videoTagDao()

    @Provides @Singleton
    fun provideTimeCapsuleDao(db: AppDatabase): TimeCapsuleDao = db.timeCapsuleDao()

    @Provides @Singleton
    fun provideVoiceNoteDao(db: AppDatabase): VoiceNoteDao = db.voiceNoteDao()

    @Provides @Singleton
    fun provideJournalDao(db: AppDatabase): JournalDao = db.journalDao()

    @Provides @Singleton
    fun provideMediaLocationDao(db: AppDatabase): MediaLocationDao = db.mediaLocationDao()

    @Provides @Singleton
    fun provideFaceClusterDao(db: AppDatabase): FaceClusterDao = db.faceClusterDao()

    @Provides @Singleton
    fun provideMediaHashDao(db: AppDatabase): MediaHashDao = db.mediaHashDao()

    @Provides @Singleton
    fun provideVideoRepository(impl: VideoRepositoryImpl): VideoRepository = impl

    @Provides @Singleton
    fun provideVideoPagingSourceFactory(
        @ApplicationContext context: Context
    ): VideoPagingSourceFactory = VideoPagingSourceFactory(context)

    @Provides @Singleton
    fun provideDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = PreferenceDataStoreFactory.create {
        context.preferencesDataStoreFile("user_preferences")
    }
}
