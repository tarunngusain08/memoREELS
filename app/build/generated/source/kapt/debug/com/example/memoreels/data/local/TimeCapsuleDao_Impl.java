package com.example.memoreels.data.local;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.memoreels.data.model.TimeCapsuleEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
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
public final class TimeCapsuleDao_Impl implements TimeCapsuleDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TimeCapsuleEntity> __insertionAdapterOfTimeCapsuleEntity;

  private final EntityDeletionOrUpdateAdapter<TimeCapsuleEntity> __updateAdapterOfTimeCapsuleEntity;

  private final SharedSQLiteStatement __preparedStmtOfMarkOpened;

  private final SharedSQLiteStatement __preparedStmtOfDelete;

  public TimeCapsuleDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTimeCapsuleEntity = new EntityInsertionAdapter<TimeCapsuleEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `time_capsules` (`id`,`title`,`mediaUris`,`unlockDate`,`createdAt`,`isOpened`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TimeCapsuleEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTitle());
        }
        if (entity.getMediaUris() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getMediaUris());
        }
        statement.bindLong(4, entity.getUnlockDate());
        statement.bindLong(5, entity.getCreatedAt());
        final int _tmp = entity.isOpened() ? 1 : 0;
        statement.bindLong(6, _tmp);
      }
    };
    this.__updateAdapterOfTimeCapsuleEntity = new EntityDeletionOrUpdateAdapter<TimeCapsuleEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `time_capsules` SET `id` = ?,`title` = ?,`mediaUris` = ?,`unlockDate` = ?,`createdAt` = ?,`isOpened` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TimeCapsuleEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTitle());
        }
        if (entity.getMediaUris() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getMediaUris());
        }
        statement.bindLong(4, entity.getUnlockDate());
        statement.bindLong(5, entity.getCreatedAt());
        final int _tmp = entity.isOpened() ? 1 : 0;
        statement.bindLong(6, _tmp);
        statement.bindLong(7, entity.getId());
      }
    };
    this.__preparedStmtOfMarkOpened = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE time_capsules SET isOpened = 1 WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDelete = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM time_capsules WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final TimeCapsuleEntity capsule, final Continuation<? super Long> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfTimeCapsuleEntity.insertAndReturnId(capsule);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object update(final TimeCapsuleEntity capsule, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfTimeCapsuleEntity.handle(capsule);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object markOpened(final long id, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkOpened.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
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
          __preparedStmtOfMarkOpened.release(_stmt);
        }
      }
    }, arg1);
  }

  @Override
  public Object delete(final long id, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDelete.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
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
    }, arg1);
  }

  @Override
  public Flow<List<TimeCapsuleEntity>> getAll() {
    final String _sql = "SELECT * FROM time_capsules ORDER BY unlockDate ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"time_capsules"}, new Callable<List<TimeCapsuleEntity>>() {
      @Override
      @NonNull
      public List<TimeCapsuleEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfMediaUris = CursorUtil.getColumnIndexOrThrow(_cursor, "mediaUris");
          final int _cursorIndexOfUnlockDate = CursorUtil.getColumnIndexOrThrow(_cursor, "unlockDate");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfIsOpened = CursorUtil.getColumnIndexOrThrow(_cursor, "isOpened");
          final List<TimeCapsuleEntity> _result = new ArrayList<TimeCapsuleEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TimeCapsuleEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpMediaUris;
            if (_cursor.isNull(_cursorIndexOfMediaUris)) {
              _tmpMediaUris = null;
            } else {
              _tmpMediaUris = _cursor.getString(_cursorIndexOfMediaUris);
            }
            final long _tmpUnlockDate;
            _tmpUnlockDate = _cursor.getLong(_cursorIndexOfUnlockDate);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final boolean _tmpIsOpened;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsOpened);
            _tmpIsOpened = _tmp != 0;
            _item = new TimeCapsuleEntity(_tmpId,_tmpTitle,_tmpMediaUris,_tmpUnlockDate,_tmpCreatedAt,_tmpIsOpened);
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
  public Object getById(final long id, final Continuation<? super TimeCapsuleEntity> arg1) {
    final String _sql = "SELECT * FROM time_capsules WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<TimeCapsuleEntity>() {
      @Override
      @Nullable
      public TimeCapsuleEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfMediaUris = CursorUtil.getColumnIndexOrThrow(_cursor, "mediaUris");
          final int _cursorIndexOfUnlockDate = CursorUtil.getColumnIndexOrThrow(_cursor, "unlockDate");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfIsOpened = CursorUtil.getColumnIndexOrThrow(_cursor, "isOpened");
          final TimeCapsuleEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpMediaUris;
            if (_cursor.isNull(_cursorIndexOfMediaUris)) {
              _tmpMediaUris = null;
            } else {
              _tmpMediaUris = _cursor.getString(_cursorIndexOfMediaUris);
            }
            final long _tmpUnlockDate;
            _tmpUnlockDate = _cursor.getLong(_cursorIndexOfUnlockDate);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final boolean _tmpIsOpened;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsOpened);
            _tmpIsOpened = _tmp != 0;
            _result = new TimeCapsuleEntity(_tmpId,_tmpTitle,_tmpMediaUris,_tmpUnlockDate,_tmpCreatedAt,_tmpIsOpened);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, arg1);
  }

  @Override
  public Object getUnlockedCapsules(final long now,
      final Continuation<? super List<TimeCapsuleEntity>> arg1) {
    final String _sql = "SELECT * FROM time_capsules WHERE unlockDate <= ? AND isOpened = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, now);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<TimeCapsuleEntity>>() {
      @Override
      @NonNull
      public List<TimeCapsuleEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfMediaUris = CursorUtil.getColumnIndexOrThrow(_cursor, "mediaUris");
          final int _cursorIndexOfUnlockDate = CursorUtil.getColumnIndexOrThrow(_cursor, "unlockDate");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfIsOpened = CursorUtil.getColumnIndexOrThrow(_cursor, "isOpened");
          final List<TimeCapsuleEntity> _result = new ArrayList<TimeCapsuleEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TimeCapsuleEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpMediaUris;
            if (_cursor.isNull(_cursorIndexOfMediaUris)) {
              _tmpMediaUris = null;
            } else {
              _tmpMediaUris = _cursor.getString(_cursorIndexOfMediaUris);
            }
            final long _tmpUnlockDate;
            _tmpUnlockDate = _cursor.getLong(_cursorIndexOfUnlockDate);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final boolean _tmpIsOpened;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsOpened);
            _tmpIsOpened = _tmp != 0;
            _item = new TimeCapsuleEntity(_tmpId,_tmpTitle,_tmpMediaUris,_tmpUnlockDate,_tmpCreatedAt,_tmpIsOpened);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, arg1);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
