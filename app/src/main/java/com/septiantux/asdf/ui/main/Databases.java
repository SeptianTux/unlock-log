package com.septiantux.asdf.ui.main;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Data.class}, version = 1)
public abstract class Databases extends RoomDatabase {
    public abstract DataDao dataDao();

    private static volatile Databases INSTANCE;
    private static final int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static Databases getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (Databases.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            Databases.class, "unlock-log.db")
                            .build();
                }
            }
        }

        return INSTANCE;
    }
}
