package com.septiantux.asdf.ui.main;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DataLog {
    private int id;
    private long timestamp;
    private int type;
    private int icon;
    private boolean mark;

    public DataLog() {
    }

    public DataLog(int id, int timestamp, int type, boolean mark) {
        this.id = id;
        this.timestamp = timestamp;
        this.type = type;
        this.mark = mark;
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

    public void setMark(boolean m) {
        this.mark = m;
    }

    public boolean getMark() {
        return this.mark;
    }
}
