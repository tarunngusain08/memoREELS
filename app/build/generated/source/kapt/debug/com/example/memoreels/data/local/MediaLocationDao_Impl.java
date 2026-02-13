package com.example.memoreels.data.local;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.memoreels.data.model.MediaLocationEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class MediaLocationDao_Impl implements MediaLocationDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<MediaLocationEntity> __insertionAdapterOfMediaLocationEntity;

  public MediaLocationDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMediaLocationEntity = new EntityInsertionAdapter<MediaLocationEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `media_locations` (`mediaUri`,`latitude`,`longitude`,`locationName`) VALUES (?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MediaLocationEntity entity) {
        if (entity.getMediaUri() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getMediaUri());
        }
        statement.bindDouble(2, entity.getLatitude());
        statement.bindDouble(3, entity.getLongitude());
        if (entity.getLocationName() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getLocationName());
        }
      }
    };
  }

  @Override
  public Object insertAll(final List<MediaLocationEntity> locations,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfMediaLocationEntity.insert(locations);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<MediaLocationEntity>> getAll() {
    final String _sql = "SELECT * FROM media_locations";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"media_locations"}, new Callable<List<MediaLocationEntity>>() {
      @Override
      @NonNull
      public List<MediaLocationEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfMediaUri = CursorUtil.getColumnIndexOrThrow(_cursor, "mediaUri");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfLocationName = CursorUtil.getColumnIndexOrThrow(_cursor, "locationName");
          final List<MediaLocationEntity> _result = new ArrayList<MediaLocationEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MediaLocationEntity _item;
            final String _tmpMediaUri;
            if (_cursor.isNull(_cursorIndexOfMediaUri)) {
              _tmpMediaUri = null;
            } else {
              _tmpMediaUri = _cursor.getString(_cursorIndexOfMediaUri);
            }
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            final String _tmpLocationName;
            if (_cursor.isNull(_cursorIndexOfLocationName)) {
              _tmpLocationName = null;
            } else {
              _tmpLocationName = _cursor.getString(_cursorIndexOfLocationName);
            }
            _item = new MediaLocationEntity(_tmpMediaUri,_tmpLatitude,_tmpLongitude,_tmpLocationName);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<MediaLocationEntity>> getInBounds(final double minLat, final double maxLat,
      final double minLng, final double maxLng) {
    final String _sql = "SELECT * FROM media_locations WHERE latitude BETWEEN ? AND ? AND longitude BETWEEN ? AND ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 4);
    int _argIndex = 1;
    _statement.bindDouble(_argIndex, minLat);
    _argIndex = 2;
    _statement.bindDouble(_argIndex, maxLat);
    _argIndex = 3;
    _statement.bindDouble(_argIndex, minLng);
    _argIndex = 4;
    _statement.bindDouble(_argIndex, maxLng);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"media_locations"}, new Callable<List<MediaLocationEntity>>() {
      @Override
      @NonNull
      public List<MediaLocationEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfMediaUri = CursorUtil.getColumnIndexOrThrow(_cursor, "mediaUri");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfLocationName = CursorUtil.getColumnIndexOrThrow(_cursor, "locationName");
          final List<MediaLocationEntity> _result = new ArrayList<MediaLocationEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MediaLocationEntity _item;
            final String _tmpMediaUri;
            if (_cursor.isNull(_cursorIndexOfMediaUri)) {
              _tmpMediaUri = null;
            } else {
              _tmpMediaUri = _cursor.getString(_cursorIndexOfMediaUri);
            }
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            final String _tmpLocationName;
            if (_cursor.isNull(_cursorIndexOfLocationName)) {
              _tmpLocationName = null;
            } else {
              _tmpLocationName = _cursor.getString(_cursorIndexOfLocationName);
            }
            _item = new MediaLocationEntity(_tmpMediaUri,_tmpLatitude,_tmpLongitude,_tmpLocationName);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object count(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM media_locations";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
