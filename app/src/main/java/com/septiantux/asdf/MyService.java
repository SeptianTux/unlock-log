package com.septiantux.asdf;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyService extends Service {
    private MyBroadcaseReceiver myBroadcaseReceiver;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.w("Service", "onCreate");

        myBroadcaseReceiver = new MyBroadcaseReceiver();

        registerReceiver(
                myBroadcaseReceiver, new IntentFilter("android.intent.action.SCREEN_OFF")
        );

        registerReceiver(
                myBroadcaseReceiver
                , new IntentFilter("android.intent.action.USER_PRESENT")
        );

        registerReceiver(
                myBroadcaseReceiver
                , new IntentFilter("android.intent.action.ACTION_SHUTDOWN")
        );

        registerReceiver(
                myBroadcaseReceiver
                , new IntentFilter("android.intent.action.QUICKBOOT_POWEROFF")
        );

        registerReceiver(
                myBroadcaseReceiver
                , new IntentFilter("android.intent.action.LOCKED_BOOT_COMPLETE")
        );

        registerReceiver(
                myBroadcaseReceiver
                , new IntentFilter("android.intent.action.BOOT_COMPLETE")
        );
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Log.w("Service", "onDestroy");
        unregisterReceiver(myBroadcaseReceiver);
    }
}