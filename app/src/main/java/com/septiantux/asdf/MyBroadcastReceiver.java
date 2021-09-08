package com.septiantux.asdf;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
            case "android.intent.action.BOOT_COMPLETE":
            case "android.intent.action.LOCKED_BOOT_COMPLETE":
                message = "boot";
                data.type = 2;
                toast_show = false;
                break;
            case "android.intent.action.ACTION_SHUTDOWN":
            case "android.intent.action.QUICKBOOT_POWEROFF":
                message = "shutdown";
                data.type = 3;
                toast_show = false;
                break;
            default:
                message = "unknown";
                data.type = 4;
                break;
        }

        if(toast_show) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }

        Log.w("MyBroadcastReceiver", message);

        this.writeToDatabase(data);

        /*
        if(data.type == 2) {
            Intent i = new Intent("com.septiantux.asdf.MyService");
            i.setClass(context, MyService.class);
            context.startService(i);
        }
         */
    }

    private void writeToDatabase(final Data data) {
        final Databases db;
        db = Databases.getDatabase(context);

        try {
            Databases.databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        DataDao dataDao = db.dataDao();
                        dataDao.insertAll(data);
                        Log.w("MyBroadcastReceiver", "write to database");
                    } catch (Exception e) {
                        Log.w("err", e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            Log.w("err", e.getMessage());
        }
    }
}
