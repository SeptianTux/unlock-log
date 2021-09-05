package com.septiantux.asdf.ui.main;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class Dao_Impl implements Dao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Data> __insertionAdapterOfData;

  private final EntityDeletionOrUpdateAdapter<Data> __deletionAdapterOfData;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public Dao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfData = new EntityInsertionAdapter<Data>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Data` (`id`,`timestamp`,`type`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Data value) {
        stmt.bindLong(1, value.id);
        stmt.bindLong(2, value.timestamp);
        stmt.bindLong(3, value.type);
      }
    };
    this.__deletionAdapterOfData = new EntityDeletionOrUpdateAdapter<Data>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Data` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Data value) {
        stmt.bindLong(1, value.id);
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM Data";
        return _query;
      }
    };
  }

  @Override
  public void insertAll(final Data... data) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfData.insert(data);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Data data) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfData.handle(data);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public LiveData<List<Data>> getAll() {
    final String _sql = "SELECT * FROM Data ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"Data"}, false, new Callable<List<Data>>() {
      @Override
      public List<Data> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final List<Data> _result = new ArrayList<Data>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Data _item;
            _item = new Data();
            _item.id = _cursor.getInt(_cursorIndexOfId);
            _item.timestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _item.type = _cursor.getInt(_cursorIndexOfType);
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
  public Data findById(final int id) {
    final String _sql = "SELECT * FROM Data WHERE id = ? ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
      final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
      final Data _result;
      if(_cursor.moveToFirst()) {
        _result = new Data();
        _result.id = _cursor.getInt(_cursorIndexOfId);
        _result.timestamp = _cursor.getLong(_cursorIndexOfTimestamp);
        _result.type = _cursor.getInt(_cursorIndexOfType);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<Integer> count() {
    final String _sql = "SELECT COUNT(timestamp) FROM Data";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"Data"}, false, new Callable<Integer>() {
      @Override
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if(_cursor.moveToFirst()) {
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
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
