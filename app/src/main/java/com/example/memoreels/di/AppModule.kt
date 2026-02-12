package com.example.memoreels.di

import android.content.Context
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.memoreels.data.datasource.VideoPagingSourceFactory
import com.example.memoreels.data.local.AppDatabase
import com.example.memoreels.data.local.FavoritesDao
import com.example.memoreels.data.local.VideoTagDao
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

    /** Adds the video_tags table without touching favorites. */
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

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return androidx.room.Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "memoreels_db"
        ).addMigrations(MIGRATION_1_2).build()
    }

    @Provides
    @Singleton
    fun provideFavoritesDao(database: AppDatabase): FavoritesDao {
        return database.favoritesDao()
    }

    @Provides
    @Singleton
    fun provideVideoTagDao(database: AppDatabase): VideoTagDao {
        return database.videoTagDao()
    }

    @Provides
    @Singleton
    fun provideVideoRepository(impl: VideoRepositoryImpl): VideoRepository {
        return impl
    }

    @Provides
    @Singleton
    fun provideVideoPagingSourceFactory(
        @ApplicationContext context: Context
    ): VideoPagingSourceFactory {
        return VideoPagingSourceFactory(context)
    }
}
