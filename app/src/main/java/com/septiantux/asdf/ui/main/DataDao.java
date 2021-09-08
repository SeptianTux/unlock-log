package com.septiantux.asdf.ui.main;

import androidx.room.Dao;
import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DataDao {
    @Query("SELECT * FROM Data ORDER BY id DESC")
    LiveData<List<Data>> getAll();

    @Query("SELECT * FROM Data WHERE id = :id ")
    Data findById(int id);

    @Query("SELECT COUNT(timestamp) FROM Data")
    LiveData<Integer> count();

    @Insert
    void insertAll(Data... data);

    @Delete
    void delete(Data data);

    @Query("DELETE FROM Data")
    void deleteAll();

    @Query("UPDATE Data SET mark=:mark WHERE id=:id")
    void mark(int id, boolean mark);
}
