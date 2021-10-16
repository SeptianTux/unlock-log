package com.septiantux.asdf;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.septiantux.asdf.ui.main.DataDao;
import com.septiantux.asdf.ui.main.Data;
import com.septiantux.asdf.ui.main.Databases;

public class MyBroadcastReceiver extends BroadcastReceiver {
    Data data;
    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = null;
        long timeMillis = System.currentTimeMillis();
        String action = intent.getAction();
        boolean toast_show = true;

        this.context = context;
        data = new Data();
        data.timestamp = timeMillis/1000;

        assert action != null;
        switch (action) {
            case "android.intent.action.SCREEN_OFF":
                message = "lock";
                data.type = 0;
                toast_show = false;
                break;
            case "android.intent.action.USER_PRESENT":
                message = "unlock";
                data.type = 1;
                break;
            case "android.intent.action.BOOT_COMPLETED":
            case "android.intent.action.LOCKED_BOOT_COMPLETED":
                message = "boot";
                data.type = 2;
                break;
            case "android.intent.action.ACTION_SHUTDOWN":
            case "android.intent.action.QUICKBOOT_POWEROFF":
                message = "shutdown";
                data.type = 3;
                break;
            default:
                message = "unknown";
                data.type = 4;
                break;
        }

        if(toast_show) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }

        this.writeToDatabase(data);
    }

    private void writeToDatabase(final Data data) {
        final Databases db;
        db = Databases.getDatabase(context);

        Databases.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    DataDao dataDao = db.dataDao();
                    dataDao.insertAll(data);
                } catch (Exception ignored) {
                }
            }
        });

    }
}
