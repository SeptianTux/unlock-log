package com.septiantux.asdf.ui.main;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@androidx.room.Dao
public interface Dao {
    @Query("SELECT * FROM Data ORDER BY id DESC")
    LiveData<List<Data>> getAll();

    @Query("SELECT * FROM Data WHERE id = :id ")
    Data findById(int id);

    /*
    @Query("SELECT * FROM lockunlocklog WHERE timestamp = :timestamp ")
    LockUnlockLog findByTimestamp(long timestamp);

    @Query("SELECT * FROM lockunlocklog WHERE type = :type ")
    LiveData<List<LockUnlockLog>> findByType(int type);

     */

    @Query("SELECT COUNT(timestamp) FROM Data")
    LiveData<Integer> count();

    @Insert
    void insertAll(Data... data);

    @Delete
    void delete(Data data);

    @Query("DELETE FROM Data")
    void deleteAll();
}
