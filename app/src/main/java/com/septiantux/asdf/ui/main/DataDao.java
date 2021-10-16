package com.septiantux.asdf.ui.main;

import androidx.room.Dao;
import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DataDao {
    @Query("SELECT * FROM Data ORDER BY id DESC")
    LiveData<List<Data>> getAll();

    @Insert
    void insertAll(Data... data);

    @Query("DELETE FROM Data")
    void deleteAll();

    @Query("UPDATE Data SET mark=:mark WHERE id=:id")
    void mark(int id, boolean mark);
}
