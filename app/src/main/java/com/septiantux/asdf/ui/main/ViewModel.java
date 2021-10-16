package com.septiantux.asdf.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewModel extends AndroidViewModel {
    private DataDao mDataDao;

    public ViewModel(@NonNull Application application) {
        super(application);

        Databases db = Databases.getDatabase(application);
        mDataDao = db.dataDao();
    }

    public LiveData<List<Data>> getAllLog() {
        return mDataDao.getAll();
    }

    public void deleteAll() {
        Thread t = new Thread(new Runnable(){
            @Override
            public void run() {
                mDataDao.deleteAll();
            }
        });
        t.start();
    }

    public void mark(final int id, final boolean mark) {
        Thread t = new Thread(new Runnable(){
            @Override
            public void run() {
                mDataDao.mark(id, mark);
            }
        });
        t.start();
    }
}
