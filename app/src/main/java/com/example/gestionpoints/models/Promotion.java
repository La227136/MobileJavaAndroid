package com.example.gestionpoints.models;

import java.io.Serializable;

public class Promotion implements Serializable {

    private boolean mIsSelected = false;
    private Integer mId;
    private String mName;

    public Promotion(String name, int id) {
        this.mName = name;
        this.mId = id;
    }

    public Promotion(String name) {
        this.mName = name;
    }
    public Integer getId() {
        return mId;
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