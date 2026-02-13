package com.example.memoreels.data.local;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.memoreels.data.model.FaceClusterEntity;
import com.example.memoreels.data.model.FaceMediaEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
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
public final class FaceClusterDao_Impl implements FaceClusterDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<FaceClusterEntity> __insertionAdapterOfFaceClusterEntity;

  private final EntityInsertionAdapter<FaceMediaEntity> __insertionAdapterOfFaceMediaEntity;

  private final EntityDeletionOrUpdateAdapter<FaceClusterEntity> __updateAdapterOfFaceClusterEntity;

  private final SharedSQLiteStatement __preparedStmtOfRenameCluster;

  public FaceClusterDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFaceClusterEntity = new EntityInsertionAdapter<FaceClusterEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `face_clusters` (`id`,`name`,`avatarUri`,`mediaCount`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final FaceClusterEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getAvatarUri() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getAvatarUri());
        }
        statement.bindLong(4, entity.getMediaCount());
      }
    };
    this.__insertionAdapterOfFaceMediaEntity = new EntityInsertionAdapter<FaceMediaEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `face_media` (`mediaUri`,`clusterId`) VALUES (?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final FaceMediaEntity entity) {
        if (entity.getMediaUri() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getMediaUri());
        }
        statement.bindLong(2, entity.getClusterId());
      }
    };
    this.__updateAdapterOfFaceClusterEntity = new EntityDeletionOrUpdateAdapter<FaceClusterEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `face_clusters` SET `id` = ?,`name` = ?,`avatarUri` = ?,`mediaCount` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final FaceClusterEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getAvatarUri() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getAvatarUri());
        }
        statement.bindLong(4, entity.getMediaCount());
        statement.bindLong(5, entity.getId());
      }
    };
    this.__preparedStmtOfRenameCluster = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE face_clusters SET name = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertCluster(final FaceClusterEntity cluster,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfFaceClusterEntity.insertAndReturnId(cluster);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertFaceMedia(final List<FaceMediaEntity> entries,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfFaceMediaEntity.insert(entries);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateCluster(final FaceClusterEntity cluster,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfFaceClusterEntity.handle(cluster);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object renameCluster(final long id, final String name,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfRenameCluster.acquire();
        int _argIndex = 1;
        if (name == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, name);
        }
        _argIndex = 2;
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
          __preparedStmtOfRenameCluster.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<FaceClusterEntity>> getAllClusters() {
    final String _sql = "SELECT * FROM face_clusters ORDER BY mediaCount DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"face_clusters"}, new Callable<List<FaceClusterEntity>>() {
      @Override
      @NonNull
      public List<FaceClusterEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfAvatarUri = CursorUtil.getColumnIndexOrThrow(_cursor, "avatarUri");
          final int _cursorIndexOfMediaCount = CursorUtil.getColumnIndexOrThrow(_cursor, "mediaCount");
          final List<FaceClusterEntity> _result = new ArrayList<FaceClusterEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FaceClusterEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpAvatarUri;
            if (_cursor.isNull(_cursorIndexOfAvatarUri)) {
              _tmpAvatarUri = null;
            } else {
              _tmpAvatarUri = _cursor.getString(_cursorIndexOfAvatarUri);
            }
            final int _tmpMediaCount;
            _tmpMediaCount = _cursor.getInt(_cursorIndexOfMediaCount);
            _item = new FaceClusterEntity(_tmpId,_tmpName,_tmpAvatarUri,_tmpMediaCount);
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
  public Flow<List<String>> getMediaForCluster(final long clusterId) {
    final String _sql = "SELECT mediaUri FROM face_media WHERE clusterId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, clusterId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"face_media"}, new Callable<List<String>>() {
      @Override
      @NonNull
      public List<String> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final List<String> _result = new ArrayList<String>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final String _item;
            if (_cursor.isNull(0)) {
              _item = null;
            } else {
              _item = _cursor.getString(0);
            }
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
  public Object countMediaForCluster(final long clusterId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM face_media WHERE clusterId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, clusterId);
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
