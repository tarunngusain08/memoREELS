package com.example.memoreels.data.local;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.memoreels.data.model.MediaHashEntity;
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

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class MediaHashDao_Impl implements MediaHashDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<MediaHashEntity> __insertionAdapterOfMediaHashEntity;

  private final SharedSQLiteStatement __preparedStmtOfDelete;

  public MediaHashDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMediaHashEntity = new EntityInsertionAdapter<MediaHashEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `media_hashes` (`mediaUri`,`pHash`) VALUES (?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MediaHashEntity entity) {
        if (entity.getMediaUri() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getMediaUri());
        }
        if (entity.getPHash() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getPHash());
        }
      }
    };
    this.__preparedStmtOfDelete = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM media_hashes WHERE mediaUri = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertAll(final List<MediaHashEntity> hashes,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfMediaHashEntity.insert(hashes);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final String uri, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDelete.acquire();
        int _argIndex = 1;
        if (uri == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, uri);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDelete.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getAll(final Continuation<? super List<MediaHashEntity>> $completion) {
    final String _sql = "SELECT * FROM media_hashes";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<MediaHashEntity>>() {
      @Override
      @NonNull
      public List<MediaHashEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfMediaUri = CursorUtil.getColumnIndexOrThrow(_cursor, "mediaUri");
          final int _cursorIndexOfPHash = CursorUtil.getColumnIndexOrThrow(_cursor, "pHash");
          final List<MediaHashEntity> _result = new ArrayList<MediaHashEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MediaHashEntity _item;
            final String _tmpMediaUri;
            if (_cursor.isNull(_cursorIndexOfMediaUri)) {
              _tmpMediaUri = null;
            } else {
              _tmpMediaUri = _cursor.getString(_cursorIndexOfMediaUri);
            }
            final String _tmpPHash;
            if (_cursor.isNull(_cursorIndexOfPHash)) {
              _tmpPHash = null;
            } else {
              _tmpPHash = _cursor.getString(_cursorIndexOfPHash);
            }
            _item = new MediaHashEntity(_tmpMediaUri,_tmpPHash);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object count(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM media_hashes";
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
