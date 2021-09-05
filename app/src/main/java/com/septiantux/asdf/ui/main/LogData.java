package com.septiantux.asdf.ui.main;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class LogData {
    private int id;
    private long timestamp;
    private int type;
    private String date;
    private int icon;

    public LogData() {
    }

    public LogData(int id, int timestamp, int type) {
        this.id = id;
        this.timestamp = timestamp;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getType() {
        return this.type;
    }

    public String getTypeString() {
        String ret;

        switch (this.type) {
            case 0  : ret = "Lock";     break;
            case 1  : ret = "Unlock";   break;
            case 2  : ret = "Boot";     break;
            case 3  : ret = "Shutdown"; break;
            default : ret = "Unknown";  break;
        }

        return ret;
    }

    public void setType(int type) {
        this.type = type;
    }

    @SuppressLint("SimpleDateFormat")
    public String getDate() {
        Date date;
        DateFormat format;

        date = new Date(this.timestamp*1000);
        format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));

        return format.format(date);
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getIcon() {
        return this.icon;
    }
}
