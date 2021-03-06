package com.septiantux.asdf;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

/*
    Mungkin ada penghemat baterai atau pembatasan aplikasi yang berjalan di
    latar belakang, pastikan itu tidak menghentikan service ini.
 */
public class MyService extends Service {
    private MyBroadcastReceiver myBroadcastReceiver;

    @Override
    public void onCreate() {
        super.onCreate();

        myBroadcastReceiver = new MyBroadcastReceiver();

        registerReceiver(
                myBroadcastReceiver, new IntentFilter("android.intent.action.SCREEN_OFF")
        );

        registerReceiver(
                myBroadcastReceiver
                , new IntentFilter("android.intent.action.USER_PRESENT")
        );

        registerReceiver(
                myBroadcastReceiver
                , new IntentFilter("android.intent.action.BOOT_COMPLETED")
        );

        registerReceiver(
                myBroadcastReceiver
                , new IntentFilter("android.intent.action.LOCKED_BOOT_COMPLETED")
        );

        registerReceiver(
                myBroadcastReceiver
                , new IntentFilter("android.intent.action.ACTION_SHUTDOWN")
        );

        registerReceiver(
                myBroadcastReceiver
                , new IntentFilter("android.intent.action.QUICKBOOT_POWEROFF")
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
        unregisterReceiver(myBroadcastReceiver);
    }
}