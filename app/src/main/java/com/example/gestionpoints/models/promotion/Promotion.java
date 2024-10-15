package com.example.gestionpoints.models.promotion;

import java.io.Serializable;

public class Promotion implements Serializable {

    private boolean mIsSelected = false;
    private int mId;
    private String mName;

    public int getId() {
        return mId;
    }

    public Promotion(String name, int id) {
        this.mName = name;
        this.mId = id;
    }

    public Promotion(String name) {
        this.mName = name;
    }

    public String getName() {
        return mName;
    }

    public boolean isSelected() {
        return mIsSelected;
    }

    public void setIsSelected(boolean mIsSelected) {
        this.mIsSelected = mIsSelected;
    }
}