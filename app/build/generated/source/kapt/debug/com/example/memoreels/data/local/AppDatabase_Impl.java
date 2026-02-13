package com.example.memoreels.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile FavoritesDao _favoritesDao;

  private volatile VideoTagDao _videoTagDao;

  private volatile TimeCapsuleDao _timeCapsuleDao;

  private volatile VoiceNoteDao _voiceNoteDao;

  private volatile JournalDao _journalDao;

  private volatile MediaLocationDao _mediaLocationDao;

  private volatile FaceClusterDao _faceClusterDao;

  private volatile MediaHashDao _mediaHashDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(4) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `favorites` (`videoUri` TEXT NOT NULL, `note` TEXT, `createdAt` INTEGER NOT NULL, PRIMARY KEY(`videoUri`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `video_tags` (`videoUri` TEXT NOT NULL, `tag` TEXT NOT NULL, `confidence` REAL NOT NULL, PRIMARY KEY(`videoUri`, `tag`))");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_video_tags_tag` ON `video_tags` (`tag`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_video_tags_tag_confidence` ON `video_tags` (`tag`, `confidence`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `time_capsules` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT NOT NULL, `mediaUris` TEXT NOT NULL, `unlockDate` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, `isOpened` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `voice_notes` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `mediaUri` TEXT NOT NULL, `audioPath` TEXT NOT NULL, `durationMs` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `journal_entries` (`date` TEXT NOT NULL, `note` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, PRIMARY KEY(`date`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `media_locations` (`mediaUri` TEXT NOT NULL, `latitude` REAL NOT NULL, `longitude` REAL NOT NULL, `locationName` TEXT, PRIMARY KEY(`mediaUri`))");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_media_locations_latitude_longitude` ON `media_locations` (`latitude`, `longitude`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `face_clusters` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `avatarUri` TEXT, `mediaCount` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `face_media` (`mediaUri` TEXT NOT NULL, `clusterId` INTEGER NOT NULL, PRIMARY KEY(`mediaUri`, `clusterId`))");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_face_media_clusterId` ON `face_media` (`clusterId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `media_hashes` (`mediaUri` TEXT NOT NULL, `pHash` TEXT NOT NULL, PRIMARY KEY(`mediaUri`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '9628ec8f09e4c91154c856835af3f636')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `favorites`");
        db.execSQL("DROP TABLE IF EXISTS `video_tags`");
        db.execSQL("DROP TABLE IF EXISTS `time_capsules`");
        db.execSQL("DROP TABLE IF EXISTS `voice_notes`");
        db.execSQL("DROP TABLE IF EXISTS `journal_entries`");
        db.execSQL("DROP TABLE IF EXISTS `media_locations`");
        db.execSQL("DROP TABLE IF EXISTS `face_clusters`");
        db.execSQL("DROP TABLE IF EXISTS `face_media`");
        db.execSQL("DROP TABLE IF EXISTS `media_hashes`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsFavorites = new HashMap<String, TableInfo.Column>(3);
        _columnsFavorites.put("videoUri", new TableInfo.Column("videoUri", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavorites.put("note", new TableInfo.Column("note", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavorites.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFavorites = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFavorites = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFavorites = new TableInfo("favorites", _columnsFavorites, _foreignKeysFavorites, _indicesFavorites);
        final TableInfo _existingFavorites = TableInfo.read(db, "favorites");
        if (!_infoFavorites.equals(_existingFavorites)) {
          return new RoomOpenHelper.ValidationResult(false, "favorites(com.example.memoreels.data.model.FavoriteEntity).\n"
                  + " Expected:\n" + _infoFavorites + "\n"
                  + " Found:\n" + _existingFavorites);
        }
        final HashMap<String, TableInfo.Column> _columnsVideoTags = new HashMap<String, TableInfo.Column>(3);
        _columnsVideoTags.put("videoUri", new TableInfo.Column("videoUri", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoTags.put("tag", new TableInfo.Column("tag", "TEXT", true, 2, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoTags.put("confidence", new TableInfo.Column("confidence", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysVideoTags = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesVideoTags = new HashSet<TableInfo.Index>(2);
        _indicesVideoTags.add(new TableInfo.Index("index_video_tags_tag", false, Arrays.asList("tag"), Arrays.asList("ASC")));
        _indicesVideoTags.add(new TableInfo.Index("index_video_tags_tag_confidence", false, Arrays.asList("tag", "confidence"), Arrays.asList("ASC", "ASC")));
        final TableInfo _infoVideoTags = new TableInfo("video_tags", _columnsVideoTags, _foreignKeysVideoTags, _indicesVideoTags);
        final TableInfo _existingVideoTags = TableInfo.read(db, "video_tags");
        if (!_infoVideoTags.equals(_existingVideoTags)) {
          return new RoomOpenHelper.ValidationResult(false, "video_tags(com.example.memoreels.data.model.VideoTagEntity).\n"
                  + " Expected:\n" + _infoVideoTags + "\n"
                  + " Found:\n" + _existingVideoTags);
        }
        final HashMap<String, TableInfo.Column> _columnsTimeCapsules = new HashMap<String, TableInfo.Column>(6);
        _columnsTimeCapsules.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTimeCapsules.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTimeCapsules.put("mediaUris", new TableInfo.Column("mediaUris", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTimeCapsules.put("unlockDate", new TableInfo.Column("unlockDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTimeCapsules.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTimeCapsules.put("isOpened", new TableInfo.Column("isOpened", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTimeCapsules = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTimeCapsules = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTimeCapsules = new TableInfo("time_capsules", _columnsTimeCapsules, _foreignKeysTimeCapsules, _indicesTimeCapsules);
        final TableInfo _existingTimeCapsules = TableInfo.read(db, "time_capsules");
        if (!_infoTimeCapsules.equals(_existingTimeCapsules)) {
          return new RoomOpenHelper.ValidationResult(false, "time_capsules(com.example.memoreels.data.model.TimeCapsuleEntity).\n"
                  + " Expected:\n" + _infoTimeCapsules + "\n"
                  + " Found:\n" + _existingTimeCapsules);
        }
        final HashMap<String, TableInfo.Column> _columnsVoiceNotes = new HashMap<String, TableInfo.Column>(5);
        _columnsVoiceNotes.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVoiceNotes.put("mediaUri", new TableInfo.Column("mediaUri", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVoiceNotes.put("audioPath", new TableInfo.Column("audioPath", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVoiceNotes.put("durationMs", new TableInfo.Column("durationMs", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVoiceNotes.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysVoiceNotes = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesVoiceNotes = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoVoiceNotes = new TableInfo("voice_notes", _columnsVoiceNotes, _foreignKeysVoiceNotes, _indicesVoiceNotes);
        final TableInfo _existingVoiceNotes = TableInfo.read(db, "voice_notes");
        if (!_infoVoiceNotes.equals(_existingVoiceNotes)) {
          return new RoomOpenHelper.ValidationResult(false, "voice_notes(com.example.memoreels.data.model.VoiceNoteEntity).\n"
                  + " Expected:\n" + _infoVoiceNotes + "\n"
                  + " Found:\n" + _existingVoiceNotes);
        }
        final HashMap<String, TableInfo.Column> _columnsJournalEntries = new HashMap<String, TableInfo.Column>(4);
        _columnsJournalEntries.put("date", new TableInfo.Column("date", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsJournalEntries.put("note", new TableInfo.Column("note", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsJournalEntries.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsJournalEntries.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysJournalEntries = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesJournalEntries = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoJournalEntries = new TableInfo("journal_entries", _columnsJournalEntries, _foreignKeysJournalEntries, _indicesJournalEntries);
        final TableInfo _existingJournalEntries = TableInfo.read(db, "journal_entries");
        if (!_infoJournalEntries.equals(_existingJournalEntries)) {
          return new RoomOpenHelper.ValidationResult(false, "journal_entries(com.example.memoreels.data.model.JournalEntryEntity).\n"
                  + " Expected:\n" + _infoJournalEntries + "\n"
                  + " Found:\n" + _existingJournalEntries);
        }
        final HashMap<String, TableInfo.Column> _columnsMediaLocations = new HashMap<String, TableInfo.Column>(4);
        _columnsMediaLocations.put("mediaUri", new TableInfo.Column("mediaUri", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMediaLocations.put("latitude", new TableInfo.Column("latitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMediaLocations.put("longitude", new TableInfo.Column("longitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMediaLocations.put("locationName", new TableInfo.Column("locationName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMediaLocations = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMediaLocations = new HashSet<TableInfo.Index>(1);
        _indicesMediaLocations.add(new TableInfo.Index("index_media_locations_latitude_longitude", false, Arrays.asList("latitude", "longitude"), Arrays.asList("ASC", "ASC")));
        final TableInfo _infoMediaLocations = new TableInfo("media_locations", _columnsMediaLocations, _foreignKeysMediaLocations, _indicesMediaLocations);
        final TableInfo _existingMediaLocations = TableInfo.read(db, "media_locations");
        if (!_infoMediaLocations.equals(_existingMediaLocations)) {
          return new RoomOpenHelper.ValidationResult(false, "media_locations(com.example.memoreels.data.model.MediaLocationEntity).\n"
                  + " Expected:\n" + _infoMediaLocations + "\n"
                  + " Found:\n" + _existingMediaLocations);
        }
        final HashMap<String, TableInfo.Column> _columnsFaceClusters = new HashMap<String, TableInfo.Column>(4);
        _columnsFaceClusters.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFaceClusters.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFaceClusters.put("avatarUri", new TableInfo.Column("avatarUri", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFaceClusters.put("mediaCount", new TableInfo.Column("mediaCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFaceClusters = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFaceClusters = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFaceClusters = new TableInfo("face_clusters", _columnsFaceClusters, _foreignKeysFaceClusters, _indicesFaceClusters);
        final TableInfo _existingFaceClusters = TableInfo.read(db, "face_clusters");
        if (!_infoFaceClusters.equals(_existingFaceClusters)) {
          return new RoomOpenHelper.ValidationResult(false, "face_clusters(com.example.memoreels.data.model.FaceClusterEntity).\n"
                  + " Expected:\n" + _infoFaceClusters + "\n"
                  + " Found:\n" + _existingFaceClusters);
        }
        final HashMap<String, TableInfo.Column> _columnsFaceMedia = new HashMap<String, TableInfo.Column>(2);
        _columnsFaceMedia.put("mediaUri", new TableInfo.Column("mediaUri", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFaceMedia.put("clusterId", new TableInfo.Column("clusterId", "INTEGER", true, 2, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFaceMedia = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFaceMedia = new HashSet<TableInfo.Index>(1);
        _indicesFaceMedia.add(new TableInfo.Index("index_face_media_clusterId", false, Arrays.asList("clusterId"), Arrays.asList("ASC")));
        final TableInfo _infoFaceMedia = new TableInfo("face_media", _columnsFaceMedia, _foreignKeysFaceMedia, _indicesFaceMedia);
        final TableInfo _existingFaceMedia = TableInfo.read(db, "face_media");
        if (!_infoFaceMedia.equals(_existingFaceMedia)) {
          return new RoomOpenHelper.ValidationResult(false, "face_media(com.example.memoreels.data.model.FaceMediaEntity).\n"
                  + " Expected:\n" + _infoFaceMedia + "\n"
                  + " Found:\n" + _existingFaceMedia);
        }
        final HashMap<String, TableInfo.Column> _columnsMediaHashes = new HashMap<String, TableInfo.Column>(2);
        _columnsMediaHashes.put("mediaUri", new TableInfo.Column("mediaUri", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMediaHashes.put("pHash", new TableInfo.Column("pHash", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMediaHashes = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMediaHashes = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMediaHashes = new TableInfo("media_hashes", _columnsMediaHashes, _foreignKeysMediaHashes, _indicesMediaHashes);
        final TableInfo _existingMediaHashes = TableInfo.read(db, "media_hashes");
        if (!_infoMediaHashes.equals(_existingMediaHashes)) {
          return new RoomOpenHelper.ValidationResult(false, "media_hashes(com.example.memoreels.data.model.MediaHashEntity).\n"
                  + " Expected:\n" + _infoMediaHashes + "\n"
                  + " Found:\n" + _existingMediaHashes);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "9628ec8f09e4c91154c856835af3f636", "a079b12819a20342e9b23f1fbafed206");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "favorites","video_tags","time_capsules","voice_notes","journal_entries","media_locations","face_clusters","face_media","media_hashes");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `favorites`");
      _db.execSQL("DELETE FROM `video_tags`");
      _db.execSQL("DELETE FROM `time_capsules`");
      _db.execSQL("DELETE FROM `voice_notes`");
      _db.execSQL("DELETE FROM `journal_entries`");
      _db.execSQL("DELETE FROM `media_locations`");
      _db.execSQL("DELETE FROM `face_clusters`");
      _db.execSQL("DELETE FROM `face_media`");
      _db.execSQL("DELETE FROM `media_hashes`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(FavoritesDao.class, FavoritesDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(VideoTagDao.class, VideoTagDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(TimeCapsuleDao.class, TimeCapsuleDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(VoiceNoteDao.class, VoiceNoteDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(JournalDao.class, JournalDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(MediaLocationDao.class, MediaLocationDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(FaceClusterDao.class, FaceClusterDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(MediaHashDao.class, MediaHashDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public FavoritesDao favoritesDao() {
    if (_favoritesDao != null) {
      return _favoritesDao;
    } else {
      synchronized(this) {
        if(_favoritesDao == null) {
          _favoritesDao = new FavoritesDao_Impl(this);
        }
        return _favoritesDao;
      }
    }
  }

  @Override
  public VideoTagDao videoTagDao() {
    if (_videoTagDao != null) {
      return _videoTagDao;
    } else {
      synchronized(this) {
        if(_videoTagDao == null) {
          _videoTagDao = new VideoTagDao_Impl(this);
        }
        return _videoTagDao;
      }
    }
  }

  @Override
  public TimeCapsuleDao timeCapsuleDao() {
    if (_timeCapsuleDao != null) {
      return _timeCapsuleDao;
    } else {
      synchronized(this) {
        if(_timeCapsuleDao == null) {
          _timeCapsuleDao = new TimeCapsuleDao_Impl(this);
        }
        return _timeCapsuleDao;
      }
    }
  }

  @Override
  public VoiceNoteDao voiceNoteDao() {
    if (_voiceNoteDao != null) {
      return _voiceNoteDao;
    } else {
      synchronized(this) {
        if(_voiceNoteDao == null) {
          _voiceNoteDao = new VoiceNoteDao_Impl(this);
        }
        return _voiceNoteDao;
      }
    }
  }

  @Override
  public JournalDao journalDao() {
    if (_journalDao != null) {
      return _journalDao;
    } else {
      synchronized(this) {
        if(_journalDao == null) {
          _journalDao = new JournalDao_Impl(this);
        }
        return _journalDao;
      }
    }
  }

  @Override
  public MediaLocationDao mediaLocationDao() {
    if (_mediaLocationDao != null) {
      return _mediaLocationDao;
    } else {
      synchronized(this) {
        if(_mediaLocationDao == null) {
          _mediaLocationDao = new MediaLocationDao_Impl(this);
        }
        return _mediaLocationDao;
      }
    }
  }

  @Override
  public FaceClusterDao faceClusterDao() {
    if (_faceClusterDao != null) {
      return _faceClusterDao;
    } else {
      synchronized(this) {
        if(_faceClusterDao == null) {
          _faceClusterDao = new FaceClusterDao_Impl(this);
        }
        return _faceClusterDao;
      }
    }
  }

  @Override
  public MediaHashDao mediaHashDao() {
    if (_mediaHashDao != null) {
      return _mediaHashDao;
    } else {
      synchronized(this) {
        if(_mediaHashDao == null) {
          _mediaHashDao = new MediaHashDao_Impl(this);
        }
        return _mediaHashDao;
      }
    }
  }
}
