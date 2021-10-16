package com.septiantux.asdf.ui.main;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Data")
public class Data {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "timestamp")
    public long timestamp;

    // 0    = lock
    // 1    = unlock
    // 2    = boot
    // 3    = shutdown
    @ColumnInfo(name = "type")
    public int type;

    @ColumnInfo(name = "mark")
    public boolean mark;
}