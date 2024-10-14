package com.example.gestionpoints.models.student;

import java.io.Serializable;
import java.util.List;

public class Student {
    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public int getId() {
        return mid;
    }

    public void setId(int mid) {
        this.mid = mid;
    }

    public int getPromotionID() {
        return mPromotionID;
    }

    public void setPromotionID(int mPromotionID) {
        this.mPromotionID = mPromotionID;
    }

    private String mLastName;
    private String mFirstName;
    private int mid;
    private int mPromotionID;

    public Student(int id, String lastName, String firstName, int promotion) {
        this.mid = id;
        this.mLastName = lastName;
        this.mFirstName = firstName;
        this.mPromotionID = promotion;
    }
}