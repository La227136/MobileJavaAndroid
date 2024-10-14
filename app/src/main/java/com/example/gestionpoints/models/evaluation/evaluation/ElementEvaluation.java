package com.example.gestionpoints.models.evaluation.evaluation;

import java.io.Serializable;

public abstract class ElementEvaluation implements Serializable {
    private String mName;
    private float mMaxPoints;
    private int mParentID;
    private int mID;

    public String getParentType() {
        return mParentType;
    }

    private String mParentType;

    public ElementEvaluation(String Name, float Points, int ID) {
        this.mName = Name;
        this.mMaxPoints = Points;
        this.mID = ID;
    }
    public int getID() {
        return mID;
    }

    public float getMaxPoints() {
        return mMaxPoints;
    }

    public void setMaxPoints(int mPoints) {
        this.mMaxPoints = mPoints;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public int getParentID() {
        return mParentID;
    }
}

