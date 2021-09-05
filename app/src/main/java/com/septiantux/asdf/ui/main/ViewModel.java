package com.septiantux.asdf.ui.main;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewModel extends AndroidViewModel {
    private Dao mDao;

    public ViewModel(@NonNull Application application) {
        super(application);

        Databases db = Databases.getDatabase(application);
        mDao = db.lockUnlockLogDao();
    }

    public LiveData<List<Data>> getAllLog() {
        return mDao.getAll();
    }

    public LiveData<Integer> count() {
        return mDao.count();
    }

    public void insert(final Data data) {
        Thread t = new Thread(new Runnable(){
            @Override
            public void run() {
                mDao.insertAll(data);
            }
        });
        t.start();
    }

    public void deleteAll() {
        Thread t = new Thread(new Runnable(){
            @Override
            public void run() {
                mDao.deleteAll();
            }
        });
        t.start();
    }
}
