package com.example.memoreels.data.local;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.memoreels.data.model.VideoTagEntity;
import java.lang.Class;
import java.lang.Exception;
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
public final class VideoTagDao_Impl implements VideoTagDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<VideoTagEntity> __insertionAdapterOfVideoTagEntity;

  public VideoTagDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfVideoTagEntity = new EntityInsertionAdapter<VideoTagEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `video_tags` (`videoUri`,`tag`,`confidence`) VALUES (?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final VideoTagEntity entity) {
        if (entity.getVideoUri() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getVideoUri());
        }
        if (entity.getTag() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTag());
        }
        statement.bindDouble(3, entity.getConfidence());
      }
    };
  }

  @Override
  public Object insertTags(final List<VideoTagEntity> tags,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfVideoTagEntity.insert(tags);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getTagsForVideo(final String uri,
      final Continuation<? super List<VideoTagEntity>> $completion) {
    final String _sql = "SELECT * FROM video_tags WHERE videoUri = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (uri == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, uri);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<VideoTagEntity>>() {
      @Override
      @NonNull
      public List<VideoTagEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfVideoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "videoUri");
          final int _cursorIndexOfTag = CursorUtil.getColumnIndexOrThrow(_cursor, "tag");
          final int _cursorIndexOfConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "confidence");
          final List<VideoTagEntity> _result = new ArrayList<VideoTagEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final VideoTagEntity _item;
            final String _tmpVideoUri;
            if (_cursor.isNull(_cursorIndexOfVideoUri)) {
              _tmpVideoUri = null;
            } else {
              _tmpVideoUri = _cursor.getString(_cursorIndexOfVideoUri);
            }
            final String _tmpTag;
            if (_cursor.isNull(_cursorIndexOfTag)) {
              _tmpTag = null;
            } else {
              _tmpTag = _cursor.getString(_cursorIndexOfTag);
            }
            final float _tmpConfidence;
            _tmpConfidence = _cursor.getFloat(_cursorIndexOfConfidence);
            _item = new VideoTagEntity(_tmpVideoUri,_tmpTag,_tmpConfidence);
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
  public Flow<List<String>> getDistinctTags() {
    final String _sql = "SELECT DISTINCT tag FROM video_tags ORDER BY tag ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"video_tags"}, new Callable<List<String>>() {
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
  public Flow<List<String>> getVideosForTag(final String tag) {
    final String _sql = "SELECT videoUri FROM video_tags WHERE tag = ? ORDER BY confidence DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (tag == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, tag);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"video_tags"}, new Callable<List<String>>() {
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
  public Flow<List<String>> searchByTag(final String query) {
    final String _sql = "SELECT DISTINCT videoUri FROM video_tags WHERE LOWER(tag) LIKE '%' || LOWER(?) || '%'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"video_tags"}, new Callable<List<String>>() {
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
  public Flow<List<TagCount>> getTagCounts() {
    final String _sql = "SELECT tag, COUNT(DISTINCT videoUri) as cnt FROM video_tags GROUP BY tag ORDER BY cnt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"video_tags"}, new Callable<List<TagCount>>() {
      @Override
      @NonNull
      public List<TagCount> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfTag = 0;
          final int _cursorIndexOfCnt = 1;
          final List<TagCount> _result = new ArrayList<TagCount>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TagCount _item;
            final String _tmpTag;
            if (_cursor.isNull(_cursorIndexOfTag)) {
              _tmpTag = null;
            } else {
              _tmpTag = _cursor.getString(_cursorIndexOfTag);
            }
            final int _tmpCnt;
            _tmpCnt = _cursor.getInt(_cursorIndexOfCnt);
            _item = new TagCount(_tmpTag,_tmpCnt);
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
  public Object getFirstVideoForTag(final String tag,
      final Continuation<? super String> $completion) {
    final String _sql = "SELECT videoUri FROM video_tags WHERE tag = ? ORDER BY confidence DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (tag == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, tag);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<String>() {
      @Override
      @Nullable
      public String call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final String _result;
          if (_cursor.moveToFirst()) {
            if (_cursor.isNull(0)) {
              _result = null;
            } else {
              _result = _cursor.getString(0);
            }
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

  @Override
  public Object getProcessedUris(final Continuation<? super List<String>> $completion) {
    final String _sql = "SELECT DISTINCT videoUri FROM video_tags";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<String>>() {
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
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object searchByTagDirect(final String query,
      final Continuation<? super List<String>> $completion) {
    final String _sql = "SELECT DISTINCT videoUri FROM video_tags WHERE LOWER(tag) LIKE ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<String>>() {
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
