package com.example.l.afiefbelajar;

/**
 * Created by L on 1/9/17.
 */

public class DataSelected {
    private String data;
    private boolean isselected;

    public DataSelected(String data, boolean isselected) {
        this.data = data;
        this.isselected = isselected;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isselected() {
        return isselected;
    }

    public void setIsselected(boolean isselected) {
        this.isselected = isselected;
    }
}
