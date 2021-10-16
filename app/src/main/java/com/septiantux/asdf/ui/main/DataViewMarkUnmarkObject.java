package com.septiantux.asdf.ui.main;

import java.util.ArrayList;

public class DataViewMarkUnmarkObject {
    private ArrayList<Integer> id;
    private ArrayList<Boolean> mark;

    DataViewMarkUnmarkObject() {
        this.id = new ArrayList<>();
        this.mark = new ArrayList<>();
    }

    public void markUnmark(DataLog dataLog) {
        int index = -1;

        index=this.id.indexOf(dataLog.getId());
        if(index >= 0) {
            this.mark.set(index, !this.mark.get(index));
        } else {
            this.id.add(dataLog.getId());
            this.mark.add(!dataLog.getMark());
        }
    }

    public ArrayList<Integer> getId() {
        return this.id;
    }

    public int getId(int index) {
        return this.id.get(index);
    }

    public ArrayList<Boolean> getMark() {
        return this.mark;
    }

    public Boolean getMark(int index) {
        return this.mark.get(index);
    }

    public int getSize() {
        return this.id.size();
    }

    public boolean getMarkById(int id) {
        return this.mark.get(this.id.indexOf(id));
    }
}
